package org.bitbucket.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.Constants;
import org.apache.tomcat.websocket.server.WsContextListener;
import org.bitbucket.handlers.UsersHandlers;
import org.bitbucket.handlers.WebsocketHandler;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class ServerConfig {

    public static ServerRunner tomcat() throws ServletException {
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080"; //TODO - make 5432
        }
        tomcat.setPort(Integer.parseInt(webPort));
        Context ctx = tomcat.addWebapp("/", new File(".").getAbsolutePath());
        ctx.addApplicationListener(WsContextListener.class.getName());
        tomcat.addServlet(ctx, "UsersHandlers", HandlerConfig.usersHandlers());
        ctx.addServletMappingDecoded("/*", "UsersHandlers");
        return new ServerRunner(tomcat, ctx, List.of(chatWebsocketHandler));
    }

    private static Consumer<Context> chatWebsocketHandler = ctx -> {
        WebsocketHandler websocketHandler = HandlerConfig.websocketHandler();
        ServerContainer scon = (ServerContainer) ctx.getServletContext().getAttribute(Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
        try {
            scon.addEndpoint(ServerEndpointConfig.Builder.create(WebsocketHandler.class, "/chat")
                    .configurator(new ServerEndpointConfig.Configurator() {
                        @Override
                        public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
                            return (T) websocketHandler;
                        }
                    }).build());
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    };

}
