import java.io.*;
import java.net.*;

public class KnockKnockServer {
  public static void main(String[] args) throws IOException{
    if (args.length != 1) {
        System.err.println("Usage: java KnockKnockServer <port number>");
        System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);

    try (
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    ) {

        String inputLine, outputLine;

        KnockKnockProtocol kkp = new KnockKnockProtocol();
        outputLine = kkp.processInput(null);
        out.println(outputLine);

        while ((inputLine = in.readLine()) != null) {
            outputLine = kkp.processInput(inputLine);
            out.println(outputLine);

            if (outputLine.equals("Bye."))
                break;
        }

    } catch (IOException e) {
      System.out.println("Exception on port number" + portNumber);
      System.out.println(e.getMessage());
    }
  }
}
