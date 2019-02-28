package calculadoraservidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *Realiza una calculadora cliente servidor
 * @author mbacelofernandez
 */
public class CalculadoraServidor {

    public static void main(String[] args) throws IOException {
        
      try {
            int puerto = Integer.parseInt(JOptionPane.showInputDialog("Introduce puerto"));
            //Creamos el socket del servidor:
            System.out.println("Creando socket servidor");
            //El socket del servidor se queda escuchando en la direccion deseada.
            ServerSocket serverSocket = new ServerSocket(puerto);

            while (true) {
                //En cuanto reciba una conexion se crea el objeto Socket
                System.out.println("Aceptando conexiones");
                Socket newSocket = serverSocket.accept();

                // Se crea un Thread
                new Clientes(newSocket).start();
               
            }

        } catch (IOException ex) {
            System.out.println("Error al recibir conexiones");
        }
    }

}

class Clientes extends Thread {

    private Socket socket;
    private InputStream input;
    private OutputStream os;

    public Clientes(Socket socket) throws IOException {
        this.socket = socket;
        input = socket.getInputStream();
        os = socket.getOutputStream();
        System.out.println("Conexión recibida");
    }

    @Override
    public void run() {

        try {
            while (true) {
                double resultado = 0;

                //Creamos una variable que nos permitira visualizar el mensaje.
                //Grabamos en esa variable lo que nos llega en el input
                byte[] mensaje = new byte[25];
                input.read(mensaje);

                //asignamos el mensaje a una variable tipo String
                String cadena = new String(mensaje);
                //asignamos el mensaje a una cadena y lo parseamos 
                String[] numeros = cadena.split(" ");
                //Dependiendo del tipo de operacion escogemos la opcion deseada
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

                }
                if (numeros[0].equalsIgnoreCase("raiz")) {
                    resultado = Math.sqrt(Double.parseDouble(numeros[1]));
                }
                        
                //Mostramos el mensaje
                System.out.println("resultado:" + resultado);

                OutputStream os = socket.getOutputStream();

                System.out.println("Enviando mensaje");
                os.write(String.valueOf(resultado).getBytes());
                System.out.println("Mensaje enviado");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CalculadoraServidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //Cerramos la conexión y el hilo
                this.stop();
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar la conexión");
            }
        }

    }

}