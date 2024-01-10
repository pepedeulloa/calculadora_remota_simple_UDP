import java.io.IOException;
import java.lang.Integer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class udpcli {

    public InetAddress  ipServer;
    public Integer      portoServer;
    public Integer      porto;
    public String       operacion;

    public udpcli (String[] args){

        comprobaArgs(args);

        try {
            this.ipServer       = InetAddress.getByName(args[0]);
            this.portoServer    = Integer.parseInt(args[1]);
            this.operacion      = args[2]+" "+args[3]+" "+args[4];
        } catch (UnknownHostException e) {
            System.out.println("Erro na dirección do servidor, revise os argumentos introducidos");
        }
    }

    public static void main(String[] args) {

        udpcli cliente = new udpcli(args);

        if(cliente.operacion.equals("0")){
            System.out.println("Ata logo!");
            System.exit(0);
        }

        try {

            DatagramSocket socket = new DatagramSocket();
            Integer porto = socket.getLocalPort();
        
            byte [] buf = new byte[1024];
            DatagramPacket paqueteRecibido = new DatagramPacket(buf, buf.length);

            DatagramPacket paqueteEnviado = new DatagramPacket(cliente.operacion.getBytes("UTF8"), cliente.operacion.getBytes("UTF8").length, cliente.ipServer, cliente.portoServer);
            socket.send(paqueteEnviado);
            
            System.out.println("Envío feito. Esperando resposta durante 10s.");
            socket.setSoTimeout(10000);
            
            while (true){
                try{
                    socket.receive(paqueteRecibido);
                    String mensaxeServer = new String(paqueteRecibido.getData(), "UTF8");
                    System.out.println("Resultado da operación: "+ mensaxeServer);
                    socket.close();
                    System.out.println("Ata logo!");

                    System.exit(1);
                } catch (SocketTimeoutException timeout){
                    System.out.println("Non se recibiu resposta do servidor tras 10s de espera.\nAta logo!");
                    socket.close();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro no envío. ");
            System.exit(0);
        }
        
    }

    public void comprobaArgs (String [] args){
        if(args.length != 5){
            System.out.println("Erro nos argumentos pasados ó cliente.\nA sintaxe válida é a seguinte:");
            System.out.println("\tudpcli ip_servidor portoServidor numero1 numero2 'operación'");
            System.out.println("\tExemplo: udpcli 127.0.0.1 9123 33 34 '*'");
            System.exit(-1);
        }
    }
}
