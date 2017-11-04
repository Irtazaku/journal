package com.journal.endpoint;

import com.journal.dto.*;
import com.journal.entity.*;
import com.journal.util.EntityHelper;
import com.journal.util.ResponseStatusCodeEnum;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Path("1")
//@Api(value = "Endpoint", produces = MediaType.APPLICATION_JSON_VALUE)
public class Endpoint {

	private static String UPLOADED_FOLDER = "C://journals//";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FileRepository fileRepository;
    @Autowired
	private JournalRepository journalRepository;
    @Autowired
    private ArticleRepository articleRepository;

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> msInfo() throws IOException {
		Map<String, Object> info = new HashMap<>();
		info.put("asd", "asdasda");
		return info;
	}

	@POST
	@Path("/signup") // Map ONLY GET Requests
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	UserResponseDto addNewUser (@QueryParam("username") String username,
					  			@QueryParam("password") String password,
								UserDto requestedUser) {
		UserResponseDto response = new UserResponseDto();
		response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
		if(!EntityHelper.isSet(requestedUser.getUsername()) || !EntityHelper.isSet(requestedUser.getUsername())){
			response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
			return response;
		}
		User n = requestedUser.asEntity();
		User user = userRepository.save(n);
		if(EntityHelper.isSet(user.getId())){
			response.setUserDto(user.asDto());
			response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
		}
		return response;
	}

    @POST
    @Path("/resetPassword") // Map ONLY GET Requests
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponseDto addNewUser (ResetPasswordRequestDto requestDto) {
        UserResponseDto response = new UserResponseDto();
        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        if(!EntityHelper.isSet(requestDto.getOldPassword()) || !EntityHelper.isSet(requestDto.getOldPassword()) || !EntityHelper.isSet(requestDto.getUserId())){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            return response;
        }
        User n = userRepository.findOne(requestDto.getUserId());
        if(!n.getPassword().equals(requestDto.getOldPassword())){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_LOGIN_DETAILS.getHeader());
            return response;
        }
        n.setPassword(requestDto.getNewPassword());
        User user = userRepository.save(n);
        if(EntityHelper.isSet(user.getId())){
            response.setUserDto(user.asDto());
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
        }
        return response;
    }

    @GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	UserResponseDto getAllUsers(@QueryParam("username") String username,
								@QueryParam("password") String password){
		UserResponseDto response = new UserResponseDto();
		response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_LOGIN_DETAILS.getHeader());
		List<User> userList = (List<User>) userRepository.findAll();
		if(userList != null) {
			for (User user : userList) {
				if(user.getUsername().equals(username) && user.getPassword().equals(password)){
					response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
					response.setUserDto(user.asDto());
					break;
				}
			}
		}
		return response;
	}



    @GET
    @Path("/getJournalById")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public JournalResponseDto getJournalById(@QueryParam("journalId") Integer journalId){
        JournalResponseDto response = new JournalResponseDto();
        response.setJournalDto(new JournalDto(null, null, null, null, null, null, null, new FileDto(null, null, null, null), new FileDto(null, null, null, null), null));
        try {
            Journal journal= journalRepository.findOne(journalId);
            if(EntityHelper.isSet(journal.getId())){
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                response.setJournalDto(journal.asDto());
            }
        } catch (Exception e) {
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        return response;
    }

    @POST
    @Path("/addJournal")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public JournalResponseDto addJournalById(JournalDto journalDto){
        JournalResponseDto response = new JournalResponseDto();
        //response.setJournalDto(new JournalDto(null, null, null, null, null, null, null, new FileDto(null, null, null, null) , new FileDto(null, null, null, null) , null));
        try {
            if(!EntityHelper.isSet(journalDto.getName()) || EntityHelper.isNull(journalDto.getJournal())){
                response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
                return response;
            }
            Journal journal= journalDto.asEntity();
            Journal savedJournal = journalRepository.save(journal);
            if(EntityHelper.isSet(savedJournal.getId())){
                response.setJournalDto(savedJournal.asDto());
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
            }
        } catch (Exception e) {
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        return response;
    }


    @GET
	@Path("/downloadFile")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> download(@QueryParam("fileKey") String fileKey,
											 @QueryParam("id") Integer fileId) throws IOException {

		if(fileId != null){
			com.journal.entity.File file = fileRepository.findOne(fileId);
			fileKey = file.getFileKey();
		}
		java.io.File file = new File(UPLOADED_FOLDER + fileKey);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok()
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}

    @POST
    @Path("/uploadFile")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseHeaderDto singleFileUpload(//@FormDataParam("file") FormDataContentDisposition disposition,
                                              @RequestParam("file") FileInputStream fileStream/*/,
                                   @RequestParam("file") InputStream fileStream,*/,
                                   @QueryParam("fileKey") String fileKey
								   /*RedirectAttributes redirectAttributes*/) throws IOException {



        try {
            byte[] bytes = IOUtils.toByteArray(fileStream);
            //System.out.println(fileStream.);
            //byte[] bytes = fileStream.getBytes();
            OutputStream out;
            out = new BufferedOutputStream(new FileOutputStream(UPLOADED_FOLDER + fileKey));
            out.write(bytes);
            out.flush();
            out.close();

            //java.nio.file.Path path = Paths.get(UPLOADED_FOLDER + fileKey);
            //Files.copy(fileStream, path);
            //Files.write(path, bytes);
            com.journal.entity.File file = new com.journal.entity.File();
            file.setFileName(fileKey);
            file.setFileKey(fileKey);
            file.setType(fileKey);
            fileRepository.save(file);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseStatusCodeEnum.ERROR.getHeader();
        }

        return ResponseStatusCodeEnum.SUCCESS.getHeader();
    }


    /*@Path("/postfile")
    @POST
    @Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
    public Response postfile(//@FormDataParam("file") FormDataBodyPart bodyPart,
                             @QueryParam("fileKey") String fileKey,
                             @FormDataParam("Filedata") FormDataContentDisposition disposition,
                             @FormDataParam("Filedata") InputStream fileStream) throws IOException {
       // LOGGER.info("postfile CALLED WITH FILE KEY-> " + fileKey);
        long startTime = System.currentTimeMillis();
        //IFileStorage storage = storageResolver.resolveStorage(fileKey);
        // no appropriate storage
        *//*if(storage==null)
        {
            //Logger.getLogger(this.getClass().getName()).warning("postfile -- Storage for file key not found. fileKey = " + fileKey);
            throw new WebApplicationException(HttpStatus.NOT_ACCEPTABLE.value());
        }*//*

        byte[] fileContents = IOUtils.toByteArray(fileStream);;

        boolean result = false;

        //FileKey fileKeyResolved = resolveFileKey(fileKey);
       // if(fileKeyResolved.isResolved()){
            String url = UPLOADED_FOLDER + fileKey;
            OutputStream out;
            try {
               // LOGGER.log(Level.INFO, "url = " + url);

                File file = new File(url);
                System.out.println(url);
                File folder = file.getParentFile();
                if (!folder.mkdirs() && !folder.exists()) {
             //       LOGGER.log(Level.SEVERE, "uploadFile: Cannot create folders");
                }
                else {
                    out = new BufferedOutputStream(new FileOutputStream(url));
                    out.write(fileContents);
                    out.flush();
                    out.close();
                    result = true;
                }
            } catch (Throwable e) {
           //     LOGGER.log(Level.SEVERE, "An exception occurred in FileSystemStorage.uploadFile: ", e);
            }
        *//*}
        else {
            LOGGER.log(Level.WARNING, "FileSystemStorage: file key not resolved: " + fileKey);
        }*//*

        //return result;
        if(result) {
           // LOGGER.info(String.format("postfile -- TOOK[%d millis] with FILE KEY-> %s", (System.currentTimeMillis() - startTime), fileKey));
            return Response.ok().build();
        } else {
           // Logger.getLogger(this.getClass().getName()).severe("postfile -- File upload failed. fileKey = " + fileKey);
            throw new WebApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //return fileServiceLogic.postfile(fileKey, disposition, fileStream);
    }*/


    @POST
    @Path("/addArticle")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public ArticleResponseDto addArticle (ArticleDto requestedDto) {
        ArticleResponseDto response = new ArticleResponseDto();
        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        if(!EntityHelper.isSet(requestedDto.getTitle()) || !EntityHelper.isSet(requestedDto.getContent())){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            return response;
        }
        Article n = requestedDto.asEntity();
        Article article = articleRepository.save(n);
        if(EntityHelper.isSet(article.getId())){
            response.setArticleDto(article.asDto());
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
        }
        return response;
    }

    @GET
    @Path("/getArticlesList")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ArticlesListResponseDto getArticlesList () {
        ArticlesListResponseDto response = new ArticlesListResponseDto();
        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        List<Article> articles = (List)articleRepository.findAll();
        List<ArticleDto> articleDtos =  new ArrayList<>();
        for(Article article: articles){
            articleDtos.add(article.asDto());
        }
        response.setArticleDtos(articleDtos);
        return response;
    }


}
