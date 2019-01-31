package socketclientetcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Usando o porto 6666, conversacion de 3 mensajes por cada aplicacion.
 *
 * @author mbacelofernandez
 */
public class SocketClienteTCP {

    public static void main(String[] args) throws IOException {
        //Se crea el cocket del cliente
        System.out.println("Creando socket cliente");
        Socket clienteSocket = new Socket();

        //Se establece la direccion del socket cliente
        System.out.println("Estableciendo la conexión");
        InetSocketAddress direccion = new InetSocketAddress("localhost", 6666);
        clienteSocket.connect(direccion);

        // lo necesitamos para enviar datos
        OutputStream output = clienteSocket.getOutputStream();

        //Enviamos 3 mensajes
        System.out.println("Enviando mensaje");
        String mensaje = "mensaje desde el cliente";
        output.write(mensaje.getBytes());
        System.out.println("Mensaje enviado");

        System.out.println("Enviando mensaje");
        String mensaje2 = "mensaje 2 desde el cliente";
        output.write(mensaje2.getBytes());
        System.out.println("Mensaje enviado");

        System.out.println("Enviando mensaje");
        String mensaje3 = "mensaje 3 desde el cliente";
        output.write(mensaje3.getBytes());
        System.out.println("Mensaje enviado");

        //hacemos un bucle para recibir 3 mensajes
        for (int i = 1; i <= 3; i++) {
            // lo necesitamos para recibir datos
            InputStream input = clienteSocket.getInputStream();

            //Creamos una variable que nos permitira visualizar el mensaje.
            //Grabamos en esa variable lo que nos llega en el input
            byte[] mensaxe = new byte[27];
            input.read(mensaxe);

            //Mostramos el mensaje
            System.out.println("Mensaje recibido: " + new String(mensaxe));
        }

        //Cerramos el socket
        System.out.println("Cerrando el socket cliente");
        clienteSocket.close();

        System.out.println("Terminado");
    }

}
