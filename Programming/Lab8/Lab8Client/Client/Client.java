package Client;
import Data.Person;
import communication.Packet;
import communication.Serializer;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.Stack;

public class Client implements ClientCommands {

    private int port;
    private DatagramSocket socket;
    private static Stack<Scanner> scanners = new Stack<Scanner>();
    static Scanner in = new Scanner(System.in);
    {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    private InetAddress address;
    private InetSocketAddress socketAddress;
    private ByteBuffer buffer = ByteBuffer.allocate(4096);
    private String command;
    private String login, password;


    public Packet run(String command, Person person, String info) {
            try {
                Packet packet;
                packet = invoker(command, login, password, person, info);
                packet = sendReceive(packet);
                buffer.clear();
                buffer.put(new byte[4096]);
                return packet;
            }
            catch (IOException e){
                System.out.println("Фу, быдлокодер");
                return null;
            } catch (ClassNotFoundException e) {
                System.out.println("Сорри, класс не найден");
                return null;
            }
        }

    private void scripter(String command){
        if(command.equals("execute_script")){
            String line;
            File file;
            while (true) {
                try {
                    System.out.println("Введите путь до Файла");
                    file = new File(Client.in.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Что-то пошло не так. Попробуйте ещё раз.");
                }
            }
            line = file.getPath();
            Packet packet = new Packet(true, "execute_script", line + " " + login, null, login, password);
            try{
                packet = sendReceive(packet);
                packet.giveAnswer();
                if(packet.getMode()){
                    Client.scanners.push(in);
                    in = new Scanner(file);
                }
                buffer.clear();
                buffer.put(new byte[4096]);
            }
            catch (IOException e){
                System.out.println("Фу, быдлокодер");
            } catch (ClassNotFoundException e) {
                System.out.println("Сорри, класс не найден");
            }
        }
        else{
            Packet packet = new Packet(true, "over", null, null, login, password);
            try{
                packet = sendReceive(packet);
                packet.giveAnswer();
                buffer.clear();
                buffer.put(new byte[4096]);
                in = Client.scanners.peek();
            }
            catch (IOException e){
                System.out.println("Фу, быдлокодер");
            } catch (ClassNotFoundException e) {
                System.out.println("Сорри, класс не найден");
            }
        }
    }

    public boolean setSocketAddress(int portt, String addr){
        try{
            port = portt;
            address = InetAddress.getByName(addr);
            System.out.println("Создаём адресс соединения");
            socketAddress = new InetSocketAddress(address, port);
            System.out.println("Адресс соединения успешно создан");
            return true;
        }
        catch (Exception e){
            System.out.println("Ваши данные были введены неверно, попробуйте ещё раз");
            return false;
        }

    }

    public boolean logRegIn(String command, String log, String pas) {
        login = log;
        password = pas;
        if(command.equals("reg")){
                Packet packet = new Packet(true, "reg", null, null, login, makeHash(password));
                try {
                    packet = sendReceive(packet);
                    return packet.getMode();
                } catch (IOException e) {
                    return false;
                } catch (ClassNotFoundException e) {
                    return false;
                }
            }
        else{
            Packet packet = new Packet(true, "sign", null, null, login, makeHash(password));
            try {
                packet = sendReceive(packet);
                return packet.getMode();
            } catch (IOException e) {
                return false;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }
    }

    private Packet sendReceive(Packet packet) throws IOException, ClassNotFoundException {
        DatagramPacket datagramPacket = packer(packet, socketAddress);
        System.out.println("Пакет успешно сформирован, отправляем запрос на сервер...");
        socket.send(datagramPacket);
        System.out.println("Ожидаем ответа от сервера");
        DatagramPacket output = new DatagramPacket(buffer.array(), buffer.limit());
        Thread checker = new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(5000L);
                    System.out.println("Время ожидания пакета превышено, попробуйте позже отправить запрос. " +
                            "Отключаю клиент");
                    System.exit(0);
                } catch (InterruptedException ignored) {

                }
            }
        };
        checker.start();
        socket.receive(output);
        checker.interrupt();
        packet = (Packet) Serializer.deserialize(output.getData());
        System.out.println("Ответ получен");
        return packet;
    }

    public String getLogin(){
        return login;
    }
}
