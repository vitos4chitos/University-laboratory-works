package Lab5.Controller;

import Lab5.DataBases.*;
import Lab5.Methods.BinSearch;
import Lab5.Sorts.SortById;
import Lab5.Sorts.SortByLocalDate;

import java.util.*;
import java.time.LocalDate;
import java.io.*;

public class Controller {
    public static Scanner in = new Scanner(System.in);

    public static void Command(String cmd, DataBase dataBase) {
        switch (cmd) {
            case "help":
                Help.help();
                DataBase.addToHistory("help");
                break;
            case "info":
                Info.info(dataBase.base);
                DataBase.addToHistory("info");
                break;
            case "show":
                Show.show(dataBase.base);
                DataBase.addToHistory("show");
                break;
            case "add":
                Add.add(dataBase);
                DataBase.addToHistory("add");
                break;
            case "update id":
                UpdateId.updateId(dataBase);
                DataBase.addToHistory("update id");
                break;
            case "remove_by_id":
                RemoveById.removeById(dataBase);
                DataBase.addToHistory("remove_by_id");
                break;
            case "clear":
                Clear.clear(dataBase);
                DataBase.addToHistory("clear");
                break;
            case "exit":
                Exit.exit();
                System.exit(0);
                break;
            case "history":
                History.history();
                DataBase.addToHistory("history");
                break;
            case "save":
                Save.save(dataBase);
                DataBase.addToHistory("save");
                break;
            case "add_if_max":
                AddIfMax.addIfMax(dataBase);
                DataBase.addToHistory("add_if_max");
                break;
            case "remove_lower":
                RemoveLower.removeLower(dataBase);
                DataBase.addToHistory("remove_lower");
                break;
            case "filter_by_height":
                FilterByHeight.filterByHeight(dataBase);
                DataBase.addToHistory("filter_by_height");
                break;
            case "print_unique_location":
                PrintUniqueLocation.printUniqueLocation(dataBase);
                DataBase.addToHistory("print_unique_location");
                break;
            case "group_counting_by_creation_date":
                GroupCountingByCreationDate.groupCountingByCreationDate(dataBase);
                DataBase.addToHistory("group_counting_by_creation_date");
                break;
            case "execute_script":
                ExecuteScript.executeScript(dataBase);
                DataBase.addToHistory("execute_script");
                break;
            default:
                System.out.println("Команда введена нереверно. Попробуйте ещё раз");
        }
    }
    private static void changeScanner(File file) throws java.io.FileNotFoundException{
        in = new Scanner(file);
    }
    private static void returnScanner(){
        in = new Scanner(System.in);
    }

    private static class Help {
        static void help() {
            System.out.println("Информация о командах:");
            System.out.println("Команда help: вывести справку по доступным командам");
            Info.getInfo();
            Show.getInfo();
            Add.getInfo();
            UpdateId.getInfo();
            RemoveById.getInfo();
            Clear.getInfo();
            Save.getInfo();
            ExecuteScript.getInfo();
            Exit.getInfo();
            AddIfMax.getInfo();
            RemoveById.getInfo();
            RemoveLower.getInfo();
            History.getInfo();
            GroupCountingByCreationDate.getInfo();
            FilterByHeight.getInfo();
            PrintUniqueLocation.getInfo();
            System.out.println("");
        }
    }

    private static class Info {
        static void getInfo() {
            System.out.println("Команда info: вывести в стандартный поток вывода информацию о коллекции");
        }

        static void info(HashSet<Person> base) {
            System.out.println("Тип коллекции: HashSet");
            System.out.println("Тип объектов в коллекции: Lab5.DataBases.Person");
            System.out.println("Количество элементов коллекции: " + base.size());
            System.out.println("Размер считываемого файла в байтах: " + DataBase.getFile().length());
            System.out.println("Время последней модификации файла: " + DataBase.getFile().lastModified());
            System.out.println("");
        }
    }

    private static class Show {
        static void getInfo() {
            System.out.println("Команда show: вывести в стандартный поток вывода " +
                    "все элементы коллекции в строковом представлении");
        }

        static void show(HashSet<Person> base) {
            System.out.println("Информация представлена в порядке: id; name; coordinate x; coordinate y; creationDate;" +
                    "height; pasportID; hairColor; nationality; location x; location y; location z");
            for (Person p : base)
                System.out.println(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                        + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                        + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                        p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
            System.out.println("");
        }
    }

    private static class Add {
        static void getInfo() {
            System.out.println("Команда add: добавить новый элемент в коллекцию");
        }

        static void add(DataBase dataBase) {
            Integer id = dataBase.id.get(0);
            dataBase.id.remove(0);
            System.out.println("Введите Имя: ");
            String name = in.nextLine();
            int cx;
            while (true) {
                try {
                    System.out.println("Введите координату x: ");
                    cx = Integer.parseInt(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неверно, попробуйте ещё раз");
                }
            }
            Double cy;
            while (true) {
                try {
                    System.out.println("Введите координату y: ");
                    cy = Double.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            if(Double.valueOf(-0.0).equals(cy)){
                cy = 0.0;
            }
            LocalDate creationDate = LocalDate.now();
            Float height;
            while (true) {
                try {
                    System.out.println("Введите height: ");
                    height = Float.valueOf(in.nextLine());
                    while(Float.parseFloat(height.toString()) - 0.00001 <= 0.0f){
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                        height = Float.valueOf(in.nextLine());
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            String passportID;
            while (true) {
                System.out.println("Введите passportID: ");
                passportID = in.nextLine();
                while (passportID.length() > 14) {
                    System.out.println("Слишком длинный passportId, попробуйте ещё раз: ");
                    passportID = in.nextLine();
                }
                if (DataBase.passportId.contains(passportID)) {
                    System.out.println("Такие данные уже есть, попробуйте ещё раз");
                } else {
                    DataBase.passportId.add(passportID);
                    break;
                }
            }
            Color hairColor;
            while (true) {
                try {
                    System.out.print("Введите hairColor. Вот доступные цвета: ");
                    System.out.println(Arrays.toString(Color.values()));
                    hairColor = Color.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неверно, попробуйте ещё раз");
                }
            }
            Country nationality;
            while (true) {
                try {
                    System.out.print("Введите nationality:. Вот доступные страны: ");
                    System.out.println(Arrays.toString(Country.values()));
                    nationality = Country.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неверно, попробуйте ещё раз");
                }
            }
            float lx;

            while (true) {
                try {
                    System.out.println("Введите Location x: ");
                    lx = Float.parseFloat(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            if(Float.parseFloat(String.valueOf(-0.0)) == lx){
                lx = 0.0f;
            }
            Long ly;
            while (true) {
                try {
                    System.out.println("Введите Location y: ");
                    ly = Long.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            Long lz;
            while (true) {
                try {
                    System.out.println("Введите Location z: ");
                    lz = Long.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            dataBase.base.add(new Person(id, name, cx, cy, creationDate, height, passportID, hairColor,
                    nationality, lx, ly, lz));
            System.out.println("Элемент добавлен в коллекцию");
            System.out.println("");
        }
    }

    private static class UpdateId {
        static void getInfo() {
            System.out.println("Команда update id: обновить значение элемента коллекции, id которого равен заданному");
        }
        static void updateId(DataBase dataBase){
            ArrayList<Person> temporary = new ArrayList<Person>(dataBase.base);
            temporary.sort(new SortById());
            System.out.println("Введите id элемента, который хотите изменить: ");
            Integer id = Integer.parseInt(in.nextLine());
            int number = BinSearch.binSearchPerson(temporary, id);
            while(number == -1){
                System.out.println("Элемента с таким id не существует, попробуйте ещё раз");
                number = BinSearch.binSearchPerson(temporary,Integer.parseInt(in.nextLine()));
            }
            System.out.println("Введите Имя: ");
            String name = in.nextLine();
            temporary.get(number).updateName(name);
            int cx;
            while (true) {
                try {
                    System.out.println("Введите координату x: ");
                    cx = Integer.parseInt(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            Double cy;
            while (true) {
                try {
                    System.out.println("Введите координату y: ");
                    cy = Double.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            if(Double.valueOf(-0.0).equals(cy)){
                cy = 0.0;
            }
            temporary.get(number).updateCoordinates(cx, cy);
            temporary.get(number).updateCreationDate(LocalDate.now());
            Float height;
            while (true) {
                try {
                    System.out.println("Введите height: ");
                    height = Float.valueOf(in.nextLine());
                    while(Float.parseFloat(height.toString()) - 0.00001 <= 0.0f){
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                        height = Float.valueOf(in.nextLine());
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            temporary.get(number).updateHeight(height);
            String passportID;
            Collections.sort(DataBase.passportId);
            int place = BinSearch.binSearchPassport(DataBase.passportId, temporary.get(number).getPassportID());
            while (true) {
                System.out.println("Введите passportID: ");
                passportID = in.nextLine();
                while (passportID.length() > 14) {
                    System.out.println("Слишком длинный passportId, попробуйте ещё раз: ");
                    passportID = in.nextLine();
                }
                if (DataBase.passportId.contains(passportID)) {
                    System.out.println("Такие данные уже есть, попробуйте ещё раз");
                } else {
                    temporary.get(number).updatePassportId(passportID);
                    DataBase.passportId.remove(place);
                    DataBase.passportId.add(passportID);
                    break;
                }
            }
            Color hairColor;
            while (true) {
                try {
                    System.out.print("Введите hairColor. Вот доступные цвета: ");
                    System.out.println(Arrays.toString(Color.values()));
                    hairColor = Color.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неверно, попробуйте ещё раз");
                }
            }
            temporary.get(number).updateHairColor(hairColor);
            Country nationality;
            while (true) {
                try {
                    System.out.print("Введите nationality. Вот доступные страны: ");
                    System.out.println(Arrays.toString(Country.values()));
                    nationality = Country.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неверно, попробуйте ещё раз");
                }
            }
            temporary.get(number).updateNationality(nationality);
            float lx;
            while (true) {
                try {
                    System.out.println("Введите Location x: ");
                    lx = Float.parseFloat(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            if(Float.parseFloat(String.valueOf(-0.0)) == lx){
                lx = 0.0f;
            }
            Long ly;
            while (true) {
                try {
                    System.out.println("Введите Location y: ");
                    ly = Long.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            Long lz;
            while (true) {
                try {
                    System.out.println("Введите Location z: ");
                    lz = Long.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            temporary.get(number).updateLocation(lx, ly, lz);
            dataBase.base = new HashSet<Person>(temporary);
            System.out.println("Элемент в коллекции обновлён");
            System.out.println("");
        }
    }

    private static class RemoveById {
        static void getInfo() {
            System.out.println("Команда remove_by_id: удалить элемент из коллекции по его id");
        }
        static void removeById(DataBase dataBase){
            ArrayList<Person> temporary = new ArrayList<Person>(dataBase.base);
            temporary.sort(new SortById());
            System.out.println("Введите id элемента, который хотите удалить: ");
            Integer id = Integer.parseInt(in.nextLine());
            int number = BinSearch.binSearchPerson(temporary, id);
            while(number == -1){
                System.out.println("Элемента с таким id не существует, попробуйте ещё раз");
                number = BinSearch.binSearchPerson(temporary,Integer.parseInt(in.nextLine()));
            }
            dataBase.id.add(id);
            String passportId = temporary.get(number).getPassportID();
            Collections.sort(DataBase.passportId);
            int place = BinSearch.binSearchPassport(DataBase.passportId, temporary.get(number).getPassportID());
            DataBase.passportId.remove(place);
            temporary.remove(number);
            dataBase.base = new HashSet<Person>(temporary);
            System.out.println("Элемент удалён из коллекции");
            System.out.println("");
        }
    }

    private static class Clear {
        static void getInfo() {
            System.out.println("Команда clear: очистить коллекцию");
        }
        static void clear(DataBase dataBase){
            for(Person p: dataBase.base){
                dataBase.id.add(p.getId());
            }
            dataBase.base.clear();
            DataBase.passportId.clear();
            System.out.println("Коллекция очищена");
            System.out.println("");
        }
    }

    private static class Save {
        static void getInfo() {
            System.out.println("Команда save: сохранить коллекцию в файл");
        }
        static void save(DataBase dataBase){
            try{
                FileWriter writer = new FileWriter(DataBase.getFile().toString(), false);
                String str;
                writer.write("id;name;coordinate x;coordinate y;creationDate;height;pasport ID;hairColor;nationality;" +
                        "location x;location y;location z");
                for(Person p: dataBase.base){
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
                for(Integer s: dataBase.id){
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
            System.out.println("");
        }
    }

    private static class ExecuteScript {
        static void getInfo() {
            System.out.println("Команда execute_script: : считать и исполнить скрипт из указанного файла. " +
                    "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в " +
                    "интерактивном режиме.");
        }

        static void executeScript(DataBase dataBase) {
            String line;
            File file;
            while (true) {
                try {
                    System.out.println("Введите путь до Файла");
                    file = new File(Controller.in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Что-то пошло не так. Попробуйте ещё раз.");
                }
            }
            if (DataBase.scriptsNames.indexOf(file) >= 0) {
                System.out.println("Ошибка возникновения рекурсии, данная команда не будет выполняться");
            } else {
                try {
                    changeScanner(file);
                    DataBase.scanners.push(in);
                    DataBase.scriptsNames.add(file);
                    while (in.hasNext()) {
                        Command(in.nextLine(), dataBase);
                    }
                    DataBase.scriptsNames.remove(DataBase.scriptsNames.size() - 1);
                    DataBase.scanners.pop();
                    try {
                        in = DataBase.scanners.peek();
                    } catch (Exception e) {
                        returnScanner();
                    }
                } catch (Exception e) {
                    System.out.println("Файла не существует, операция будет пропущена");
                }
            }
        }
    }

    private static class Exit {
        static void getInfo() {
            System.out.println("Команда exit: завершить программу (без сохранения в файл)");
        }
        static void exit(){
            System.out.println("Спасибо, что выбрали мою программу.");
            System.out.println("До свидания");
        }
    }

    private static class AddIfMax {
        static void getInfo() {
            System.out.println("Команда add_if_max: добавить новый элемент в коллекцию, если его значение " +
                    "превышает значение наибольшего элемента этой коллекции");
        }
        static void addIfMax(DataBase dataBase){
            System.out.println("Введите поле PassportId, и если оно больше максимального, то элемент будет добавлен");
            String passportId;
            while (true) {
                System.out.println("Введите passportID: ");
                passportId = in.nextLine();
                while ( passportId.length() > 14) {
                    System.out.println("Слишком длинный passportId, попробуйте ещё раз: ");
                    passportId = in.nextLine();
                }
                if (DataBase.passportId.contains(passportId)) {
                    System.out.println("Такие данные уже есть, попробуйте ещё раз");
                } else {
                    break;
                }
            }
            Collections.sort(DataBase.passportId);
            if(passportId.compareTo(DataBase.passportId.get(DataBase.passportId.size() - 1)) <= 0){
                System.out.println("Элемент меньше максимального, Команда завершена");
                System.out.println("");
            }
            else {
                DataBase.passportId.add(passportId);
                Integer id = dataBase.id.get(0);
                dataBase.id.remove(0);
                System.out.println("Введите Имя: ");
                String name = in.nextLine();
                int cx;
                while (true) {
                    try {
                        System.out.println("Введите координату x: ");
                        cx = Integer.parseInt(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                Double cy;
                while (true) {
                    try {
                        System.out.println("Введите координату y: ");
                        cy = Double.valueOf(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                LocalDate creationDate = LocalDate.now();
                Float height;
                while (true) {
                    try {
                        System.out.println("Введите height: ");
                        height = Float.valueOf(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                Color hairColor;
                while (true) {
                    try {
                        System.out.print("Введите hairColor. Вот некоторые из доступных параметров: ");
                        System.out.println(Arrays.toString(Color.values()));
                        hairColor = Color.valueOf(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неверно, попробуйте ещё раз");
                    }
                }
                Country nationality;
                while (true) {
                    try {
                        System.out.print("Введите nationality:. Вот некоторые из доступных параметров: ");
                        System.out.println(Arrays.toString(Country.values()));
                        nationality = Country.valueOf(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неверно, попробуйте ещё раз");
                    }
                }
                float lx;
                while (true) {
                    try {
                        System.out.println("Введите координату x: ");
                        lx = Float.parseFloat(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                Long ly;
                while (true) {
                    try {
                        System.out.println("Введите координату y: ");
                        ly = Long.valueOf(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                Long lz;
                while (true) {
                    try {
                        System.out.println("Введите координату y: ");
                        lz = Long.valueOf(in.nextLine());
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                dataBase.base.add(new Person(id, name, cx, cy, creationDate, height, passportId, hairColor,
                        nationality, lx, ly, lz));
                System.out.println("Элемент добавлен в коллекцию");
                System.out.println("");
            }
            }


    }

    private static class RemoveLower {
        static void getInfo() {
            System.out.println("Команда remove_lower: удалить из коллекции все элементы, меньшие, чем заданный");
        }
        static void removeLower(DataBase dataBase){
            System.out.println("Введите значение height, чтобы удалить все элементы, поле height " +
                    "которого ниже заданного");
            Float height;
            while (true) {
                try {
                    System.out.println("Введите height: ");
                    height = Float.valueOf(in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            for(Person p: dataBase.base){
                if(height - Float.parseFloat(p.getHeight().toString()) >= 0.000000001f){
                    dataBase.base.remove(p);
                }
            }
            System.out.println("Объекты удалены");
            System.out.println("");
        }
    }

    private static class History {
        static void getInfo() {
            System.out.println("Команда history: вывести последние 10 команд (без их аргументов)");
        }
        static void history(){
            for(int i = 0; i < DataBase.history.size(); i++){
                System.out.println(DataBase.history.peek());
                DataBase.history.add(DataBase.history.peek());
                DataBase.history.poll();
            }
            System.out.println("");
        }
    }

    private static class GroupCountingByCreationDate {
        static void getInfo() {
            System.out.println("Команда group_counting_by_creation_date: группировать элементы коллекции " +
                    "по значению поля creationDate, вывести количество элементов в каждой группе");
        }
        static void groupCountingByCreationDate(DataBase dataBase){
            ArrayList<Person> people = new ArrayList<Person>(dataBase.base);
            people.sort(new SortByLocalDate());
            LocalDate ld = people.get(0).getCreationDate();
            int i = 0;
            for(Person p: people){
                if(ld.compareTo(p.getCreationDate()) == 0){
                    i++;
                    System.out.println(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                            + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                            + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                            p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                }
                else{
                    ld = p.getCreationDate();
                    System.out.println("Колличество элеметнов: " + i);
                    i = 1;
                }
            }
            Person p = people.get(people.size() - 1);
            System.out.println(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                    + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                    + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                    p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
            System.out.println("Колличество элеметнов: " + i);
            System.out.println("");
        }
    }

    private static class FilterByHeight {
        static void getInfo() {
            System.out.println("Команда filter_by_height: вывести элементы, значение поля height которых равно заданному");
        }
        static void filterByHeight(DataBase dataBase){
            System.out.println("Введите значение height, чтобы вывести все элементы, поле height " +
                    "которого равно заданному");
            Float height;
            while (true) {
                try {
                    System.out.println("Введите height: ");
                    height = Float.valueOf(in.nextLine());
                    while(Float.parseFloat(height.toString()) - 0.00001 <= 0.0f){
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                        height = Float.valueOf(in.nextLine());
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                }
            }
            for(Person p: dataBase.base){
                if(Math.abs(height - Float.parseFloat(p.getHeight().toString())) <= 0.000000001f){
                    System.out.println(p.getId() + " " + p.getName() + " " + p.getCoordinates().getX() + " "
                            + p.getCoordinates().getY() + " " + p.getCreationDate() + " " + p.getHeight() + " "
                            + p.getPassportID() + " " + p.getHairColor() + " " + p.getNationality() + " " +
                            p.getLocation().getX() + " " + p.getLocation().getY() + " " + p.getLocation().getZ());
                }
            }
            System.out.println("Объекты выведены на экран");
            System.out.println("");
        }
    }

    private static class PrintUniqueLocation {
        static void getInfo() {
            System.out.println("Команда print_unique_location: вывести уникальные значения поля location");
        }
        static void printUniqueLocation(DataBase dataBase){
            HashSet<Location> locations = new HashSet<Location>();
            for(Person p: dataBase.base){
                locations.add(p.getLocation());
            }
            for(Location l: locations){
                System.out.println(l.getX() + " " + l.getY() + " " + l.getZ());
            }
            System.out.println("Вывод закончен");
            System.out.println("");
        }

    }
}
