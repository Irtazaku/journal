package com.journal.endpoint;

import com.journal.dto.*;
import com.journal.entity.*;
import com.journal.util.ConstantsAndEnums.GlobalConstants;
import com.journal.util.ConstantsAndEnums.ResponseStatusCodeEnum;
import com.journal.util.EntityHelper;
import com.journal.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@Component
@Path("1")
public class Endpoint {

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

    public static final Logger LOGGER= Logger.getLogger(Endpoint.class);

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> msInfo(@Context HttpServletResponse response) {
        LOGGER.info("info() started");
		Map<String, Object> info = new HashMap<>();
		info.put("Project", "com.journal");
		info.put("version", "V1.0");
        Map<String, Map<String, String>> teamMemberInfoMap= new HashMap<>();
        Map<String, String> indiviualInfoMap= new HashMap<>();
        indiviualInfoMap.put("name", "Syed Irtaza Zaidi");
        indiviualInfoMap.put("Seat No", "EP-1349103");
        teamMemberInfoMap.put("Syed Irtaza Zaidi", indiviualInfoMap);
        indiviualInfoMap.put("name", "Syed Talha Hai");
        indiviualInfoMap.put("Seat No", "EP-1349113");
        teamMemberInfoMap.put("Syed Talha Hai", indiviualInfoMap);
        indiviualInfoMap.put("name", "Muhammad Furqan Chipa");
        indiviualInfoMap.put("Seat No", "EP-1349062");
        teamMemberInfoMap.put("Muhammad Furqan Chipa", indiviualInfoMap);
        info.put("Team Members", teamMemberInfoMap);
        response.setHeader("Access-Control-Allow-Origin", "*");
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
                                @DefaultValue(GlobalConstants.USER_TYPE_USER) @FormDataParam("type") String type,
                                @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("addNewUser() started. with params ")
                .append("username: ").append(username)
                .append("password: ").append(password)
                .append("name: ").append(name)
                .append("email: ").append(email));
		UserResponseDto response = new UserResponseDto();
		if(!EntityHelper.isSet(username) || !EntityHelper.isSet(password) || !EntityHelper.isSet(name) || !EntityHelper.isSet(email)){
			response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
		} else {
            User user = new User(email, username, password, name, type);
            try {
                user = userManager.persist(user);
                if (EntityHelper.isSet(user.getId())) {
                    response.setUserDto(user.asDto());
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                } else {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
                }
            } catch (DataIntegrityViolationException e) {
                LOGGER.error("error while persisting new user in database", e);
                response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_ALREADY_EXIXST.getHeader());
            }
        }
        LOGGER.info(String.format("addNewUser() ended with isError %s.",  response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
		return response;
	}

    @POST
    @Path("/resetPassword")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody
    UserResponseDto resetPassword (@QueryParam("token") String token,
                                   @FormDataParam("oldPassword") String oldPassword,
                                   @FormDataParam("newPassword") String newPassword,
                                   @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("resetPassword() started. with params ")
                .append("token: ").append(token)
                .append("oldPassword: ").append(oldPassword)
                .append("newPassword: ").append(newPassword));
        UserResponseDto response = new UserResponseDto();
        try {
            if (!EntityHelper.isSet(oldPassword) || !EntityHelper.isSet(newPassword) || !EntityHelper.isSet(token)) {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            } else {
                User n = userManager.getUserByAuthenticationToken(token);
                if (!n.getPassword().equals(oldPassword)) {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_LOGIN_DETAILS.getHeader());
                } else {
                    n.setPassword(newPassword);
                    User user = userManager.merge(n);
                    if ( EntityHelper.isNotNull(user) && EntityHelper.isSet(user.getId())) {
                        response.setUserDto(user.asDto());
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                    } else {
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
                    }
                }
            }
        } catch (Exception e){
            LOGGER.error(String.format("error while getJournalById() -> token: %s", token), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("resetPassword() -> %s ended with isError %s.", token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody
	UserResponseDto login(@FormDataParam("username") String username,
                          @FormDataParam("password") String password,
                          @Context HttpServletResponse responses){
        LOGGER.info(new StringBuilder("login() started. with params ")
                .append("username: ").append(username)
                .append("password: ").append(password));
		UserResponseDto response = new UserResponseDto();
		if(EntityHelper.isSet(username) && EntityHelper.isSet(password)) {
            User user = userManager.getUserByUsernameAndPassword(username, password);
            if (EntityHelper.isNotNull(user)) {
                response.setUserDto(user.asDto());
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());

            }else {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_LOGIN_DETAILS.getHeader());
            }
        } else {
            response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
        }
        LOGGER.info(String.format("login() ended with isError %s.", response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
		return response;
	}



    @GET
    @Path("/getJournalById")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public JournalResponseDto getJournalById(@QueryParam("journalId") Integer journalId,
                                             @QueryParam("token") String token /*Optional*/,
                                             @Context HttpServletResponse responses){
        LOGGER.info(new StringBuilder("getJournalById() started. with params ")
                .append("journalId: ").append(journalId)
                .append("token: ").append(token));
        JournalResponseDto response = new JournalResponseDto();
        try {
            Journal journal= journalManager.getJournalById(journalId);
            if(EntityHelper.isNotNull(journal) && EntityHelper.isSet(journal.getId())){
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                response.setJournalDto(journal.asDto());
            } else {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_DETAILS.getHeader());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("error while getJournalById() -> token: %s; journalId: %s", token, journalId.toString()), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("getJournalById() -> %s ended with isError %s.", token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @GET
    @Path("/getRecentJournals")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public JournalListResponseDto getRecentJournals(@QueryParam("token") String token, /*Optional*/
                                                    @DefaultValue("") @QueryParam("queryString") String queryString,
                                                    @Context HttpServletResponse responses){
        LOGGER.info(new StringBuilder("getRecentJournals() started. with params ")
                .append("token: ").append(token)
                .append("queryString: ").append(queryString));
        JournalListResponseDto response = new JournalListResponseDto();
        try {
            List<JournalDto> journalDtos = journalManager.getRecentJournals("%" + queryString + "%");
            response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
            response.setJournalDtos(journalDtos);
        } catch (Exception e) {
            LOGGER.error(String.format("error while getRecentJournals() -> token: %s", token), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("getJournalById() -> %s ended with isError %s.", token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @POST
    @Path("/addJournal")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    JournalResponseDto addJournal(@QueryParam("token") String token,
                                  @FormDataParam("name") String name,
                                  @FormDataParam("Abstract") String Abstract,
                                  @DefaultValue(GlobalConstants.PDF_COVER_BACKGROUND_PATH) @FormDataParam("coverKey") String coverKey,
                                  @FormDataParam("articleIds") List<Integer> articleIds,
                                  @FormDataParam("date") String date,
                                  @Context HttpServletResponse responses){
        LOGGER.info(new StringBuilder("addJournal() started. with params ")
                .append("token: ").append(token)
                .append("name: ").append(name)
                .append("Abstract: ").append(Abstract)
                .append("coverKey: ").append(coverKey)
                .append("articleIds: ").append(articleIds)
                .append("date: ").append(date));
        JournalResponseDto response = new JournalResponseDto();
        try {
            User user = userManager.getUserByAuthenticationToken(token);
            if (EntityHelper.isNotNull(user) && GlobalConstants.USER_TYPE_ADMIN.equals(user.getType())) {
                List<ArticleDto> articleDtos = articleManager.getArticleListByIds(articleIds);
                FileDto fileDto;
                fileDto = util.generateJournal(GlobalConstants.PDF_TEMPLET,
                        String.format("%s.%s", name, "pdf"), articleDtos, coverKey);

                if(EntityHelper.isNotNull(fileDto)) {
                    Journal journal = new Journal();
                    journal.setName(name);
                    journal.setAbstract(Abstract);
                    journal.setFileKey(fileDto.getFileKey());
                    journal.setDate(new Date());
                    journal.setJournal(fileDto.asEntity());
                    journal.setNumberOfViews(0);
                    journal.setPublisher(GlobalConstants.JOURNAL_PUBLISHER_NAME);
                    if (!GlobalConstants.PDF_COVER_BACKGROUND_PATH.equals(coverKey)) {
                        journal.setImage(fileManager.getFileByKeyAndType(coverKey, GlobalConstants.IMAGE_FILE_KEY));
                    }
                    journal = journalManager.persist(journal);
                    if (EntityHelper.isSet(journal.getId())) {
                        response.setJournalDto(journal.asDto());
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                    } else {
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
                    }
                } else {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
                }
            } else {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("error while addJournal() -> token: %s", token), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("addJournal() -> %s ended with isError %s.",
                token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }


    @GET
	@Path("/downloadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public Response downloadFile(@QueryParam("token") String token, /*Optional*/
                                 @QueryParam("fileKey") String fileKey,
                                 @QueryParam("id") Integer fileId,
                                 @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("downloadFile() started. with params ")
                .append("token: ").append(token)
                .append("fileKey: ").append(fileKey)
                .append("fileId: ").append(fileId));
        try {
            File document;
            if (fileId != null) {
                document = fileManager.getFileById(fileId);
                fileKey = document.getFileKey();
            } else {
                document = fileManager.getFileByKey(fileKey);
            }

            java.io.File file = new java.io.File(fileKey);
            String returnType = fileKey.split("[.]")[1];
            LOGGER.info(String.format("download() -> %s ended with isError %s.", token, Boolean.FALSE));
            /*return Response.ok(file, String.format("applicaion/%s", returnType))
                    .build();*/
            String fileType;
            switch (document.getType()){
                case GlobalConstants.IMAGE_FILE_KEY:
                    fileType = String.format("image/%s", returnType);
                    break;
                case GlobalConstants.JOURNAL_FILE_KEY:
                    fileType = "application/pdf";
                    break;
                default:
                    fileType = String.format("applicaion/%s", returnType);
            }
            responses.setHeader("Access-Control-Allow-Origin", "*");
            return Response.ok(file)
                    .type(fileType)
                    .header("Content-Disposition", String.format("inline; filename=%s", document.getFileName()))
                    .build();
        }catch (Exception e){
            LOGGER.error(String.format("downloadFile() -> %s ended with isError %s.", token, Boolean.TRUE), e);
            responses.setHeader("Access-Control-Allow-Origin", "*");
            return Response.ok().status(Response.Status.NOT_FOUND).build();
        }
	}

    @Path("/postfile")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResponseDto postfile(
                             @DefaultValue(GlobalConstants.IMAGE_FILE_KEY) @FormDataParam("type") String type,
                             @QueryParam("token") String token,
                             @FormDataParam("Filedata") FormDataContentDisposition disposition,
                             @FormDataParam("Filedata") InputStream fileStream,
                             @Context HttpServletResponse responses) throws IOException {
        LOGGER.info(new StringBuilder("postfile() started. with params ")
                .append("token: ").append(token)
                .append("Filedata: ").append(EntityHelper.isNotNull(disposition) ? disposition.getFileName(): ""));
        FileResponseDto response = new FileResponseDto();
        try {
            if(EntityHelper.isNotNull(fileStream)) {
                byte[] bytes = IOUtils.toByteArray(fileStream);
                File file = fileManager.save(bytes, disposition.getFileName(), type);
                if (EntityHelper.isNotNull(file) && EntityHelper.isSet(file.getId())) {
                    response.setFileDto(file.asDto());
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                } else {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
                }
            } else {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_DETAILS.getHeader());
            }

        } catch (Exception e) {
            LOGGER.error(String.format("error while postfile() -> %s.", token), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("postfile() -> %s ended with isError %s.", token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }


    @POST
    @Path("/addArticle")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto addArticle (@FormDataParam("title") String title,
                                          @FormDataParam("content") String content,
                                          @QueryParam("token") String authToken,
                                          @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("addArticle() started. with params ")
                .append("authToken: ").append(authToken)
                .append("title: ").append(title)
                .append("content: ").append(content));
        ArticleResponseDto response = new ArticleResponseDto();
        try {
            if (!EntityHelper.isSet(title) || !EntityHelper.isSet(content)) {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
            } else {
                User user = userManager.getUserByAuthenticationToken(authToken);
                if (EntityHelper.isNotNull(user)) {
                    Article article = new Article(title, content, user, 0, null);
                    article = articleManager.persist(article);
                    if (EntityHelper.isSet(article.getId())) {
                        response.setArticleDto(article.asDto());
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                    } else {
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
                    }
                }else {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader());
                }
            }
        } catch (Exception e){
            LOGGER.error(String.format("error while addArticle() -> %s.", authToken), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }

        LOGGER.info(String.format("addArticle() -> %s ended with isError %s.", authToken, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @POST
    @Path("/editArticle")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto editArticle (@FormDataParam("title") String title,
                                           @FormDataParam("content") String content,
                                           @FormDataParam("articleId") Integer articleId,
                                           @FormDataParam("status") Integer status,
                                           @QueryParam("token") String authToken,
                                           @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("editArticle() started. with params ")
                .append("authToken: ").append(authToken)
                .append("title: ").append(title)
                .append("articleId: ").append(articleId)
                .append("status: ").append(status)
                .append("content: ").append(content));
        ArticleResponseDto response = new ArticleResponseDto();
        try {
            if (!EntityHelper.isSet(title) || !EntityHelper.isSet(content) || !EntityHelper.isSet(articleId)) {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());

            } else {
                User user = userManager.getUserByAuthenticationToken(authToken);
                Article article = articleManager.getArticleByArticleId(articleId);
                if(EntityHelper.isNotNull(user,article)) {
                    if (GlobalConstants.USER_TYPE_ADMIN.equals(user.getType()) || article.getUser().getId().equals(user.getId())) {
                        article.setContent(content);
                        article.setTitle(title);
                        if (EntityHelper.isIdSet(status)) {
                            article.setStatus(status);
                        }
                        articleManager.merge(article);
                        response.setArticleDto(article.asDto());
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                    } else {
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader());
                    }
                } else {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.INVALID_DETAILS.getHeader());
                }
            }
        } catch (Exception  e) {
            LOGGER.error(String.format("error while editArticle() -> %s.", authToken), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("editArticle() -> %s ended with isError %s.", authToken, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @GET
    @Path("/getArticleById")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ArticleResponseDto getArticleById (@QueryParam("token") String token,
                                              @QueryParam("articleId") Integer articleId,
                                              @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("getArticleById() started. with params ")
                .append("token: ").append(token)
                .append("articleId: ").append(articleId));
        ArticleResponseDto response = new ArticleResponseDto();
        try {
            User user = userManager.getUserByAuthenticationToken(token);
            if (EntityHelper.isNotNull(user)) {
                if (EntityHelper.isNotNull(articleId)) {
                    ArticleDto article = articleManager.getArticleByArticleId(articleId).asDto();
                    if (GlobalConstants.USER_TYPE_ADMIN.equals(user.getType()) || (EntityHelper.isNotNull(article) && article.getUser().getId().equals(user.getId()))) {
                        response.setArticleDto(article);
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
                    } else {
                        response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader());
                    }
                } else {
                    response.setResponseHeaderDto(ResponseStatusCodeEnum.BAD_REQUEST.getHeader());
                }
            } else {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("error while getArticleById() -> %s.", token), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("getArticleById() -> %s ended with isError %s.", token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @GET
    @Path("/getArticlesList")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ArticlesListResponseDto getArticlesList (@QueryParam("token") String token,
                                                    @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("getArticlesList() started. with params ")
                .append("token: ").append(token));
        ArticlesListResponseDto response = new ArticlesListResponseDto();
        try {
            User user = userManager.getUserByAuthenticationToken(token);
            if (EntityHelper.isNotNull(user)) {
                List<Article> articles;
                if (user.getType().equals(GlobalConstants.USER_TYPE_USER)) {
                    articles = articleManager.getAllArticleByUserId(user.getId());
                } else {
                    articles = articleManager.getAllArticles();
                }
                List<ArticleDto> articleDtos = new ArrayList<>();
                for (Article article : EntityHelper.safeList(articles)) {
                    article.setContent(null);
                    articleDtos.add(article.asDto());
                }
                response.setArticleDtos(articleDtos);
                response.setResponseHeaderDto(ResponseStatusCodeEnum.SUCCESS.getHeader());
            } else {
                response.setResponseHeaderDto(ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader());
            }
        } catch (Exception e){
            LOGGER.error(String.format("error while getArticlesList() -> %s.", token), e);
            response.setResponseHeaderDto(ResponseStatusCodeEnum.ERROR.getHeader());
        }
        LOGGER.info(String.format("getArticlesList() -> %s ended with isError %s.", token, response.getResponseHeaderDto().getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public ResponseHeaderDto logout (@QueryParam("token") String token,
                                     @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("logout() started. with params ")
                .append("token: ").append(token));
        ResponseHeaderDto response;
        try {
            User user = userManager.getUserByAuthenticationToken(token);
            if (EntityHelper.isNotNull(user)) {
                user.setToken(null);
                userManager.merge(user);
                response = ResponseStatusCodeEnum.SUCCESS.getHeader();
            } else {
                response = ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader();
            }
        } catch (Exception e){
            LOGGER.error(String.format("error while logout() -> %s.", token), e);
            response = ResponseStatusCodeEnum.ERROR.getHeader();
        }
        LOGGER.info(String.format("logout() -> %s ended with isError %s.", token, response.getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @POST
    @Path("/createPdf")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseHeaderDto createPdf (@QueryParam("token") String token,
                                        @FormDataParam("articleIdList") List<Integer> articleIdList,
                                        @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("createPdf() started. with params ")
                .append("token: ").append(token)
                .append("articleIdListOfSize: ").append(articleIdList.size()));
        ResponseHeaderDto response = ResponseStatusCodeEnum.ERROR.getHeader();
        try {
            User user = userManager.getUserByAuthenticationToken(token);
            if(EntityHelper.isNotNull(user) && GlobalConstants.USER_TYPE_ADMIN.equals(user.getType())) {

                articleIdList.add(1);
                articleIdList.add(2);
                List<ArticleDto> articleDtos  = articleManager.getArticleListByIds(articleIdList);
                util.generateJournal(GlobalConstants.PDF_TEMPLET, "New Journal.pdf", articleDtos, "cover");
                response = ResponseStatusCodeEnum.SUCCESS.getHeader();
            }else {
                response = ResponseStatusCodeEnum.USER_NOT_AUTHORIZED.getHeader();
            }
        }catch (IOException e) {
            LOGGER.error(String.format("error while createPdf() -> %s.", token), e);
            response = ResponseStatusCodeEnum.ERROR.getHeader();
        }
        LOGGER.info(String.format("createPdf() -> %s ended with isError %s.", token, response.getIsError()));
        responses.setHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @POST
    @Path("/dosPDF")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Produces(MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response dosPDF(@FormDataParam("fileName") String fileName,
                           @FormDataParam("coverHtml") String coverHtml,
                           @FormDataParam("articlesHtml") List<String> articlesHtml,
                           @Context HttpServletResponse responses) {
        LOGGER.info(new StringBuilder("downloadFile() started. with params")
                .append(" fileName: ").append(fileName)
                .append(" coverHtml: ").append(coverHtml)
                .append(" articlesHtmlofSize: ").append(articlesHtml.size()));
        try {
            byte[] pdf = util.generateRunTime(coverHtml, articlesHtml, fileName);
            LOGGER.info(String.format("download() ended with isError %s.", Boolean.FALSE));
            responses.setHeader("Access-Control-Allow-Origin", "*");
            return Response.ok(pdf)
                    .type("application/pdf")
                    .header("Content-Disposition", String.format("inline; filename=%s", fileName))
                    .build();
        }catch (Exception e){
            LOGGER.error(String.format("downloadFile() ended with isError %s.", Boolean.TRUE), e);
            responses.setHeader("Access-Control-Allow-Origin", "*");
            return Response.ok().status(Response.Status.BAD_REQUEST).build();
        }
    }
}
