package com.journal.util;

import com.journal.dto.ArticleDto;
import com.journal.dto.FileDto;
import com.journal.entity.File;
import com.journal.entity.FileManager;
import com.journal.util.ConstantsAndEnums.FileTypeEnum;
import com.journal.util.ConstantsAndEnums.GlobalConstants;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class Util {
    public static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
    @Autowired
    private MapData mapData;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private FileManager fileManager;

    public  static String generateToken(){
        String uid = java.util.UUID.randomUUID().toString();
        return uid.replace("-", "");
    }

    public static String generateFileKey(String fileType, String fileName){
        String fileKey = GlobalConstants.BASE_FILE_PATH + FileTypeEnum.valueOf(fileType).getValue() + fileName;
        return fileKey;
    }

    public FileDto generateJournal(String templateName, String fileName, List<ArticleDto> articleDtos) throws IOException{
        FileDto response = new FileDto();
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        String processedHtml = null;
        /*mapData = new MapData();*/
        Map<String,Object> map = mapData.dataMapping(articleDtos, fileName, new Date());
        /*TemplateEngine templateEngine = new SpringTemplateEngine();*/

        Context ctx = new Context();
        ctx.setVariables(map);

        if(map != null && ctx != null){

            try {
                String uuId = UUID.randomUUID().toString();

                ITextRenderer renderer = new ITextRenderer();
                renderer = loadFonts(renderer);
                String coverPageString = templateEngine.process(GlobalConstants.AS_COVER_PAGE, ctx);
                processedHtml = templateEngine.process(templateName, ctx);
                LOGGER.info("coverPage html: " + coverPageString);
                LOGGER.info("processed html: " + processedHtml);
                renderer.setDocumentFromString(coverPageString);
                renderer.layout();
                renderer.createPDF(pdfOut, true);
                renderer.setDocumentFromString(processedHtml);
                renderer.layout();
                renderer.writeNextDocument();
                renderer.finishPDF();
                LOGGER.info(GlobalConstants.MSG_SUCCESS_PDF_CONVERSION);


                LOGGER.info(String.format("Uploading PDF to ES. FileKey = %s", fileName));
                File file = fileManager.save(pdfOut.toByteArray(), fileName, FileTypeEnum.journal.getKey());
                if (EntityHelper.isSet(file.getId())) {
                    //header = ResponseStatusCodeEnum.SUCCESS_PDF_CONVERSION.getHeader();
                    response =  file.asDto();
                }
                else {
                    LOGGER.info("Error while uploading PDF");
                    response =  null;
                }
            }
            catch(IllegalStateException e){
                LOGGER.error(GlobalConstants.MSG_ERROR_ILLEGAL_STATEMENT, e);
            }
            catch(TemplateProcessingException e){
                LOGGER.error(GlobalConstants.MSG_ERROR_TEMPLATE_PARSING, e);
            }
            catch (DocumentException e) {
                LOGGER.error(GlobalConstants.MSG_ERROR_TEMPLATE_CONVERSION, e);
            }
            return response;
        }
        else{
            LOGGER.info(GlobalConstants.MSG_ERROR_PAYLOAD_MAPPING);
            return response;
        }
    }
    public ITextRenderer loadFonts(ITextRenderer renderer){
        try{
            renderer.getFontResolver().flushFontFaceFonts();
            for(String font : GlobalConstants.PDF_FONT_FAMILIES){
                renderer.getFontResolver().addFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
        }
        catch (Exception e) {
            LOGGER.error(GlobalConstants.MSG_ERROR_FONTS_LOADING, e);
        }
        return renderer;
    }



}
