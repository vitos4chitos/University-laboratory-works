package Handlers;

import Server.ServerCommands;
import communication.Packet;

import java.net.SocketAddress;
import java.util.concurrent.Callable;

public class CommandHandler implements Callable<Packet> {
    private ServerCommands commands;
    private Packet packet;
    private SocketAddress socketAddress;

    public CommandHandler(Packet packet, ServerCommands commands) {
        this.commands = commands;
        this.packet = packet;
    }

    @Override
    public Packet call() {
        System.out.println("Выполняю команду " + packet.getCommand() + " от " + packet.getLogin());
        socketAddress = packet.getToBack();
        Packet answer = commands.invoker(packet);
        answer.setToBack(socketAddress);
        return answer;
    }
}