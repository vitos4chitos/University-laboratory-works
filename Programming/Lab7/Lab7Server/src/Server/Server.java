package Server;

import Data.*;
import Handlers.CommandHandler;
import Handlers.RequestHandler;
import Handlers.ResponseHandler;
import communication.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Server implements ServerCommands, Runnable {

    private int port = 1402;
    private SocketAddress socketAddress = new InetSocketAddress(port);
    private DatagramChannel channel;
    {
        try {
            channel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            channel.bind(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String path, login, password;
    public static Connection con;
    private ForkJoinPool readRequest = new ForkJoinPool(5);
    private ExecutorService requestProcessing = Executors.newCachedThreadPool();
    private ExecutorService sendResponse = Executors.newFixedThreadPool(4);
    private ConcurrentHashMap<SelectionKey, Future<Packet>> clientObj = new ConcurrentHashMap<>();
    private ConcurrentHashMap<SelectionKey, Future<Packet>> result = new ConcurrentHashMap<>();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        shutDownHook();
        uploading();
        System.out.println("Коллекция успешно выгружна, ожидаем запросов...");
        try {
            Selector selector = Selector.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            while (true) {
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();
                    if (key.isReadable())
                        read(key);
                    if (key.isWritable())
                        write(key);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        System.out.println("Включаем сервер...");
        Server server = new Server();
        Server.path = args[0];
        Server.login = args[1];
        Server.password = args[2];
        server.run();
    }

    private void read(SelectionKey key) {
        DatagramChannel channel = (DatagramChannel) key.channel();
        clientObj.put(key, readRequest.submit(new RequestHandler(channel, new ServerCommands() {
        })));
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key){
        try {
            if (clientObj.containsKey(key) && clientObj.get(key).isDone()) {
                result.put(key, requestProcessing.submit(new CommandHandler(clientObj.get(key).get(), new ServerCommands(){})));
                clientObj.remove(key);
            }
            if (result.containsKey(key) && result.get(key).isDone()) {
                sendResponse.execute(new ResponseHandler(key, result.get(key).get()));
                result.remove(key);
                key.interestOps(SelectionKey.OP_READ);
            }

        } catch (RuntimeException | InterruptedException | ExecutionException ignored) {
            ignored.printStackTrace();
        }

    }

    private void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            readRequest.shutdown();
            requestProcessing.shutdown();
            sendResponse.shutdown();
        }));

    }

    private void uploading(){
        System.out.println("Производим выгрузку коллекции...");
        try {
            con = DriverManager.getConnection(
                    path, login, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (!uploadCollection()) {
            System.out.println("Введите url");
            path = scanner.nextLine();
            System.out.println("Введите логин");
            login = scanner.nextLine();
            System.out.println("Введите пароль");
            password = scanner.nextLine();
            try {
                con = DriverManager.getConnection(
                        path, login, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        new Thread(() -> {
            Scanner cin = new Scanner(System.in);
            while (cin.hasNext()) {
                String in = cin.nextLine();
                if (in.equals("exit")) {
                    System.out.println("Работа сервера завершена.");
                    System.exit(0);
                }
            }
        }).start();
    }

    static class Help {
        static Packet help() {
            Packet p  = new Packet(true, "answer", null, null);
            p.addAnswer("Информация о командах");
            p.addAnswer("Команда help: вывести справку по доступным командам");
            p.addAnswer(Info.getInfo());
            p.addAnswer(Show.getInfo());
            p.addAnswer(Add.getInfo());
            p.addAnswer(UpdateId.getInfo());
            p.addAnswer(RemoveById.getInfo());
            p.addAnswer(Clear.getInfo());
            p.addAnswer(ExecuteScript.getInfo());
            p.addAnswer(AddIfMax.getInfo());
            p.addAnswer(RemoveById.getInfo());
            p.addAnswer(RemoveLower.getInfo());
            p.addAnswer(History.getInfo());
            p.addAnswer(GroupCountingByCreationDate.getInfo());
            p.addAnswer(FilterByHeight.getInfo());
            p.addAnswer(PrintUniqueLocation.getInfo());
            return p;
        }
    }

    static class Info {
        static String getInfo() {
            return "Команда info: вывести в стандартный поток вывода информацию о коллекции";
        }

        static Packet info() {
            Packet p  = new Packet(true, "answer", null, null);
            p.addAnswer("Тип коллекции: HashSet");
            p.addAnswer("Тип объектов в коллекции: Person");
            p.addAnswer("Количество элементов коллекции: " + DataBase.base.size());
            return p;
        }
    }

    static class Show {
        static String getInfo() {
            return ("Команда show: вывести в стандартный поток вывода " +
                    "все элементы коллекции в строковом представлении");
        }

        static Packet show() {
            Packet pack  = new Packet(true, "answer", null, null);
            pack.addAnswer("Информация представлена в порядке: id; name; coordinate x; coordinate y; creationDate;" +
                    "height; pasportID; hairColor; nationality; location x; location y; location z, user");
            for(Person p: DataBase.base){
                pack.addAnswer(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                        + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                        + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                        p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ() + " " +
                        p.getUser());
            }
            return pack;
        }
    }

    static class Add {
        static String getInfo() {
            return ("Команда add: добавить новый элемент в коллекцию");
        }

        static Packet add(Person person) {
            Packet packet;
            person.changeCreationTime(LocalDate.now());
            try(Statement statement = con.createStatement()){
                String ex = "INSERT INTO people (person_name, coordinate_x, coordinate_Y, " +
                        "creation_date, height, haircolor, nationality, location_x, location_y, location_z, " +
                        "data_user, person_id, passportid) VALUES ('" + person.getName() + "', " + person.getCoordinates().getX() +
                        ", " + person.getCoordinates().getY() + ", '" + person.getCreationDate() + "', " +
                        person.getHeight() + ", '" + person.getHairColor() + "', '" + person.getNationality() + "', " +
                        person.getLocation().getX() + ", " + person.getLocation().getY() + ", " +
                        person.getLocation().getZ() + ", '" + person.getUser() + "', (SELECT nextval('serial')), '" +
                        person.getPassportID() + "')";
                statement.executeUpdate(ex);
                packet = new Packet(true, "answer", null, null);
                packet.addAnswer("Элемент успешно добавлен в коллекцию");
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                packet = new Packet(false, "answer", null, null);
                packet.addAnswer("Ошибка при добавлении в базу, элемент с таким PassportId уже существует");
            }
            return packet;
        }
    }

    static class UpdateId {
        static String getInfo() {
            return ("Команда update id: обновить значение элемента коллекции, id которого равен заданному");
        }

        static Packet updateId(Person person, String id, String login) {
            Packet packet;
            person.changeCreationTime(LocalDate.now());
            try(Statement statement = Server.con.createStatement()) {
                ResultSet rs = statement.executeQuery("select * from people where person_id = " + id);
                if(rs.next()){
                    if(rs.getString("data_user").equals(login)){
                        statement.executeUpdate("UPDATE people SET person_name = '" + person.getName() + "', " +
                                "coordinate_x = " + person.getCoordinates().getX() + ", coordinate_y = " +
                                person.getCoordinates().getY() + ", creation_date = '" + person.getCreationDate() +
                                "', height = " + person.getHeight() + ", passportid = '" + person.getPassportID() + "', " +
                                "haircolor = '" + person.getHairColor() + "', nationality = '" + person.getNationality() +
                                "', location_x = " + person.getLocation().getX() + ", location_y = " +
                                person.getLocation().getY() + ", location_z = " + person.getLocation().getZ() + " WHERE" +
                                " person_id = " + id);
                        packet = new Packet(true, "answer", null, null);
                        packet.addAnswer("Элемент успешно обновлен в коллекции");
                    }
                    else{
                        packet = new Packet(false, "answer", null, null);
                        packet.addAnswer("У вас нет прав на изменение этого элемента");
                    }
                }
                else{
                    packet = new Packet(false, "answer", null, null);
                    packet.addAnswer("Элемента с таким id нет");
                }

            } catch (SQLException throwables) {
                packet = new Packet(false, "answer", null, null);
                packet.addAnswer("Ошибка при обновлении, проверьте значение passportID, так как такое значение уже есть" +
                        " в базе");
            }
            return packet;
        }
    }

    static class RemoveById {
        static String getInfo() {
            return ("Команда remove_by_id: удалить элемент из коллекции по его id");
        }
        static Packet removeById(String id, String login){
            Packet packet;
            try(Statement statement = Server.con.createStatement()) {
                ResultSet rs = statement.executeQuery("select * from people where person_id = " + id);
                if(rs.next()){
                    if(rs.getString("data_user").equals(login)){
                        statement.executeUpdate("DELETE from people WHERE person_id = " + id);
                        packet = new Packet(true, "answer", null, null);
                        packet.addAnswer("Элемент успешно удалён из коллекции");
                    }
                    else{
                        packet = new Packet(false, "answer", null, null);
                        packet.addAnswer("У вас нет прав на удаление этого элемента");
                    }
                }
                else{
                    packet = new Packet(false, "answer", null, null);
                    packet.addAnswer("Элемента с таким id нет");
                }

            } catch (SQLException throwables) {
                packet = new Packet(false, "answer", null, null);
            }
            return packet;

        }
    }

    static class Clear {
        static String getInfo() {
            return ("Команда clear: очистить коллекцию");
        }
        static Packet clear(String login){
            Packet packet;
            try(Statement statement = Server.con.createStatement()) {
                statement.executeUpdate("DELETE from people WHERE data_user = '" + login + "'");
                packet = new Packet(true, "answer", null, null);
                packet.addAnswer("Ваши данные из базы успешно удалены");
            } catch (SQLException throwables) {
                packet = new Packet(false, "answer", null, null);
            }
            return packet;
        }
    }

    static class ExecuteScript {
        static String getInfo() {
            return ("Команда execute_script: : считать и исполнить скрипт из указанного файла. " +
                    "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в " +
                    "интерактивном режиме.");
        }

        static Packet executeScript(String path){
            Packet packet;
            if (DataBase.scriptsNames.indexOf(path) >= 0) {
                packet = new Packet(false, "answer", null, null);
                packet.addAnswer("Ошбика возникновение рекрусии, команда не будет выполнена");
            }
            else{
                DataBase.scriptsNames.add(path);
                packet = new Packet(true, "answer", null, null);
                packet.addAnswer("Скрипт запущён");
            }
            return packet;

        }

    }

    static class AddIfMax {
        static String getInfo() {
            return ("Команда add_if_max: добавить новый элемент в коллекцию, если его значение " +
                    "превышает значение наибольшего элемента этой коллекции");
        }

        static Packet addIfMax(Person person) {
            Packet packet;
            List<Person> check = DataBase.base.stream().filter(x -> x.getCoordinates().getX() >= person.getCoordinates().getX())
                    .collect(Collectors.toList());
            if(check.size() == 0){
                return Server.Add.add(person);
            }
            else {
                packet = new Packet(false, "answer", null, null);
                packet.addAnswer("Данный элемент не обладает максимальным свойством и не будет добавлен");
                return packet;
            }
        }
    }

    static class RemoveLower {
        static String getInfo() {
            return ("Команда remove_lower: удалить из коллекции все элементы, меньшие, чем заданный");
        }
        static Packet removeLower(String h, String login){
            Packet packet;
            try(Statement statement = Server.con.createStatement()) {
                statement.executeUpdate("DELETE from people WHERE data_user = '" + login + "' and height < " + h);
                packet = new Packet(true, "answer", null, null);
                packet.addAnswer("Ваши данные из базы успешно удалены");
            } catch (SQLException throwables) {
                packet = new Packet(false, "answer", null, null);
            }
            return packet;
        }
    }

    static class History {
        static String getInfo() {
            return ("Команда history: вывести последние 10 команд (без их аргументов)");
        }

        static Packet history() {
            Packet packet = new Packet(true, "answer", null, null);
            for (int i = 0; i < DataBase.history.size(); i++) {
                packet.addAnswer(DataBase.history.peek());
                DataBase.history.add(DataBase.history.peek());
                DataBase.history.poll();
            }
            return packet;
        }
    }

    static class GroupCountingByCreationDate {
        static String getInfo() {
            return ("Команда group_counting_by_creation_date: группировать элементы коллекции " +
                    "по значению поля creationDate, вывести количество элементов в каждой группе");
        }
        static Packet groupCountingByCreationDate(){
            List<Person> check = DataBase.base.stream().sorted((Comparator<Person>) (o1, o2)->
                    o1.getCreationDate().compareTo(o2.getCreationDate()))
                    .collect(Collectors.toList());
            Packet pack  = new Packet(true, "answer", null, null);
            pack.addAnswer("Информация представлена в порядке: id; name; coordinate x; coordinate y; creationDate;" +
                    "height; pasportID; hairColor; nationality; location x; location y; location z");
            for(Person p: check){
                pack.addAnswer(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                        + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                        + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                        p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
            }
            return pack;
        }
    }

    static class FilterByHeight {
        static String getInfo() {
            return ("Команда filter_by_height: вывести элементы, значение поля height которых равно заданному");
        }
        static Packet filterByHeight(String h){
            Float height = Float.valueOf(h);
            List<Person> check = DataBase.base.stream().filter(x -> x.getHeight() == height - 0.000001f)
                    .collect(Collectors.toList());
            Packet pack  = new Packet(true, "answer", null, null);
            pack.addAnswer("Информация представлена в порядке: id; name; coordinate x; coordinate y; creationDate;" +
                    "height; pasportID; hairColor; nationality; location x; location y; location z");
            for(Person p: check){
                pack.addAnswer(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                        + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                        + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                        p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
            }
            return pack;
        }
    }

    static class PrintUniqueLocation {
        static String getInfo() {
            return ("Команда print_unique_location: вывести уникальные значения поля location");
        }
        static Packet printUniqueLocation(){
            HashSet<Location> locations = new HashSet<Location>();
            Packet packet  = new Packet(true, "answer", null, null);
            for(Person p: DataBase.base){
                locations.add(p.getLocation());
            }
            for(Location l: locations){
                packet.addAnswer(l.getX() + " " + l.getY() + " " + l.getZ());
            }
            return packet;

        }

    }

    static class Over{
        static synchronized Packet over(Packet exec){
            int index = 0;
            int index1 = 0;
            Pattern pattern = Pattern.compile(exec.getLogin() + "$");
            Matcher matcher;
            for(String s: DataBase.scriptsNames) {
                matcher = pattern.matcher(s);
                while (matcher.find()) {
                    index = index1;
                }
                index1++;
            }
            DataBase.scriptsNames.remove(index);
            Packet packet = new Packet(true, "answer", null, null);
            packet.addAnswer("Скрипт закончен");
            return packet;
        }
    }

    static class Save {
        static void save() {
        }
    }

    static class Exit{
        static void exit(){
            Save.save();
            System.out.println("Отключаю сервер...");
            System.exit(0);
        }
    }

    static class RegLogIn{
        static Packet Regin(String login, String password){
            Packet packet;
            try(Statement statement = con.createStatement()) {
                statement.executeUpdate("insert into users (login, password) VALUES ('" + login + "', '" +
                        password +"')");
                packet = new Packet(true, "answer", null, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                packet = new Packet(false, "answer", null, null);
            }
            return packet;
        }
        static Packet LogIn(String login, String password){
            Packet packet;
            try(Statement statement = con.createStatement()) {
                ResultSet rs = statement.executeQuery("select * from users where (login = '" + login +
                        "' and password = '" + password + "')");
                if(rs.next()){
                    packet = new Packet(true, "answer", null, null);
                    System.out.println("Пользователь " + login + " успешно подключился");
                    packet.addAnswer("Привет, ты залогинился");
                }
                else{
                    packet = new Packet(false, "answer", null, null);
                    packet.addAnswer("Привет, ты не залогинился");
                }

            } catch (SQLException throwables) {
                packet = new Packet(false, "answer", null, null);
                packet.addAnswer("Привет, ты не залогинился");
            }
            return packet;
        }
    }
}

