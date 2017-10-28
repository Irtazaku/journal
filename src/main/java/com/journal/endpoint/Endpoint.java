package com.journal.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Path("1")
@Api(value = "Endpoint", produces = "application/json")
public class Endpoint {

	public static final Logger logger = LoggerFactory.getLogger(Endpoint.class);
	
	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Gets info of AS MS. Version 1", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Account Statement MS is up"),
	    @ApiResponse(code = 404, message = "Requested endpoint is not available")
	})
	public Map<String, Object> msInfo() throws IOException {

		long startTime = System.currentTimeMillis();
		logger.info("msInfo GET METHOD STARTED");
		Map<String, Object> info = new HashMap<>();
		info.put("asd", "asdasda");
		logger.info("msInfo GET METHOD DONE -- TOOK " + (System.currentTimeMillis() - startTime) + " millis");
		return info;
	}
}
