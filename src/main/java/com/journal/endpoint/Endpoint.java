package com.journal.endpoint;

import com.journal.dto.*;
import com.journal.entity.*;
import com.journal.util.ConstantsAndEnums.GlobalConstants;
import com.journal.util.ConstantsAndEnums.ResponseStatusCodeEnum;
import com.journal.util.EntityHelper;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Path("1")
public class Endpoint {

	private static String UPLOADED_FOLDER = "C://journals//";

	@Autowired
	private FileRepository fileRepository;
    @Autowired
	private JournalRepository journalRepository;
    @Autowired
    private UserManager  userManager;
    @Autowired
    private ArticleManager articleManager;

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> msInfo() throws IOException {
		Map<String, Object> info = new HashMap<>();
		info.put("asd", "asdasda");
		return info;
	}

	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody
	UserResponseDto addNewUser (@FormDataParam("username") String username,
                                @FormDataParam("password") String password,
                                @FormDataParam("name") String name,
                                @FormDataParam("email") String email,
                                @DefaultValue(GlobalConstants.USER_TYPE_USER) @FormDataParam("type") String type) {
		UserResponseDto response = new UserResponseDto();
		response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
		if(!EntityHelper.isSet(username) || !EntityHelper.isSet(password) || !EntityHelper.isSet(name) || !EntityHelper.isSet(email)){
			response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
			return response;
		}
		User user = new User(email, username, password, name, type);
        try {
            user = userManager.persist(user);
        }catch (DataIntegrityViolationException e)
        {
            response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_ALREADY_EXIXST.getHeader());
            return response;
        }
		if(EntityHelper.isSet(user.getId())){
			response.setUserDto(user.asDto());
            response.getUserDto().setPassword(null);
			response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
		}
		return response;
	}

    @POST
    @Path("/resetPassword")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    UserResponseDto addNewUser (@QueryParam("token") String token,
                                @FormDataParam("oldPassword") String oldPassword,
                                @FormDataParam("newPassword") String newPassword) {
        UserResponseDto response = new UserResponseDto();
        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        if(!EntityHelper.isSet(oldPassword) || !EntityHelper.isSet(newPassword) || !EntityHelper.isSet(token)){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            return response;
        }
        User n = userManager.getUserByAuthenticationToken(token);
        if(!n.getPassword().equals(oldPassword)){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_LOGIN_DETAILS.getHeader());
            return response;
        }
        n.setPassword(newPassword);
        User user = userManager.merge(n);
        if(EntityHelper.isSet(user.getId())){
            response.setUserDto(user.asDto());
            response.getUserDto().setPassword(null);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
        }
        return response;
    }

    @POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	UserResponseDto getAllUsers(@FormDataParam("username") String username,
								@FormDataParam("password") String password){
		UserResponseDto response = new UserResponseDto();
		response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_LOGIN_DETAILS.getHeader());
        if(EntityHelper.isSet(username) && EntityHelper.isSet(password)) {
            User user = userManager.getUserByUsernameAndPassword(username, password);
            if (EntityHelper.isNotNull(user)) {

                response.setUserDto(user.asDto());
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());

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

    @Path("/postfile")
    @POST
    @Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
    public Response postfile(//@FormDataParam("file") FormDataBodyPart bodyPart,
                             @QueryParam("fileKey") String fileKey,
                             @org.glassfish.jersey.media.multipart.FormDataParam("Filedata") FormDataContentDisposition disposition,
                             @org.glassfish.jersey.media.multipart.FormDataParam("Filedata") InputStream fileStream) throws IOException {


        OutputStream out = null;

        try {
            byte[] bytes = IOUtils.toByteArray(fileStream);
            String storageKey=  fileKey+"." + disposition.getFileName().split("[.]")[1];
            out = new BufferedOutputStream(new FileOutputStream(UPLOADED_FOLDER + storageKey));
            out.write(bytes);
            out.flush();
            out.close();
            com.journal.entity.File file = new com.journal.entity.File();
            file.setFileName(fileKey);
            file.setFileKey(fileKey);
            file.setType(fileKey);
            fileRepository.save(file);

        } catch (IOException e) {
            e.printStackTrace();
            out.close();
            return null;//ResponseStatusCodeEnum.ERROR.getHeader();
        }

        return null;///ResponseStatusCodeEnum.SUCCESS.getHeader();
    }


    @POST
    @Path("/addArticle")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto addArticle (@FormDataParam("title") String title,
                                          @FormDataParam("content") String content,
                                         // @FormDataParam("user") Integer userId,
                                          @QueryParam("token") String authToken) {
        ArticleResponseDto response = new ArticleResponseDto();

        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        if(!EntityHelper.isSet(title) || !EntityHelper.isSet(content)){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            return response;
        }
        User user = userManager.getUserByAuthenticationToken(authToken);
        Article n = new Article(title, content, user, 0, null);
        Article article = articleManager.persist(n);
        if (EntityHelper.isSet(article.getId())) {
            response.setArticleDto(article.asDto());
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
        }
        return response;
    }

    @POST
    @Path("/editArticle")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto editArticle (@FormDataParam("title") String title,
                                           @FormDataParam("content") String content,
                                           @FormDataParam("articleId") Integer articleId,
                                           @HeaderParam("token") String authToken) {
        ArticleResponseDto response = new ArticleResponseDto();

        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        if(!EntityHelper.isSet(title) || !EntityHelper.isSet(content) || !EntityHelper.isSet(articleId)){
            response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            return response;
        }
        User user = userManager.getUserByAuthenticationToken(authToken);
        Article article = articleManager.getArticleByArticleId(articleId);
        if(article.getUser().getId().equals(user.getId()) || GlobalConstants.USER_TYPE_ADMIN.equals(user.getType())){
            article.setContent(content);
            article.setTitle(title);
            articleManager.merge(article);
            response.setArticleDto(article.asDto());
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
        }
        return response;
    }

    @GET
    @Path("/getMyArticlesList")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ArticlesListResponseDto getArticlesList (@HeaderParam("token") String token) {
        ArticlesListResponseDto response = new ArticlesListResponseDto();
        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        User user = userManager.getUserByAuthenticationToken(token);
        if(EntityHelper.isNotNull(user)) {
            List<Article> articles = articleManager.getAllArticleByUserId(user.getId());
            List<ArticleDto> articleDtos = new ArrayList<>();
            for (Article article : articles) {
                articleDtos.add(article.asDto());
            }
            response.setArticleDtos(articleDtos);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
        }
        return response;
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseHeaderDto logout (@QueryParam("token") String token) {
        ResponseHeaderDto response = ResponseStatusCodeEnum.ERROR.getHeader();
        User user = userManager.getUserByAuthenticationToken(token);
        if(EntityHelper.isNotNull(user)) {
            user.setToken(null);
            userManager.merge(user);
            response = ResponseStatusCodeEnum.SUCCESS.getHeader();
        }
        return response;
    }


}
