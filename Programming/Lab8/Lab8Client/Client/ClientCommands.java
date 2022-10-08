package Client;

import Data.Color;
import Data.Country;
import Data.Person;
import communication.Packet;
import communication.Serializer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public interface ClientCommands {
    default Packet invoker(String command, String login, String password, Person person, String info){
        switch (command) {
            case "help":
                return new Packet(true, "help", null, null, login, makeHash(password));
            case "info":
                return new Packet(true, "info", null, null, login, makeHash(password));
            case "show":
                return new Packet(true, "show", null, null, login, makeHash(password));
            case "add":
                return new Packet(true, "add", null, person, login, makeHash(password));
            case "update id":
                return new Packet(true, "update id", info, person, login, makeHash(password));
            case "remove_by_id":
                return new Packet(true, "remove_by_id", info, null, login, makeHash(password));
            case "clear":
                return new Packet(true, "clear", null, null, login, makeHash(password));
            case "history":
                return new Packet(true, "history", null, null, login, makeHash(password));
            case "add_if_max":
                return new Packet(true, "add_if_max", null, person, login, makeHash(password));
            case "remove_lower":
                return new Packet(true, "remove_lower", info,null, login, makeHash(password));
            case "filter_by_height":
                return new Packet(true, "filter_by_height", info,null, login, makeHash(password));
            case "print_unique_location":
                return new Packet(true, "print_unique_location", null,null, login, makeHash(password));
            case "group_counting_by_creation_date":
                return new Packet(true, "group_counting_by_creation_date", null,null, login, makeHash(password));
            case "execute_script":
                return null;
            case "exit":
                System.out.println("Спасибо, что выбрали моё приложение. До свидания.");
                System.exit(0);
            default:
                System.out.println("Такой команды нет, попробуйте ещё раз");
                return new Packet(false, null, null, null);

        }
    }

    default DatagramPacket packer(Packet packet, SocketAddress socketAddress) throws IOException {
        return new DatagramPacket(Serializer.serialize(packet), Serializer.serialize(packet).length, socketAddress);
    }

    default String makeHash(String password){
        String hash;
        try {
            MessageDigest md2 = MessageDigest.getInstance("MD2");
            byte[] bytes = md2.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte b: bytes){
                builder.append(String.format("%02X", b));
            }
            hash = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return hash;

    }

}
