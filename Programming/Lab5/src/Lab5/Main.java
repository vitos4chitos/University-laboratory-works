package Lab5;

import Lab5.Controller.Controller;
import Lab5.DataBases.DataBase;
import Lab5.Methods.Adder;

import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        Scanner scanner;
        String pathname;
        while (true) {
            try {
                //C:/Users/bezym/Desktop/Laba5/lol.csv
                pathname = args[0];
                File file = new File(pathname);
                scanner = new Scanner(file);
                String line = scanner.nextLine();
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    dataBase.base.add(Adder.addPerson(line));
                }
                break;
            } catch (IOException e) {
                System.out.println("Что-то не так с файлом, попробуйте заново.");
                args[0] = Controller.in.nextLine();
            }
        }
        dataBase.changeFileName(pathname);
        String command = "";
        while (!command.equals("exit")){
            System.out.print("Введите команду: ");
            command = Controller.in.nextLine();
            Controller.Command(command, dataBase);
            for(File f: DataBase.scriptsNames){
                System.out.println(f.getName());
            }
        }
    }
}
