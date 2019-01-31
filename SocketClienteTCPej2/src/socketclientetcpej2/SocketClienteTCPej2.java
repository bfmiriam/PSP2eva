package socketclientetcpej2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Programa o cliente para que envíe unha lista de 5 números ao servidor. O
 * servidor os sumará e devolverá o resultado ao cliente, que o amosará por
 * pantalla.
 *
 * @author mbacelofernandez
 */
public class SocketClienteTCPej2 {

    public static void main(String[] args) throws IOException {
        
        //Se crea el cocket del cliente
        System.out.println("Creando socket cliente");
        Socket clienteSocket = new Socket();

        //Se establece la direccion del socket cliente
        System.out.println("Estableciendo la conexión");
        InetSocketAddress direccion = new InetSocketAddress("localhost", 6666);
        clienteSocket.connect(direccion);
        
        //cadena de números que vamos a enviar
        String mensaje = "1 2 3 4 5 ";
        
        //lo necesitamos para enviar datos
        OutputStream output = clienteSocket.getOutputStream();

        //Enviamos el mensaje
        System.out.println("Enviando mensaje");
        output.write(mensaje.getBytes());
        System.out.println("Mensaje enviado: " + mensaje);

        //lo necesitamos para recibir datos
        InputStream input = clienteSocket.getInputStream();

        //Creamos una variable que nos permitira visualizar el mensaje.
        //Grabamos en esa variable lo que nos llega en el input
        byte[] mensaxe = new byte[27];
        input.read(mensaxe);

        //Mostramos el resultado de la suma
        System.out.println("Mensaje recibido: " + new String(mensaxe));

        //Cerramos el socket
        System.out.println("Cerrando el socket cliente");
        clienteSocket.close();

        System.out.println("Terminado");
    }

}
