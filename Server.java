import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {

        try {

            int port = Integer.parseInt(System.getenv("PORT"));

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started on port " + port);

            while(true){

                Socket socket = serverSocket.accept();

                System.out.println("Client connected");

                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                PrintWriter out =
                        new PrintWriter(
                                socket.getOutputStream(), true);

                String filename = in.readLine();

                System.out.println("Client requested: " + filename);

                File file = new File(filename);

                if(file.exists()){

                    BufferedReader fileReader =
                            new BufferedReader(
                                    new FileReader(file));

                    String line;

                    while((line = fileReader.readLine()) != null){
                        out.println(line);
                    }

                    fileReader.close();

                } else {

                    out.println("File not found");
                }

                out.println("EOF");

                socket.close();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
