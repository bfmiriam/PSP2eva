package socketservertcpej2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Programa o cliente para que envíe unha lista de 5 números ao servidor. O
 * servidor os sumará e devolverá o resultado ao cliente, que o amosará por
 * pantalla.
 *
 * @author mbacelofernandez
 */
public class SocketServerTCPej2 {

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
        System.out.println("Conexión recibida");
        
        //Se crea un stream que recibira los datos que envie el cliente
        InputStream input = newSocket.getInputStream();
        
        //creamos una variable para recoger el valor de la suma
        int resultado = 0;
        
        //Creamos una variable que nos permitira visualizar el mensaje.
        //Grabamos en esa variable lo que nos llega en el input
        byte[] mensaje = new byte[25];
        input.read(mensaje);
        
        //pasamos el mensaje recibido a una variable tipo String
        String cadena = new String(mensaje);
        //separamos los numeros de la cadena
        String[] numeros = cadena.split(" ");
        
        //hacemos un bucle que coge los numeros recibidos y los suma
        for (int i = 0; i < numeros.length - 1; i++) {
            String number = numeros[i];
            int num = Integer.valueOf(number);
            resultado = resultado + num;
        }

        //Mostramos la suma de los numeros recibidos
        System.out.println("Suma: " + resultado);
        
        //enviamos al cliente el resultado de la suma realizada 
        OutputStream os = newSocket.getOutputStream();
        System.out.println("Enviando mensaje");
        os.write(String.valueOf(resultado).getBytes());
        System.out.println("Mensaje enviado");

        //Se cierra el socket que lee 
        System.out.println("Cerrando el nuevo socket");
        newSocket.close();

        //Se cierra el socket Servidor
        System.out.println("Cerrando el socket servidor");
        serverSocket.close();

        System.out.println("Terminado");
    }

}
