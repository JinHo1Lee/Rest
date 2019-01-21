package com.examples;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.examples.resources.ResourceJson;
import com.examples.resources.ResourceXML;

public class Application extends ResourceConfig {

    public Application() {
        register(RequestContextFilter.class);
        register(ResourceXML.class);
        register(ResourceJson.class);
    }
}