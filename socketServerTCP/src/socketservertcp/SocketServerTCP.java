package socketservertcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Usando o porto 6666, conversacion de 3 mensajes por cada aplicacion.
 *
 * @author mbacelofernandez
 */
public class SocketServerTCP {

    public static void main(String[] args) throws IOException {

        //Creamos el socket del servidor
        System.out.println("Creando socket servidor");
        ServerSocket serverSocket = new ServerSocket();

        //Hacemos que el socket del servidor escuche en la direcion deseada
        System.out.println("Realizando el bind");
        InetSocketAddress addr = new InetSocketAddress("localhost", 6666);
        serverSocket.bind(addr);

        //El socket del servidor se queda escuchando en la direccion deseada.
        //En cuanto reciba una conexion se crea el objeto Socket
        System.out.println("Aceptando conexiones");
        Socket newSocket = serverSocket.accept();

        //Se crea un stream que recibira los datos que envie el cliente
        System.out.println("Conexión recibida");

        //hacemos un bucle para recibir 3 mensajes
        for (int i = 1; i <= 3; i++) {
            InputStream input = newSocket.getInputStream();

            //Creamos una variable que nos permitira visualizar el mensaje.
            //Grabamos en esa variable lo que nos llega en el input
            byte[] mensaje = new byte[27];
            input.read(mensaje);

            //Mostramos el mensaje
            System.out.println("Mensaje recibido: " + new String(mensaje));
        }

        //hacemos un bucle para enviar 3 mensajes
        for (int i = 1; i <= 3; i++) {

            OutputStream os = newSocket.getOutputStream();
            //enviamos el mensaje
            System.out.println("Enviando mensaje");
            String mensaje = "mensaje desde el servidor";
            os.write(mensaje.getBytes());
            System.out.println("Mensaje enviado");
        }

        //Se cierra el socket que lee 
        System.out.println("Cerrando el nuevo socket");
        newSocket.close();

        //Se cierra el socket Servidor
        System.out.println("Cerrando el socket servidor");
        serverSocket.close();

        System.out.println("Terminado");
    }

}
