package com.examples;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.examples.resources.ResourceXML;
import java.util.logging.Logger;

public class Server extends Thread {

    private static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");

    public static final String ROOT_PATH = "base";
    public static Server server;
    
    public static void main(String[] args) throws IOException, InterruptedException{
    	server = new Server();
    	server.start();
    }
    
    public void run() {
    	 try {
             final Application resourceConfig = new Application();
             resourceConfig.property("contextConfig", new AnnotationConfigApplicationContext(ResourceXML.class));

             final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
             Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                 public void run() {
                     server.stop();
                 }
             }));
             server.start();

             Thread.currentThread().join();
         } catch (IOException  ex) {
         	Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }
         catch (InterruptedException ex) {
             Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}