package base.servlet;

import javax.servlet.annotation.WebServlet;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

@WebServlet(urlPatterns = {"/teows"})
public class PushSocket extends Endpoint {
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                // Handle incoming messages
            }
        });

        // Send a message to the client
        session.getAsyncRemote().sendText("Hello, client!");
    }
}
