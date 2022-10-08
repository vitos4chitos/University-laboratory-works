package Server;

import communication.*;
import Data.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;

public interface ServerCommands {

    default boolean uploadCollection() {
        try(Statement statement = Server.con.createStatement();
            ResultSet rs = statement.executeQuery("select * from people")) {
            DataBase.base.clear();
            while (rs.next()) {
                DataBase.base.add(adder(rs));
            }
            return true;
        } catch (SQLException throwables) {
            System.out.println("Неправильно введены данные, попробуйте ещё раз");
            return false;
        }

    }

    default Person adder(ResultSet rs) throws SQLException {
        Integer id = Integer.valueOf(rs.getString("person_id"));
        String name = rs.getString(1);
        int cx = Integer.parseInt(rs.getString(2));
        Double cy = Double.valueOf(rs.getString(3));
        int year = Integer.parseInt(rs.getString(4).split("-")[0]),
                day = Integer.parseInt(rs.getString(4).split("-")[2]),
                month = Integer.parseInt(rs.getString(4).split("-")[1]);
        LocalDate creationDate = LocalDate.of(year, month, day);
        Float height = Float.valueOf(rs.getString(5));
        String passportID = rs.getString(6);
        Color hairColor = Color.valueOf(rs.getString(7));
        Country nationality = Country.valueOf(rs.getString(8));
        float lx = Float.parseFloat(rs.getString(9));
        Long ly = Long.valueOf(rs.getString(10));
        Long lz = Long.valueOf(rs.getString(11));
        String user = rs.getString(12);
        return new Person(id, name, cx, cy, creationDate, height, passportID, hairColor, nationality, lx, ly, lz, user);
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
        return packet;
    }

    default Packet invoker(Packet packet) {
        Packet answer;
        switch (packet.getCommand()) {
            case "help":
                DataBase.addToHistory("help by " + packet.getLogin());
                System.out.println("Пакет успешно обработан, подготовка к отправке ответа");
                return Server.Help.help();
            case "info":
                DataBase.addToHistory("info by " + packet.getLogin());
                System.out.println("Пакет успешно обработан, подготовка к отправке ответа");
                return Server.Info.info();
            case "show":
                DataBase.addToHistory("show by " + packet.getLogin());
                return Server.Show.show();
            case "add":
                DataBase.addToHistory("add by " + packet.getLogin());
                answer = Server.Add.add(packet.getPersons());
                if(answer.getMode())
                    uploadCollection();
                return answer;
            case "update id":
                DataBase.addToHistory("update id by " + packet.getLogin());
                answer = Server.UpdateId.updateId(packet.getPersons(), packet.getInfo(), packet.getLogin());
                if(answer.getMode()){
                    uploadCollection();
                }
                return answer;
            case "remove_by_id":
                DataBase.addToHistory("remove_by_id by " + packet.getLogin());
                answer =  Server.RemoveById.removeById(packet.getInfo(), packet.getLogin());
                if(answer.getMode()){
                    uploadCollection();
                }
                return answer;
            case "clear":
                DataBase.addToHistory("clear by " + packet.getLogin());
                answer = Server.Clear.clear(packet.getLogin());
                if(answer.getMode()){
                    uploadCollection();
                }
                return answer;
            case "history":
                DataBase.addToHistory("history by " + packet.getLogin());
                return Server.History.history();
            case "add_if_max":
                DataBase.addToHistory("add_if_max by " + packet.getLogin());
                answer = Server.AddIfMax.addIfMax(packet.getPersons());
                if(answer.getMode()){
                    uploadCollection();
                }
                return answer;
            case "remove_lower":
                DataBase.addToHistory("remove_lower by " + packet.getLogin());
                answer = Server.RemoveLower.removeLower(packet.getInfo(), packet.getLogin());
                if(answer.getMode()){
                    uploadCollection();
                }
                return answer;
            case "filter_by_height":
                DataBase.addToHistory("filter_by_height by " + packet.getLogin());
                return Server.FilterByHeight.filterByHeight(packet.getInfo());
            case "print_unique_location":
                DataBase.addToHistory("print_unique_location by " + packet.getLogin());
                return Server.PrintUniqueLocation.printUniqueLocation();
            case "group_counting_by_creation_date":
                DataBase.addToHistory("group_counting_by_creation_date by " + packet.getLogin());
                return Server.GroupCountingByCreationDate.groupCountingByCreationDate();
            case "execute_script":
                DataBase.addToHistory("execute_script by " + packet.getLogin());
                return Server.ExecuteScript.executeScript(packet.getInfo());
            case "over":
                return Server.Over.over(packet);
            case "sign":
                return Server.RegLogIn.LogIn(packet.getLogin(), packet.getPassword());
            case "reg":
                return Server.RegLogIn.Regin(packet.getLogin(), packet.getPassword());
            default:
                System.out.println("Такой команды нет");
                return null;
        }
    }

}
