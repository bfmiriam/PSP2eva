package calculadoraservidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *Realiza una calculadora cliente servidor
 * @author mbacelofernandez
 */
public class CalculadoraServidor {

    public static void main(String[] args) throws IOException {
        
        //Creamos el socket del servidor
        System.out.println("Creando socket servidor");
        ServerSocket serverSocket = new ServerSocket();

        //Hacemos que el socket del servidor escuche en la direcion deseada
        System.out.println("Realizando el bind");
        InetSocketAddress addr = new InetSocketAddress("localhost", 6666);
        serverSocket.bind(addr);

        //El socket del servidor se queda escuchando en la direccion deseada.
        //En cuenato reciba una conexion se crea el objeto Socket
        System.out.println("Aceptando conexiones");
        Socket newSocket = serverSocket.accept();

        //Se crea un stream que recibira los datos que envie el cliente
        System.out.println("Conexión recibida");

        InputStream input = newSocket.getInputStream();
        boolean encendido=true;
        while(encendido==true){
        double resultado = 0;
        
        //Creamos una variable que nos permitira visualizar el mensaje.
        //Grabamos en esa variable lo que nos llega en el input
        byte[] mensaje = new byte[25];
        input.read(mensaje);
        
        String cadena = new String(mensaje);
        String[] numeros = cadena.split(" ");

        if (numeros[1].equalsIgnoreCase("division")) {
            resultado = Double.parseDouble(numeros[0]) / Double.parseDouble(numeros[2]);
        }
        if (numeros[1].equalsIgnoreCase("suma")) {
            resultado = Double.parseDouble(numeros[0]) + Double.parseDouble(numeros[2]);
        }
        if (numeros[1].equalsIgnoreCase("multiplicacion")) {
            resultado = Double.parseDouble(numeros[0]) * Double.parseDouble(numeros[2]);
        }
        if (numeros[1].equalsIgnoreCase("resta")) {
            resultado = Double.parseDouble(numeros[0]) - Double.parseDouble(numeros[2]);
            
        } if (numeros[0].equalsIgnoreCase("raiz")) {
            resultado = Math.sqrt(Double.parseDouble(numeros[1]));
        }
        //Mostramos el mensaje
        System.out.println("resultado:" + resultado);

        OutputStream os = newSocket.getOutputStream();

        System.out.println("Enviando mensaje");
        os.write(String.valueOf(resultado).getBytes());
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
