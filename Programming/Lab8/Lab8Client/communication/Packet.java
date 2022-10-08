package communication;

import Data.*;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.*;

public class Packet implements Serializable {

    private boolean mode;
    private String command;
    private Person person;
    private ArrayList<String> answer = new ArrayList<>();
    private String info, login, password;
    SocketAddress toBack;


    public boolean getMode(){
        return mode;
    }
    public String getCommand(){
        return command;
    }
    public Person getPersons(){
        return person;
    }
    public String getInfo(){
        return info;
    }

    public Packet(boolean mode, String command, String info, Person person){
        this.command = command;
        this.mode = mode;
        this.info = info;
        this.person = person;
    }

    public Packet(boolean mode, String command, String info, Person person, String login, String password){
        this.command = command;
        this.mode = mode;
        this.info = info;
        this.person = person;
        this.login = login;
        this.password = password;
    }

    public void cleanAnswer(){
        answer.clear();
    }
    public void addAnswer(String s){
        answer.add(s);
    }
    public ArrayList<String> giveAnswer(){
        return answer;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public SocketAddress getToBack(){
        return toBack;
    }
    public void setToBack(SocketAddress socketAddress){
        toBack = socketAddress;
    }
}