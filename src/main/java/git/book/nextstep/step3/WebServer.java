package git.book.nextstep.step3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        int port = 0;
        if(args ==null || args.length == 0 ){
            port = DEFAULT_PORT;
        }else {
            port = Integer.parseInt(args[0]);
        }

        //서버 소켓을 생성한다. 웹 서버는 기본적으로 8080번 포트를 사용한다.
        try(ServerSocket listenSocket = new ServerSocket(port)){
            log.info("Web Application Server started {} port.",port);

            //클라이언트가 연결될 때까지 대기한다.
            Socket connection;
            while((connection = listenSocket.accept()) != null){
                RequestHandler requestHandler =
                        new RequestHandler(connection);
                requestHandler.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
class RequestHandler extends Thread{
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection=connection;
    }

    @Override
    public void run() {
        log.info("New Client Connect! Connected IP: {} , Port : {}",connection.getInetAddress(),connection.getPort());

        try(InputStream in = connection.getInputStream();
            OutputStream  out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            response200Header(dos,body.length);
            responseBody(dos,body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body,0,body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try{
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent +"\r\n");
            dos.writeBytes("\r\n");
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

}
