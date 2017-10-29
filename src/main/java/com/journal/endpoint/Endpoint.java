package com.journal.endpoint;

import com.journal.entity.FileRepository;
import com.journal.entity.User;
import com.journal.entity.UserRepository;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@Component
@Path("1")
@Api(value = "Endpoint", produces = MediaType.APPLICATION_JSON_VALUE)
public class Endpoint {

	private static String UPLOADED_FOLDER = "C://journals//";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FileRepository fileRepository;

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> msInfo() throws IOException {
		Map<String, Object> info = new HashMap<>();
		info.put("asd", "asdasda");
		return info;
	}

	@POST
	@Path("/uploadJournal")
	public String singleFileUpload(//@FormParam("file") FormDataContentDisposition disposition,
								   	@RequestAttribute("file") InputStream fileStream,
									@QueryParam("fileKey") String fileKey/*,
								   RedirectAttributes redirectAttributes*/) throws IOException {



		try {
			byte[] bytes = IOUtils.toByteArray(fileStream);
			//System.out.println(fileStream.);
			//byte[] bytes = fileStream.getBytes();
			java.nio.file.Path path = Paths.get(UPLOADED_FOLDER + fileKey);
			Files.write(path, bytes);
			com.journal.entity.File file = new com.journal.entity.File();
			file.setFileName(fileKey);
			file.setFileKey(fileKey);
			file.setType(fileKey);
			fileRepository.save(file);

		} catch (IOException e) {
			e.printStackTrace();
			return "error: ";
		}

		return "Saved";
	}

	@POST
	@Path("/add") // Map ONLY GET Requests
	public @ResponseBody
	String addNewUser (@QueryParam("ussername") String username,
					   @QueryParam("password") String password) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setUsername(username);
		n.setPassword(password);
		userRepository.save(n);
		return "Saved";
	}

	@GET
	@Path("/all")
	public @ResponseBody Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}

	@GET
	@Path("/download")
	public ResponseEntity<Resource> download(@QueryParam("fileKey") String fileKey,
											 @QueryParam("id") Integer fileId) throws IOException {

		if(fileId != null){
			com.journal.entity.File file = fileRepository.findOne(Long.parseLong(fileId.toString()));
			fileKey = file.getFileKey();
		}
		File file = new File(UPLOADED_FOLDER + fileKey);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok()
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}


}
