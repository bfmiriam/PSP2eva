package chatcliente;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sphynx
 */
public class HiloLeer extends Thread {

    Socket clienteSocket;
    InputStream input;

    public HiloLeer(Socket clienteSocket, InputStream input) throws IOException {
        this.clienteSocket = clienteSocket;
        this.input = input;
    }

    @Override
    public void run() {
        Metodos m = new Metodos();
        try {
            while (true) {
                //leemos a mensaxe recibida do servidor
                byte[] mensaje = new byte[250];
                input.read(mensaje);
                String msg = new String(mensaje);
                //se a mensaxe mostra o seguinte contido indícanos que a sala de 
                //chat está chea e pecha a interface
                if (msg.contains("/cerrar")) {
                    JOptionPane.showMessageDialog(null, "La sala de chat está llena.Inténtelo de nuevo más tarde.");
                    System.exit(0);
                 //se a mensaxe mostra o seguinte contido conéctanos á sala de chat
                 //e mostra cantos usuarios ten
                }if (msg.contains("/conectado")) {
                    String[] cadena = msg.split(":");
                    String usuarios ="Conectado a la sala de chat\nActualmente hay "+cadena[1]+ " usuarios conectados";                     
                    System.out.println("usuarios: "+usuarios);
                    m.recibirMensaje(usuarios);
                 //se non se cumplen as sentenzas anteriores envía a mensaxe aos clientes conectados
                } else {
                    System.out.println("Recibido: " + msg);
                    m.recibirMensaje(msg);                   
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloLeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
