package calculadoracliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author mbacelofernandez
 */
public class Metodos {

    Socket clienteSocket;
    OutputStream os;
    InputStream is;

    /**
     * Nos conectamos con el servidor introduciendo el puerto y la ip deseada
     */
    public void conexion() {

        try {
            System.out.println("Creando socket cliente");
            clienteSocket = new Socket();
            System.out.println("Estableciendo la conexi�n");
            int puerto = Integer.parseInt(JOptionPane.showInputDialog("Introduce puerto"));
            String ip = JOptionPane.showInputDialog("Introduce ip del servidor");
            InetSocketAddress addr = new InetSocketAddress(ip, puerto);
            clienteSocket.connect(addr);
            os = clienteSocket.getOutputStream();
            is = clienteSocket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo que recibe los operandos de la interfaz grafica, envia la operacion al
     * servidor y recibe el resultado
     * @param enviar
     * @return
     * @throws IOException 
     */
    public String operacion(String enviar) throws IOException {
        System.out.println("enviar: " +enviar);
        System.out.println("Enviando mensaje");
        os.write(enviar.getBytes());
        System.out.println("Mensaje enviado");

        byte[] mensaje = new byte[25];
        is.read(mensaje);

        System.out.println("Resultado: " + new String(mensaje));
        String resultado = new String(mensaje);

        return resultado;
    }
    /**
     * Cerramos el input, output y el socket
     * @throws IOException 
     */
    public void pecharConexion() throws IOException {
        System.out.println("Cerrando el socket cliente");
        os.close();
        is.close();
        clienteSocket.close();
        System.out.println("Terminado");

    }
}
