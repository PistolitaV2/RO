import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class plusd {
    public static void main(String args[]) { // porto N
        System.out.println("x= " + args.length);
        if (args.length != 2) {
            System.out.println("Numero de argumentos erróneo");
            return;
        }
        int num_server = Integer.parseInt(args[1]);
        int port_server = Integer.parseInt(args[0]);
        int num_cliente;
        DatagramSocket serverSocket = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        byte[] buffer = new byte[1024];
        // ByteBuffer wraped = ByteBuffer.wrap(buffer);
        System.out.println("Espero cliente");
        while (true) {
            try {
                DatagramSocket socketsv = new DatagramSocket(port_server);
                DatagramPacket consulta = new DatagramPacket(buffer, buffer.length);
                socketsv.receive(consulta);
                buffer = consulta.getData();
                ByteBuffer wraped = ByteBuffer.wrap(buffer);
                num_cliente = wraped.getInt();
                System.out.println("Número cliente: " + num_cliente);
                num_cliente = num_server + num_cliente;
                wraped.clear();
                System.out.println("Numero total= " + num_cliente);
                buffer = wraped.putInt(num_cliente).array();

                DatagramPacket resposta = new DatagramPacket(buffer, buffer.length, consulta.getAddress(),
                        consulta.getPort());
                System.out.println("Resposta creada");
                socketsv.send(resposta);
                socketsv.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}