package br.com.ythalorossy.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

@ApplicationPath("api")
public class ResourceApplication extends Application {

    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
         properties.put("jersey.config.server.provider.packages", "br.com.ythalorossy.services");
        return properties;
    }

}
