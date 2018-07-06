import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient
{
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;

    public void startConnection(int port) throws IOException, UnknownHostException
    {
        clientSocket = new Socket("127.0.0.1", port);

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        scanner = new Scanner(System.in);

        while(true)
            System.out.println((sendMessage(scanner.next())));

        /*String response1 = sendMessage("hi");
        String response2 = sendMessage("hello");
        String response3 = sendMessage("sup");
        String response4 = sendMessage(".,");

        System.out.println(response1 + " " + response2 + " " + response3 + " " + response4);*/
    }

    private String sendMessage(String message) throws IOException
    {
        out.println(message);
        return in.readLine();
    }

    public static void main(String[] args) throws IOException, UnknownHostException
    {
        EchoClient client = new EchoClient();
        client.startConnection(Integer.parseInt(args[0]));
    }
}
