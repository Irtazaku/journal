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
    public static final String IMAGE_FILE_KEY = "image";
    public static final String IMAGE_FILE_VALUE = "image//";
    public static final String JOURNAL_FILE_KEY = "journal";
    public static final String JOURNAL_FILE_VALUE = "journal//";
    public static final String BASE_FILE_PATH = "C://journals//";


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
    public final static String MSG_ERROR_CONTRACT_TIMELINE = "Error in calculating contract timeline";
    public final static String MSG_ERROR_MS_INFO = "Error in getting microservice info";
    public final static String MSG_ERROR_FILE_UPLOAD  = "Error while uploading File. The File Service Maybe Down. Please try again later";
    public final static String MSG_ERROR_STATIC_CONTENT = "File not found with name ";
    public final static String AS_COVER_PAGE = "coverPage";
    public final static String AS_INDEX_PAGE = "contentPage";
    public final static String PDF_TEMPLET = "mainFile";


    /* Constants */
    public final static Integer numOfTitlePerPage = 15;
    public final static Double KM_TO_MILES = 0.6214;
    public final static Double DEFAULT_DOUBLE_VALUE = 0.0;

    public final static Double IDEAL_MONTH_DOUBLE = 30.0;
    public final static Integer IDEAL_MONTH_INT = 30;

    public final static String PDF_LOGO_NAME = "logo";
    public final static String CSS_STYLE_NAME = "styleSheet";
    public final static String PDF_LOGO_PATH = "file:///C://Users//Venturedive//Documents//FYP//journal//src//main//resources//static//img//logo.jpg";
    public final static String CSS_STYLE_PATH = "file:///C://Users//Venturedive//Documents//FYP//journal//src//main//resources//static//css//style.css";

    public final static String DECIMAL_FORMATTER_CURRENCY = "###,###,###,###,##0.00";
    public final static String DECIMAL_FORMATTER_HOURS_1 = "###.#";
    public final static String DECIMAL_FORMATTER_HOURS_2 = "####0.00";
    public final static String DECIMAL_FORMATTER_HOURS_3 = "####0.##";
    public final static String DECIMAL_FORMATTER_HOURS_4 = "####0.0000";

    public final static Integer DECIMAL_FORMATTER_1 = 10;
    public final static Integer DECIMAL_FORMATTER_2 = 100;
    public final static Integer DECIMAL_FORMATTER_4 = 10000;



    public static final Short REMAINING_MONTHS_PIXELS = 307;
    public static final Short REMAINING_HOURS_PIXELS = 316;
    public static final String MONTH_DEFAULT_TEXT = "0 Month";
    public static final String HOUR_DEFAULT_TEXT = "0 Hour";
    public static final Integer MONTH_DEFAULT_SIZE = MONTH_DEFAULT_TEXT.length();
    public static final Integer HOUR_DEFAULT_SIZE = HOUR_DEFAULT_TEXT.length();
    public static final Short PIXEL_PER_CHARACTER = 7;
    public static final Short EXTRA_PADDING_TIMELINE = 4;
    public static final Short EXTRA_PADDING_HOURS_TIMELINE = 10;
    public static final Short EXTRA_PADDING_HOURS_TIMELINE_NO_HOURS_USED = 12;
    public static final Short MAX_WIDTH = 100;

    /* Date formats */
    public static final String DF_DEFAULT_DATE_STRING = " - ";
    public static final String DF_WITH_MONTH_CODE_ONE = "dd-MMM-yyyy";
    public static final String DF_WITH_MONTH_CODE_TWO = "MMM-yyyy";
    public static final String DF_WITH_FULL_MONTH = "MMMM-yyyy";
    public static final String DF_PERIOD = "dd MMMM yyyy";
    public static final String DF_TIME = "(HH:mm)";
}
