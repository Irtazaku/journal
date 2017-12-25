package com.journal.endpoint;

import com.journal.dto.*;
import com.journal.entity.*;
import com.journal.util.ConstantsAndEnums.GlobalConstants;
import com.journal.util.ConstantsAndEnums.ResponseStatusCodeEnum;
import com.journal.util.EntityHelper;
import com.journal.util.Util;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Path("1")
public class Endpoint {

	private static String UPLOADED_FOLDER = "C://journals//";

	@Autowired
    private Util util;
    @Autowired
    private FileManager fileManager;
    @Autowired
    private UserManager  userManager;
    @Autowired
    private ArticleManager articleManager;

    @Autowired
    private JournalManager journalManager;

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
            Journal journal= journalManager.getJournalById(journalId);
            if(EntityHelper.isSet(journal.getId())){
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                response.setJournalDto(journal.asDto());
            }
        } catch (Exception e) {
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        return response;
    }

    @GET
    @Path("/getRecentJournals")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public JournalListResponseDto getRecentJournals(){
        JournalListResponseDto response = new JournalListResponseDto();
        try {
            List<JournalDto> journalDtos = journalManager.getRecentJournals();
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
            response.setJournalDtos(journalDtos);
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
            Journal savedJournal = journalManager.persist(journal);
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
	public Response download(@QueryParam("fileKey") String fileKey,
											 @QueryParam("id") Integer fileId) throws IOException {
        File file1 = new File();
		if(fileId != null){
			 file1 = fileManager.getFileById(fileId);
			fileKey = file1.getFileKey();
		}
		java.io.File file = new java.io.File(fileKey);
        String[] fileNameParts = fileKey.split("//");
        String returnType= fileKey.split("[.]")[1];
        file.renameTo(new java.io.File(fileNameParts[fileNameParts.length-1]));
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return Response.ok(file, "applicaion/"+returnType)
                .build();
                /*ResponseEntity.ok()
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);*/
	}

    @Path("/postfile")
    @POST
    @Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
    public ResponseHeaderDto postfile(
                             @DefaultValue("journal") @FormDataParam("type") String type,
                             @org.glassfish.jersey.media.multipart.FormDataParam("Filedata") FormDataContentDisposition disposition,
                             @org.glassfish.jersey.media.multipart.FormDataParam("Filedata") InputStream fileStream) throws IOException {
        try {
            byte[] bytes = IOUtils.toByteArray(fileStream);

            File file = fileManager.save(fileStream, disposition.getFileName(), type);
            if(EntityHelper.isNotNull(file) && EntityHelper.isSet(file.getId())){
                return ResponseStatusCodeEnum.SUCCESS.getHeader();
            }
            else {
                return ResponseStatusCodeEnum.ERROR.getHeader();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseStatusCodeEnum.ERROR.getHeader();
        }
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
                                           @QueryParam("token") String authToken) {
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
    @Path("/getArticlesList")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ArticlesListResponseDto getArticlesList (@QueryParam("token") String token) {
        ArticlesListResponseDto response = new ArticlesListResponseDto();
        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        User user = userManager.getUserByAuthenticationToken(token);
        if(EntityHelper.isNotNull(user) ) {
            List<Article> articles;
            if(user.getType().equals(GlobalConstants.USER_TYPE_USER)) {
                articles = articleManager.getAllArticleByUserId(user.getId());
            }else {
                articles = articleManager.getAllArticles();
            }
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

    @POST
    @Path("/createPdf")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseHeaderDto createPdf (@QueryParam("token") String token,
                                        List<Integer> articleIdList) {
        ResponseHeaderDto response = ResponseStatusCodeEnum.ERROR.getHeader();
        try {
            User user = userManager.getUserByAuthenticationToken(token);
            if(EntityHelper.isNotNull(user) && GlobalConstants.USER_TYPE_ADMIN.equals(user.getType())) {

                articleIdList.add(1);
                articleIdList.add(2);
                List<ArticleDto> articleDtos  = articleManager.getArticleListByIds(articleIdList);
                util.generateJournal(GlobalConstants.PDF_TEMPLET, "New Journal.pdf", articleDtos);
                response = ResponseStatusCodeEnum.SUCCESS.getHeader();
            }
        }catch (IOException e)
        {

        }

        return response;
    }


}
