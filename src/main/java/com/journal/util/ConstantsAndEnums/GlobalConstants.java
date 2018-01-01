package com.journal.util.ConstantsAndEnums;

import org.springframework.stereotype.Component;

/**
 * Created by Venturedive on 14/09/2017.
 */

@Component
public class GlobalConstants {

     public static final String USER_TYPE_ADMIN = "admin";
    public static  final String USER_TYPE_USER = "user";
    public static  final Integer ARTICLE_STATUS_PENDDING = 0;
    public static  final Integer ARTICLE_STATUS_APPROVED = 1;
    public static  final Integer ARTICLE_STATUS_REJECTED = 2;
    public static  final Integer ARTICLE_STATUS_PUBLISHED = 3;
    public static  final Integer ARTICLE_STATUS_DELETED = 4;
    public static final String IMAGE_FILE_KEY = "image";
    public static final String IMAGE_FILE_VALUE = "image//";
    public static final String JOURNAL_FILE_KEY = "journal";
    public static final String JOURNAL_FILE_VALUE = "journal//";




    /* PDF fonts */
    public final static String FONTS_PATH = "static/fonts/";
    public final static String FONT_A_CASLON_BOLD = FONTS_PATH + "ACaslonPro-Bold.otf";
    public final static String FONT_A_CASLON_BOLD_ITALIC = FONTS_PATH + "ACaslonPro-BoldItalic.otf";
    public final static String FONT_A_CASLON_ITALIC = FONTS_PATH + "ACaslonPro-Italic.otf";
    public final static String FONT_A_CASLON_REGULAR = FONTS_PATH + "ACaslonPro-Regular.otf";
    public final static String FONT_A_CASLON_SEMI_BOLD = FONTS_PATH + "ACaslonPro-Semibold.otf";
    public final static String FONT_A_CASLON_SEMI_BOLD_ITALIC = FONTS_PATH + "ACaslonPro-SemiboldItalic.otf";
    public final static String FONT_CASLON_SC = FONTS_PATH + "caslon-sc.otf";
    public final static String FONT_MUSEO_SANS = FONTS_PATH + "MuseoSans_300.otf";
    public final static String[] PDF_FONT_FAMILIES = {FONT_A_CASLON_BOLD, FONT_A_CASLON_BOLD_ITALIC, FONT_A_CASLON_ITALIC,
            FONT_A_CASLON_REGULAR, FONT_A_CASLON_SEMI_BOLD, FONT_A_CASLON_SEMI_BOLD_ITALIC, FONT_CASLON_SC,
            FONT_MUSEO_SANS};

    public final static String MSG_SUCCESS_PDF_CONVERSION = "Success - Byte array conversion done";
    public final static String MSG_ERROR_ILLEGAL_STATEMENT = "Unable to generate byte stream - Illegal statement error in html template";
    public final static String MSG_ERROR_TEMPLATE_PARSING = "Unable to generate byte stream - Template parsing error";
    public final static String MSG_ERROR_TEMPLATE_CONVERSION = "Unable to generate byte stream - Error while preparing template for conversion to PDF";
    public final static String MSG_ERROR_PAYLOAD_MAPPING = "Unable to generate byte stream - Error occured in mapping payload";
    public final static String MSG_ERROR_FONTS_LOADING = "Unable to generate byte stream - Error in loading fonts";
    public final static String MSG_ERROR_STATIC_CONTENT = "File not found with name ";

    //config 1
    public final static String AS_COVER_PAGE = /*"coverPage";*/ "cover";
    public final static String AS_INDEX_PAGE = /*"contentPage";*/ "toc";
    public final static String PDF_TEMPLET =  /* "mainPage"; */ "content";

  /*  //config 2
    public final static String AS_COVER_PAGE = "coverPage";
    public final static String AS_INDEX_PAGE ="contentPage";
    public final static String PDF_TEMPLET =   "mainPage";   */

    public final static String PDF_LOGO_NAME = "logo";
    public final static String CSS_STYLE_NAME = "styleSheet";

    /* Date formats */
    public static final String DF_DEFAULT_DATE_STRING = " - ";
    public static final String DF_WITH_MONTH_CODE_ONE = "dd-MMM-yyyy";
    public static final String DF_WITH_MONTH_CODE_TWO = "MMM-yyyy";
    public static final String DF_WITH_FULL_MONTH = "MMMM-yyyy";
    public static final String DF_PERIOD = "dd MMMM yyyy";
    public static final String DF_TIME = "(HH:mm)";

    public static final String CONTENT_TYPE_PDF = "application/pdf";
    //PROD COnfig
    public static final String FILE_DOWNLOAD_BASE_URL = "http://18.217.17.151:80/journal/1/downloadFile?fileKey=";
    public static final String PDF_COVER_BACKGROUND_PATH = "file://///root//journalRepo//journal//src//main//resources//static//img//cover_back.png";
    public static final String PDF_COVER_HEADER_PATH = "file://///root//journalRepo//journal//src//main//resources//static//img//cover_header.png";
    public static final String PDF_LOGO_PATH = "file://///root//journalRepo//journal//src//main//resources//static//img//logo.jpg";
    public static final String CSS_STYLE_PATH = "file://///root//journalRepo//journal//src//main//resources//static//css//style.css";
    public static final String BASE_FILE_PATH = "//root//journalRepo//fileStorage//";


    //DEV Config
/*
    public static final String FILE_DOWNLOAD_BASE_URL = "http://localhost:54728/journal/1/downloadFile?fileKey=";
    public static final String PDF_COVER_BACKGROUND_PATH = "file:///C://Users//Venturedive//Documents//FYP//journal//src//main//resources//static//img//cover_back.png";
    public static final String PDF_COVER_HEADER_PATH = "file:///C://Users//Venturedive//Documents//FYP//journal//src//main//resources//static//img//cover_header.png";
    public static final String PDF_LOGO_PATH = "file:///C://Users//Venturedive//Documents//FYP//journal//src//main//resources//static//img//logo.jpg";
    public static final String CSS_STYLE_PATH = "file:///C://Users//Venturedive//Documents//FYP//journal//src//main//resources//static//css//style.css";
    public static final String BASE_FILE_PATH = "C://journals//";*/
    public static final String JOURNAL_PUBLISHER_NAME = "UBIT JOURNALS";
}
