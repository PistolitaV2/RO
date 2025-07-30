import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;

public class plus {
    public static void main(String args[]) { // IP_servidor puerto_destino M timeout
        if ((args.length != 4)) {
            System.out.println("Numero de argumentos erróneo");
            return;
        }
        // InetAddress IP_servidor = InetAddress.getByName(args[0]);
        int port_out = Integer.parseInt(args[1]);
        int num = Integer.parseInt(args[2]);
        int timeoutmilis = (Integer.parseInt(args[3])) * 1000;
        byte[] buffer = new byte[1024];
        ByteBuffer wraped = ByteBuffer.wrap(buffer);

        try {
            DatagramSocket socketc = new DatagramSocket();
            InetAddress IP_servidor = InetAddress.getByName(args[0]);
            socketc.setSoTimeout(timeoutmilis);
            buffer = wraped.putInt(num).array();
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, IP_servidor, port_out);
            DatagramPacket resposta = new DatagramPacket(buffer, buffer.length);
            socketc.send(request);

            socketc.receive(resposta);
            buffer = resposta.getData();
            ByteBuffer wrapedres = ByteBuffer.wrap(buffer);
            int num_recv = wrapedres.getInt();
            System.out.println("Numero enviado= " + num);
            System.out.println("Numero recibido= " + num_recv);
            wraped.clear();
            wrapedres.clear();
            socketc.close();
        } catch (SocketTimeoutException time) {
            System.out.println("Timeout excedido (" + timeoutmilis + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}