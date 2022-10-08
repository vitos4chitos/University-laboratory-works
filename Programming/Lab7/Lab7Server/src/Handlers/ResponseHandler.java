package Handlers;

import communication.Packet;
import communication.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

public class ResponseHandler implements Runnable {
    private SelectionKey key;
    private Packet packet;
    private ByteBuffer buffer = ByteBuffer.allocate(4096);
    public ResponseHandler(SelectionKey key, Packet packet) {
        this.key = key;
        this.packet = packet;
    }

    @Override
    public void run() {
        try{
            DatagramChannel channel = (DatagramChannel) key.channel();
            buffer.clear();
            buffer.put(Serializer.serialize(packet));
            buffer.flip();
            channel.send(buffer, packet.getToBack());
            System.out.println("Ответ успешно отправлен на адресс " + packet.getToBack());
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}