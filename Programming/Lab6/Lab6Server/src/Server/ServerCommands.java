package Server;

import communication.*;
import Data.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Scanner;

public interface ServerCommands {

    default boolean uploadCollection(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            DataBase.changeFileName(path);
            String line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                DataBase.base.add(adder(line));
            }
            System.out.println("Коллекция успешно выгружена.");
            return true;
        } catch (IOException e) {
            System.out.println("Что-то не так с файлом, попробуйте заново.");
            return false;
        }
    }

    default Person adder(String line) {
        String[] property = line.split(";");
        Integer id = Integer.valueOf(property[0]);
        String name = property[1];
        int cx = Integer.parseInt(property[2]);
        Double cy = Double.valueOf(property[3]);
        int year = Integer.parseInt(property[4].split("-")[0]),
                day = Integer.parseInt(property[4].split("-")[2]),
                month = Integer.parseInt(property[4].split("-")[1]);
        LocalDate creationDate = LocalDate.of(year, month, day);
        Float height = Float.valueOf(property[5]);
        String passportID = property[6];
        Color hairColor = Color.valueOf(property[7]);
        Country nationality = Country.valueOf(property[8]);
        float lx = Float.parseFloat(property[9]);
        Long ly = Long.valueOf(property[10]);
        Long lz = Long.valueOf(property[11]);
        return new Person(id, name, cx, cy, creationDate, height, passportID, hairColor, nationality, lx, ly, lz);
    }

    default Packet opener(ByteBuffer output) {
        Packet packet = null;
        try {
            packet = (Packet) Serializer.deserialize(output.array());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return invoker(packet);
    }

    default Packet invoker(Packet packet) {
        switch (packet.getCommand()) {
            case "help":
                DataBase.addToHistory("help");
                System.out.println("Пакет успешно обработан, подготовка к отправке ответа");
                return Server.Help.help();
            case "info":
                DataBase.addToHistory("info");
                System.out.println("Пакет успешно обработан, подготовка к отправке ответа");
                return Server.Info.info();
            case "show":
                DataBase.addToHistory("show");
                return Server.Show.show();
            case "add":
                DataBase.addToHistory("add");
                return Server.Add.add(packet.getPersons());
            case "update id":
                DataBase.addToHistory("update id");
                return Server.UpdateId.updateId(packet.getPersons(), packet.getInfo());
            case "remove_by_id":
                DataBase.addToHistory("remove_by_id");
                return Server.RemoveById.removeById(packet.getInfo());
            case "clear":
                DataBase.addToHistory("clear");
                return Server.Clear.clear();
            case "history":
                DataBase.addToHistory("history");
                return Server.History.history();
            case "add_if_max":
                DataBase.addToHistory("add_if_max");
                return Server.AddIfMax.addIfMax(packet.getPersons());
            case "remove_lower":
                DataBase.addToHistory("remove_lower");
                return Server.RemoveLower.removeLower(packet.getInfo());
            case "filter_by_height":
                DataBase.addToHistory("filter_by_height");
                return Server.FilterByHeight.filterByHeight(packet.getInfo());
            case "print_unique_location":
                DataBase.addToHistory("print_unique_location");
                return Server.PrintUniqueLocation.printUniqueLocation();
            case "group_counting_by_creation_date":
                DataBase.addToHistory("group_counting_by_creation_date");
                return Server.GroupCountingByCreationDate.groupCountingByCreationDate();
            case "execute_script":
                DataBase.addToHistory("execute_script");
                return Server.ExecuteScript.executeScript(packet.getInfo());
            case "over":
                return Server.Over.over();
            default:
                System.out.println("Такой команды нет");
                return null;
        }
    }
}
