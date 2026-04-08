import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {

        try {

            int port;

            if(System.getenv("PORT") != null){
                port = Integer.parseInt(System.getenv("PORT"));
            } else {
                port = 5000;
            }

            ServerSocket server = new ServerSocket(port);

            System.out.println("File Server Running... on Port : "+port);

            while(true){

                Socket socket = server.accept();

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

                    out.println("EOF");

                    fileReader.close();

                } else {

                    out.println("FILE_NOT_FOUND");
                }

                socket.close();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
