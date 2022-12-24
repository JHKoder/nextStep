package git.book.nextstep.step3.sample1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

class WebServer {
    private static final int port = 8089;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket connection = serverSocket.accept();
                ServerHandler handler = new ServerHandler(connection);
                handler.start();
            }
        } catch (IOException ignored) {
        }
    }
}

class ServerHandler extends Thread {

    private final static String currentPath = System.getProperty("user.dir");
    private final Socket connection;

    public ServerHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        try {
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String[] lines = br.readLine().split(" ");

            if (lines[1].equals("/index.html")) {
                OutputStream os = connection.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                byte[] bytes = Files.readAllBytes(new File(currentPath + "/src/main/resources/static/html" + lines[1]).toPath());

                response200Header(dos, bytes.length);
                dos.write(bytes);
                dos.flush();
                os.close();
                dos.close();
            }


            is.close();
            br.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
        }
    }

}
