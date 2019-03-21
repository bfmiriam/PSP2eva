package chatservidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Sphynx
 */
public class ChatServidor {

    static ArrayList<Usuarios> listaUsuarios = new ArrayList();

    public static void main(String[] args) {

        try {
            int puerto = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce puerto"));
            //Creamos el socket del servidor:
            System.out.println("Creando socket servidor");
            //El socket del servidor se queda escuchando en la direccion deseada.
            ServerSocket serverSocket = new ServerSocket(puerto);
            InputStream input;
            OutputStream os;
            int maxUsuarios = 3;
            while (true) {
                //En cuanto reciba una conexion se crea el objeto Socket
                System.out.println("Aceptando conexiones");
                Socket newSocket = serverSocket.accept();
                input = newSocket.getInputStream();
                os = newSocket.getOutputStream();

                /* Se crea un Thread y se añade a un arrayList el nuevo usuario mientras
                haiga menos de 10 conectados, enviamos nº de usuarios conectados
                */
                if (listaUsuarios.size() < maxUsuarios) {
                    Usuarios usu = new Usuarios(newSocket, input, os);
                    usu.start();
                    listaUsuarios.add(usu);
                    String con = "/conectado:" + listaUsuarios.size()+":";
                    os.write(con.getBytes());
                 //si no se cumple lo anterior, notificamos y cerramos el cliente
                } else {
                    os.write("/cerrar".getBytes());
                }
            }

        } catch (IOException ex) {
            System.out.println("Error al recibir conexiones");
        }
    }

}

class Usuarios extends Thread {

    private Socket socket;
    private InputStream input;
    private OutputStream os;

    public Usuarios(Socket socket, InputStream input, OutputStream os) throws IOException {
        this.socket = socket;
        this.input = input;
        this.os = os;
        System.out.println("Conexión recibida");
    }

    @Override
    public void run() {

        try {
            while (true) {
                //Creamos una variable que nos permitira visualizar el mensaje.
                //Grabamos en esa variable lo que nos llega en el input
                byte[] mensaje = new byte[250];
                input.read(mensaje);
                String cadena = new String(mensaje);
                /*si el usuario escribe "/bye" eliminamos al usuario del arrayList,
                finalizamos el hilo y lo notificamos al resto de usuarios
                */
                if (cadena.contains("/bye")) {
                    String[] msg = cadena.split(":");
                    String desconexion = msg[0] + " acaba de desconectarse";
                    ChatServidor.listaUsuarios.remove(this);
                    
                    for (Usuarios i : ChatServidor.listaUsuarios) {
                        i.enviar(desconexion);
                    }
                    this.stop();
                    socket.close();
                //enviamos los mensajes recibidos a los usuarios conectados
                } else {

                    for (Usuarios i : ChatServidor.listaUsuarios) {
                        i.enviar(cadena);
                    }
                }

            }
        } catch (IOException ex) {
            System.out.println("Error al enviar mensaje");
        } finally {
            try {
                //Cerramos la conexión
                this.stop();
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar la conexión");
            }
        }
    }

    //recibimos el mensaje enviado por un usuario y lo reenviamos
    public void enviar(String cadena) throws IOException {

        //Mostramos el mensaje
        System.out.println("Mensaje:" + cadena);

        System.out.println("Enviando mensaje");
        os.write(cadena.getBytes());
        System.out.println("Mensaje enviado");
    }
}
