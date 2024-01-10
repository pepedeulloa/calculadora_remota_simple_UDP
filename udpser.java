import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.print.DocFlavor.STRING;


public class udpser {

    public Integer          porto;
    public Integer          secreto;
    public static byte[]    bufferEntrada;
    public static String    ipServidor = "127.0.0.1";

    public udpser (String[] args){
        
        comprobaArgs(args);

        this.porto      = Integer.parseInt(args[0]);
        this.secreto    = Integer.parseInt(args[1]);

    }
    public static void main(String[] args) {
       
        udpser server = new udpser(args);

        System.out.println("Benvido ó servidor da calculadora básica remota UDP.");

        try {
            // DatagramSocket serverSocket = new DatagramSocket(server.porto, InetAddress.getByName(ipServidor));
            DatagramSocket serverSocket = new DatagramSocket(server.porto);

            while (true){
                bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada   = new DatagramPacket(bufferEntrada,bufferEntrada.length);
                DatagramPacket paqueteSaida     = null;

                System.out.println("A espera de mensaxes de clientes no porto...");

                try{
                    
                    serverSocket.receive(paqueteEntrada);

                    
                    Integer resultado       = calculadora(paqueteEntrada, server.secreto);
                    String  resultadoString = Integer.toString(resultado);

                    paqueteSaida = new DatagramPacket(resultadoString.getBytes("UTF8"), 
                                                        resultadoString.getBytes("UTF8").length, 
                                                            paqueteEntrada.getAddress(), paqueteEntrada.getPort());
                    serverSocket.send(paqueteSaida);

                } catch (IOException a){
                    System.out.println("Erro no envio ou recepción. O servidor vai pecharse.");
                    System.exit(0);
                }
            }
        } catch (SocketException e) {
            System.out.println("Erro ó crear socket");
            System.exit(-1);
        }
        
        System.exit(0);        
    }

    public void comprobaArgs (String [] args){

        if(args.length != 2){
            System.out.println("Erro nos argumentos pasados ó servidor.\nA sintaxe válida é a seguinte:");
            System.out.println("\tudpser porto secreto");
            System.out.println("\tExemplo: udpser 9123 99");
            System.exit(-1);
        }
    }

    public static Integer calculadora (DatagramPacket paqueteRecibido, Integer secreto){
        try {

            Integer resultado           = 0;
            String operacion            = new String(paqueteRecibido.getData(), "UTF8");
            String [] operacionSplit    = operacion.split(" ");
            Integer numero1             = Integer.parseInt(operacionSplit[0]);
            Integer numero2             = Integer.parseInt(operacionSplit[1]);
            char    operador            = operacionSplit[2].charAt(0);

            System.out.println(numero1+" "+operacionSplit[2]+" "+numero2);

            switch (operador) {
                case '/': 
                    if(numero1 == 0 || numero2 == 0){
                        resultado = 0;
                    } else
                    resultado = numero1 / numero2;                    
                    break;
                case '-':
                    resultado = numero1 - numero2;
                    break;
                case '+':
                    resultado = numero1 + numero2;
                    break;
                case '*':
                    resultado = numero1 * numero2;
                    break;
                default:
                    System.out.println("Operación descoñecida...");
                    break;

            }

            return (resultado + secreto);
            
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro ó decodificar.");
            System.exit(0);
            return 0;
        }
        
    }

}
