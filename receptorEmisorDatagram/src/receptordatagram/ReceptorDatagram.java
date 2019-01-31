package receptordatagram;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 *
 * @author mbacelofernandez
 */
public class ReceptorDatagram {

    public static void main(String[] args) {
        try {
            System.out.println("Creando socket datagrama");

            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            DatagramSocket datagramSocket = new DatagramSocket(addr);

            System.out.println("Recibiendo mensaje");

            byte[] mensaje = new byte[25];
            DatagramPacket datagrama1 = new DatagramPacket(mensaje, 25);
            datagramSocket.receive(datagrama1);

            System.out.println("Mensaje recibido: " + new String(mensaje));

            System.out.println("Enviando mensaje");


            datagramSocket.send(datagrama1);

            System.out.println("Mensaje enviado");

            System.out.println("Cerrando el socket datagrama");

            datagramSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
