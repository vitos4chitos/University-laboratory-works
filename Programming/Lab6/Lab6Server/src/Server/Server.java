package Server;

import java.io.BufferedReader;
import java.io.FileWriter;
import Data.*;
import communication.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.rmi.ServerError;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Server implements ServerCommands, Runnable {

    private int port = 1341;
    private SocketAddress socketAddress = new InetSocketAddress(port);;
    private DatagramChannel channel;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
    private String path;
    private ByteBuffer output = ByteBuffer.allocate(4096);
    private ByteBuffer buffer = ByteBuffer.allocate(4096);

    @Override
    public void run() {
        System.out.println("Производим выгрузку коллекции...");
        Scanner scanner = new Scanner(System.in);
        while (!uploadCollection(path)) {
            System.out.println("Введите путь до файла:");
            path = scanner.nextLine();
        }
        Packet answer = null;
        try {
            Selector selector = Selector.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            while (true) {
                    if(in.ready()){
                        String serverCommand = in.readLine();
                        if(serverCommand.equals("exit")){
                            Server.Exit.exit();
                        }
                        if(serverCommand.equals("save")){
                            Server.Save.save();
                        }
                        else{
                            System.out.println("Такой команды нет.");
                        }
                    }
                    selector.selectNow();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();
                    while (iter.hasNext()){
                        SelectionKey key = iter.next();
                        if(key.isReadable()){
                            DatagramChannel client = (DatagramChannel) key.channel();
                            socketAddress = client.receive(output);
                            System.out.println("Пакет принят, начинаю обработку");
                            answer = opener(output);
                            output.clear();
                            key.interestOps(SelectionKey.OP_WRITE);
                        }
                        if(key.isWritable()){
                            DatagramChannel client = (DatagramChannel) key.channel();
                            buffer.clear();
                            buffer.put(Serializer.serialize(answer));
                            buffer.flip();
                            client.send(buffer, socketAddress);
                            System.out.println("Ответ успешно отправлен");
                            buffer.clear();
                            buffer.put(new byte[4096]);
                            buffer.clear();
                            key.interestOps(SelectionKey.OP_READ);
                        }
                        iter.remove();
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
        server.path = args[0];
        server.run();
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
            p.addAnswer("Тип объектов в коллекции: Lab5.DataBases.Data.Person");
            p.addAnswer("Количество элементов коллекции: " + DataBase.base.size());
            p.addAnswer("Размер считываемого файла в байтах: " + DataBase.getFile().length());
            p.addAnswer("Время последней модификации файла: " + DataBase.getFile().lastModified());
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
                    "height; pasportID; hairColor; nationality; location x; location y; location z");
            for(Person p: DataBase.base){
                pack.addAnswer(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                        + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                        + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                        p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
            }
            return pack;
        }
    }

     static class Add {
        static String getInfo() {
            return ("Команда add: добавить новый элемент в коллекцию");
        }

        static Packet add(Person person) {
            Packet packet = new Packet(true, "answer", null, null);
            List<Person> check = DataBase.base.stream().filter(x -> x.getPassportID().equals(person.getPassportID()))
                    .collect(Collectors.toList());
            if(check.size() != 0){
                packet.addAnswer("Data.Person с таким PassportId уже существует. Элемент не был " +
                        "добавлен. Пожалуйста, проверьте введенные данные " +
                        "и попробуйте ещё раз");
            }
            else{
                Integer id = DataBase.id.get(0);
                DataBase.id.remove(0);
                person.changeId(id);
                person.changeCreationTime(LocalDate.now());
                DataBase.base.add(person);
                packet.addAnswer("Элемент успешно добавлен в коллекцию");
            }
            return packet;
        }
    }

    static class UpdateId {
        static String getInfo() {
            return ("Команда update id: обновить значение элемента коллекции, id которого равен заданному");
        }

        static Packet updateId(Person person, String id) {
            List<Person> check = DataBase.base.stream().filter(x -> x.getPassportID().equals(person.getPassportID()))
                    .collect(Collectors.toList());
            Packet packet = new Packet(true, "answer", null, null);
            if ((check.size() == 0) || (check.get(0).getId().toString().equals(id))){
                Integer iD = Integer.valueOf(id);
                Optional<Person> checker = DataBase.base.stream().filter(x -> x.getId().equals(iD)).findAny();
                if (checker.isPresent()) {
                    Person person1 = checker.get();
                    person1.updateName(person.getName());
                    person1.updateCoordinates(person.getCoordinates().getX(), person.getCoordinates().getY());
                    person1.updateCreationDate(LocalDate.now());
                    person1.updateHeight(person.getHeight());
                    person1.updatePassportId(person.getPassportID());
                    person1.updateHairColor(person.getHairColor());
                    person1.updateNationality(person.getNationality());
                    person1.updateLocation(person.getLocation().getX(), person.getLocation().getY(), person
                    .getLocation().getZ());
                    packet.addAnswer("Объект успешно изменён");
                } else {
                    packet.addAnswer("Элемента с таким айди нет, попытайтесь ещё раз");
                }
            }
            else{
                packet.addAnswer("Такой passportId уже есть и не в том id, который вы хотите изменить");
            }
            return packet;
        }
    }

    static class RemoveById {
        static String getInfo() {
            return ("Команда remove_by_id: удалить элемент из коллекции по его id");
        }
        static Packet removeById(String iD){
            Integer id = Integer.valueOf(iD);
            Packet packet = new Packet(true, "answer", null, null);
            List<Person> check = DataBase.base.stream().filter(x -> !x.getId().equals(id))
                    .collect(Collectors.toList());
            if(check.size() != DataBase.base.size()) {
                DataBase.base = new HashSet<>(check);
                DataBase.id.add(id);
                packet.addAnswer("Элемент успешно удалён из колеекции");
            }
            else{
                packet.addAnswer("Элемента с таким айди нет в колеекции");
            }
            return packet;

        }
    }

    static class Clear {
        static String getInfo() {
            return ("Команда clear: очистить коллекцию");
        }
        static Packet clear(){
            for(Person p: DataBase.base){
                DataBase.id.add(p.getId());
            }
            DataBase.base.clear();
            Packet packet = new Packet(true, "answer", null, null);
            packet.addAnswer("Коллекция очищена");
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
            Packet packet = new Packet(true, "answer", null, null);
            List<Person> check = DataBase.base.stream().filter(x -> x.getPassportID().equals(person.getPassportID()))
                    .collect(Collectors.toList());
            if(check.size() != 0){
                packet.addAnswer("Data.Person с таким PassportId уже существует. Элемент не был " +
                        "добавлен. Пожалуйста, проверьте введенные данные " +
                        "и попробуйте ещё раз");
            }
            else{
                check = DataBase.base.stream().filter(x -> x.getCoordinates().getX() >= person.getCoordinates().getX())
                        .collect(Collectors.toList());
                if(check.size() == 0) {
                    Integer id = DataBase.id.get(0);
                    DataBase.id.remove(0);
                    person.changeId(id);
                    person.changeCreationTime(LocalDate.now());
                    DataBase.base.add(person);
                    packet.addAnswer("Элемент успешно добавлен в коллекцию");
                }
                else{
                    packet.addAnswer("Coordinate x не больше максимального значения в коллекции, эллемени не " +
                            "был добавлен");
                }
            }
            return packet;
        }
    }

    static class RemoveLower {
        static String getInfo() {
            return ("Команда remove_lower: удалить из коллекции все элементы, меньшие, чем заданный");
        }
        static Packet removeLower(String h){
            Float height = Float.valueOf(h);
            List<Person> check = DataBase.base.stream().filter(x -> x.getHeight() < height - 0.000001f)
                    .collect(Collectors.toList());
            for(Person p: check){
                DataBase.id.add(p.getId());
            }
            check = DataBase.base.stream().filter(x -> x.getHeight() >= height - 0.000001f)
                    .collect(Collectors.toList());
            DataBase.base = new HashSet<>(check);
            Packet packet = new Packet(true, "answer", null, null);
            packet.addAnswer("Элементы, с Height меньше чем " + h + ", удалены");
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
        static Packet over(){
            DataBase.scriptsNames.remove(DataBase.scriptsNames.size() - 1);
            Packet packet = new Packet(true, "answer", null, null);
            packet.addAnswer("Скрипт закончен");
            return packet;
        }
    }

    static class Save{
        static void save(){
            try{
                FileWriter writer = new FileWriter(DataBase.getFile().toString(), false);
                String str;
                writer.write("id;name;coordinate x;coordinate y;creationDate;height;pasport ID;hairColor;nationality;" +
                        "location x;location y;location z");
                for(Person p: DataBase.base){
                    writer.append('\n');
                    str = p.getId().toString() + ";" + p.getName() + ";" + p.getCoordinates().getX() + ";" +
                            p.getCoordinates().getY().toString() + ";" + p.getCreationDate().toString() + ";" +
                            p.getHeight().toString() + ";" + p.getPassportID() + ";" + p.getHairColor().toString() + ";"
                            + p.getNationality().toString() + ";" + p.getLocation().getX() + ";"
                            + p.getLocation().getY().toString() + ";" + p.getLocation().getZ().toString();
                    writer.write(str);
                }
                writer.flush();
                writer.close();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            try {
                FileWriter writer = new FileWriter(DataBase.getIdFile().toString(), false);
                String str = "";
                for(Integer s: DataBase.id){
                    str += s.toString() + ";";
                }
                writer.write(str);
                writer.flush();
                writer.close();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("Коллеция успешно сохранена");
        }
        }

    static class Exit{
        static void exit(){
            Save.save();
            System.out.println("Отключаю сервер...");
            System.exit(0);
        }
    }
    }

