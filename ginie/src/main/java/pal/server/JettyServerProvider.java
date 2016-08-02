package pal.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.jetty.server.Server;
import pal.settings.ControlSettings;

import java.net.InetSocketAddress;

/**
 * Created by dhruvr on 25/7/16.
 */
public class JettyServerProvider implements Provider<Server> {

    private static Server SERVER;

    @Inject
    public JettyServerProvider(ControlSettings controlSettings) {
        if (SERVER == null) {
            int port = controlSettings.getAsInt("jetty.port", 8080);
            String host = controlSettings.get("jetty.host.ip", "127.0.0.1");
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
            SERVER = server(inetSocketAddress);
        }
    }

    @Override
    public Server get() {
        return SERVER;
    }

    private Server server(InetSocketAddress inetSocketAddress) {
        return new Server(inetSocketAddress);
    }

}
