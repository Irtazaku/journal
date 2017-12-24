package com.journal.util;

import com.journal.dto.ArticleDto;
import com.journal.util.ConstantsAndEnums.GlobalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MapData {

	@Autowired
	private DateToStringHelper dateToStringHelper;

	@Autowired
	private GlobalConstants globalConstants;

	public static final Logger LOGGER = LoggerFactory.getLogger(MapData.class);

	@Value("${server.port}")
	private String serverPort;

	public Map<String,Object> dataMapping(List<ArticleDto> articleDtos,String fileName, Date editionDate){
		LOGGER.info("dataMapping METHOD STARTED");
		/*DateToStringHelper dateToStringHelper =new DateToStringHelper();*/
		long startTime = System.currentTimeMillis();
		Map<String,Object> data = new HashMap<String,Object>();

		try{

			data = setStaticContent(data);

			data.put("fileName", fileName);
			String editionDateString = dateToStringHelper.summaryDateFullMonth(editionDate);
			String[] parts = editionDateString.split("-");
			data.put("editionForMonth", parts[0]);
			data.put("editionForYear", parts[1]);
			data.put("editiontionFroMonthYear", editionDateString);
			List<Map<String, Object>> articleContents = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> articleTitles = new ArrayList<Map<String, Object>>();
			for(ArticleDto articleDto : articleDtos) {
				Map<String, Object> articleContent = new HashMap<String, Object>();
				Map<String, Object> articleTitle = new HashMap<String, Object>();

				articleTitle.put("articleTitle", articleDto.getTitle());
				articleTitle.put("pageNo", 1);
				articleContent.put("articleContent", articleDto.getContent());
				articleContent.put("articleAuthor", articleDto.getUser().getName());
				articleContent.put("articleTitle", articleDto.getTitle());
				articleContents.add(articleContent);
				articleTitles.add(articleTitle);

			}
			data.put("articleTitles", articleTitles);
			data.put("articleContents", articleContents);

			LOGGER.info("dataMapping METHOD DONE -- TOOK " + (System.currentTimeMillis() - startTime) + " millis");
			return data;
		}
		catch (Exception e) {
			LOGGER.info("dataMapping METHOD ABORTED WITH ERROR -- TOOK " + (System.currentTimeMillis() - startTime) + " millis");
			LOGGER.error("Error occured in mapping payload: ", e);
			return null;
		}

	}
	private Map<String, Object> setStaticContent(Map<String, Object> map){

		try{
			map.put(GlobalConstants.PDF_LOGO_NAME, /*GlobalConstants.RESOURCE_PATH + serverPort + */globalConstants.PDF_LOGO_PATH);
			map.put(GlobalConstants.CSS_STYLE_NAME, /*GlobalConstants.RESOURCE_PATH + serverPort + */globalConstants.CSS_STYLE_PATH);
			map.put("coverback", globalConstants.PDF_COVER_BACKGROUND_PATH);
			map.put("coverheader", globalConstants.PDF_COVER_HEADER_PATH);
		}
		catch(Exception e){
			LOGGER.error(GlobalConstants.MSG_ERROR_STATIC_CONTENT + GlobalConstants.PDF_LOGO_NAME, e);
		}
		return map;
	}
}