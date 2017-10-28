package com.journal.config;

import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.journal.endpoint.Endpoint;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/journal")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
        registerEndpoints();
    }
    private void registerEndpoints() {
    	register(JacksonXMLProvider.class);
    	this.register(WadlResource.class);
    	this.register(Endpoint.class);
    }
	
	@PostConstruct
	public void init() {
		// Register components where DI is needed
		this.configureSwagger();
	}
	
	private void configureSwagger() {
		// Available at http://localhost:port/accountStatement/swagger.json
		this.register(ApiListingResource.class);
		this.register(SwaggerSerializers.class);

		BeanConfig config = new BeanConfig();
		config.setTitle("FYP Journal");
		config.setVersion("v1");
		config.setContact("Irtaza Zaidi");
		config.setSchemes(new String[] { "http", "https" });
		config.setResourcePackage("com.journal.endpoint");
		config.setPrettyPrint(true);
		config.setScan(true);
	}
}