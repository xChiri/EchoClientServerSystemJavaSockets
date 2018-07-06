import com.sun.security.ntlm.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer
{
    private ServerSocket serverSocket;

    private void start(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        while(true)
            new ClientHandler(serverSocket.accept()).start();
    }

    private static class ClientHandler extends Thread
    {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket requiredSocket)
        {
            clientSocket = requiredSocket;
        }

        @Override
        public void run() {

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;

                while (true)
                    if ((inputLine = in.readLine()) != null)
                        if(inputLine.compareTo(".") != 0)
                            out.println(inputLine + " (back from server)");
                        else break;

                out.println("Closing client socket");
                in.close();
                out.close();
                clientSocket.close();

            }
            catch (IOException ex)
            {
                System.err.println("Something went wrong with the input and output streams of the client socket");
            }

        }
    }


    public static void main(String[] args) throws IOException
    {
        EchoServer server = new EchoServer();
        server.start(Integer.parseInt(args[0]));
    }

}
