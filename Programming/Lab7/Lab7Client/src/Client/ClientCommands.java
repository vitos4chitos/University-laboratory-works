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
    default Packet invoker(String command, String login, String password){
        switch (command) {
            case "help":
                return new Packet(true, "help", null, null, login, makeHash(password));
            case "info":
                return new Packet(true, "info", null, null, login, makeHash(password));
            case "show":
                return new Packet(true, "show", null, null, login, makeHash(password));
            case "add":
                System.out.println("Переходим к заполнению данных для Data.Person");
                return new Packet(true, "add", null, makePerson(login), login, makeHash(password));
            case "update id":
                System.out.println("Введите id");
                Integer id = Integer.parseInt(Client.in.nextLine());
                System.out.println("Переходим к заполнению данных для Data.Person");
                return new Packet(true, "update id", id.toString(), makePerson(login), login, makeHash(password));
            case "remove_by_id":
                System.out.println("Введите id");
                Integer ID = Integer.parseInt(Client.in.nextLine());
                return new Packet(true, "remove_by_id", ID.toString(), null, login, makeHash(password));
            case "clear":
                return new Packet(true, "clear", null, null, login, makeHash(password));
            case "history":
                return new Packet(true, "history", null, null, login, makeHash(password));
            case "add_if_max":
                System.out.println("Сравнение происходит по полю Coordinate x");
                System.out.println("Переходим к заполнению данных для Data.Person");
                return new Packet(true, "add_if_max", null, makePerson(login), login, makeHash(password));
            case "remove_lower":
                System.out.println("Сравнение происходит по полю Height");
                Float height;
                while (true) {
                    try {
                        System.out.println("Введите height: ");
                        height = Float.valueOf(Client.in.nextLine());
                        while(Float.parseFloat(height.toString()) - 0.00001 <= 0.0f){
                            System.out.println("Данные введены неправильно, попробуйте ещё раз");
                            height = Float.valueOf(Client.in.nextLine());
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                return new Packet(true, "remove_lower", height.toString(),null, login, makeHash(password));
            case "filter_by_height":
                Float Height;
                while (true) {
                    try {
                        System.out.println("Введите height: ");
                        Height = Float.valueOf(Client.in.nextLine());
                        while(Float.parseFloat(Height.toString()) - 0.00001 <= 0.0f){
                            System.out.println("Данные введены неправильно, попробуйте ещё раз");
                            Height = Float.valueOf(Client.in.nextLine());
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    }
                }
                return new Packet(true, "filter_by_height", Height.toString(),null, login, makeHash(password));
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

    default Person makePerson(String user){
        System.out.println("Введите Имя: ");
        String name = Client.in.nextLine();
        int cx;
        while (true) {
            try {
                System.out.println("Введите координату x: ");
                cx = Integer.parseInt(Client.in.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Данные введены неверно, попробуйте ещё раз");
            }
        }
        Double cy;
        while (true) {
            try {
                System.out.println("Введите координату y: ");
                cy = Double.valueOf(Client.in.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Данные введены неправильно, попробуйте ещё раз");
            }
        }
        if(Double.valueOf(-0.0).equals(cy)){
            cy = 0.0;
        }
        Float height;
        while (true) {
            try {
                System.out.println("Введите height: ");
                height = Float.valueOf(Client.in.nextLine());
                while(Float.parseFloat(height.toString()) - 0.00001 <= 0.0f){
                    System.out.println("Данные введены неправильно, попробуйте ещё раз");
                    height = Float.valueOf(Client.in.nextLine());
                }
                break;
            } catch (Exception e) {
                System.out.println("Данные введены неправильно, попробуйте ещё раз");
            }
        }
        String passportID;
        System.out.println("Введите passportID: ");
        passportID = Client.in.nextLine();
            while (passportID.length() > 14) {
                System.out.println("Слишком длинный passportId, попробуйте ещё раз: ");
                passportID = Client.in.nextLine();
            }
        Color hairColor;
        while (true) {
            try {
                System.out.print("Введите hairColor. Вот доступные цвета: ");
                System.out.println(Arrays.toString(Color.values()));
                hairColor = Color.valueOf(Client.in.nextLine());
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
                nationality = Country.valueOf(Client.in.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Данные введены неверно, попробуйте ещё раз");
            }
        }
        float lx;

        while (true) {
            try {
                System.out.println("Введите Data.Location x: ");
                lx = Float.parseFloat(Client.in.nextLine());
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
                System.out.println("Введите Data.Location y: ");
                ly = Long.valueOf(Client.in.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Данные введены неправильно, попробуйте ещё раз");
            }
        }
        Long lz;
        while (true) {
            try {
                System.out.println("Введите Data.Location z: ");
                lz = Long.valueOf(Client.in.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Данные введены неправильно, попробуйте ещё раз");
            }
        }
        return new Person(null, name, cx, cy, null, height, passportID, hairColor,
                nationality, lx, ly, lz, user);
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
