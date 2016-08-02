package pal.client;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import pal.ControlException;
import pal.settings.ControlSettings;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dhruvr on 15/7/16.
 */
public class ElasticSearchClientProvider implements Provider<Client> {


    private final String clusterName;
    private final String hostIp;
    private final int port;


    @Inject
    public ElasticSearchClientProvider(ControlSettings controlSettings) {
        clusterName = controlSettings.get("es.cluster.name", "dhruv_cluster");
        hostIp = controlSettings.get("es.ip", "127.0.0.1");
        port = controlSettings.getAsInt("es.port", 9300);
    }

    public Client get() {

        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .build();

        Client client = null;
        try {
            client = TransportClient.builder().settings(settings)
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostIp), port));
        } catch (UnknownHostException e) {
            throw new ControlException("Unknown host [" + hostIp + "]");
        }

        return client;
    }
}
