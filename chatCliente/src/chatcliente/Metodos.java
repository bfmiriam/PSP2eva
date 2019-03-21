package chatcliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Sphynx
 */
public class Metodos {

    Socket clienteSocket;
    OutputStream os;
    InputStream is;
    /**
     * Creamos a conexión do cliente,conectamos co servidor e iniciamos un fio 
     * que permanece aberto para poder ler constantemente
     * @param ip recibida da interface
     * @param puerto recibido da interface
     */
    public void conexion(String ip, String puerto) {

        try {
            System.out.println("Creando socket cliente");
            clienteSocket = new Socket();
            System.out.println("Estableciendo la conexion");
            InetSocketAddress addr = new InetSocketAddress(ip, Integer.parseInt(puerto));
            clienteSocket.connect(addr);
            os = clienteSocket.getOutputStream();
            is = clienteSocket.getInputStream();
            HiloLeer h = new HiloLeer(clienteSocket,is);
            h.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * recibimos a mensaxe enviada dende a interface,e enviámola ao servidor
     * @param enviar recibida da clase Chat (interface)
     * @throws IOException 
     */
    public void enviarMensaje(String enviar) throws IOException {
        System.out.println("enviar: " + enviar);
        System.out.println("Enviando mensaje");
        os.write(enviar.getBytes());
        System.out.println("Mensaje enviado");

    }
    /**
     * recibimos a mensaxe do fio HiloLeer, e mostrámola na sala de chat (interface)
     * @param mensaje
     * @throws IOException 
     */
    public void recibirMensaje(String mensaje) throws IOException {
       Chat.txtSala.setText(Chat.txtSala.getText() + mensaje+"\n");
    }
    /**
     * pechamos a conexion do cliente
     * @throws IOException 
     */
    public void pecharConexion() throws IOException {
        System.out.println("Cerrando el socket cliente");
        clienteSocket.close();
        System.out.println("Terminado");

    }
}
