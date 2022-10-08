package Handlers;

import Server.ServerCommands;
import communication.Packet;
import communication.Serializer;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.Callable;

public class RequestHandler implements Callable<Packet> {
    private DatagramChannel dc;
    private ByteBuffer output = ByteBuffer.allocate(4096);
    ServerCommands commands;

    public RequestHandler(DatagramChannel dc, ServerCommands commands) {
        this.dc = dc;
        this.commands = commands;
    }

    @Override
    public Packet call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        SocketAddress socketAddress = dc.receive(output);
        System.out.println("Пакет принят в обрабтку");
        Packet packet = commands.opener(output);
        System.out.println("Это Request и я получил: " + packet.getCommand() + " от " + packet.getLogin());
        packet.setToBack(socketAddress);
        output.clear();
        return packet;
    }
}
