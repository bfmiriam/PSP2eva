package calculadoracliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author mbacelofernandez
 */
public class Metodos {

    Socket clienteSocket;
    OutputStream os;
    InputStream is;

    public void conexion() {

        try {
            System.out.println("Creando socket cliente");
            clienteSocket = new Socket();
            System.out.println("Estableciendo la conexi�n");
            InetSocketAddress addr = new InetSocketAddress("localhost", 6666);
            clienteSocket.connect(addr);
            os = clienteSocket.getOutputStream();
            is = clienteSocket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void pecharConexion() throws IOException {
        System.out.println("Cerrando el socket cliente");
        clienteSocket.close();
        System.out.println("Terminado");

    }
}
