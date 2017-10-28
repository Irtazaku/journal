package com.journal.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Created by Venturedive on 14/09/2017.
 */

@Component
public class GlobalConstants {

    /* constants */
    public final static String TYPE_NON_VISITED_AIRPORT =  "NonVisitedAirports";
    public final static String TYPE_VISITED_AIRPORT =  "VisitedAirports";
    public final static String TYPE_GLOBAL_BEST =  "Global";
    public final static Integer NUMBER_OF_PLACES =  5;
    public static final SimpleDateFormat DATE_FORMAT =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    public static final String VISITED_AIRPORT_MAP = "VisitedAirportSortedMap";
    public static final String FILE_SERVICE_ENDPOINT = "extranetServices/1.2/files/getpublicfile?fileKey=";

    /* Response codes */
    public final static int CODE_SUCCCES = 0;
    public final static int CODE_RECORD_NOT_FOUND = 1;
    public final static int CODE_ERROR_UNEXPECTED = 2;
    public final static int CODE_ERROR_RECORD_SIZE = 3;
    public final static int CODE_ERROR_SEARCH_KEYWORD = 4;
    public final static int CODE_ERROR_MS_INFO = 5;
    public final static int CODE_ERROR_INVALID_AIRPORT_ID = 6;

    /* Response messages */
    public final static String MSG_SUCCCES = "OK";
    public final static String MSG_SUCCESS_MS_INFO = "MS-Airport-Data is up";
    public final static String MSG_ERROR_MS_INFO = "Error in getting MS-Airport-Data info";
    public final static String MSG_RECORD_NOT_FOUND = "Record does not exist";
    public final static String MSG_ERROR_UNEXPECTED = "Something unexpected happened";
    public final static String MSG_ERROR_RECORD_SIZE = "Record size cannot be less than 1";
    public final static String MSG_ERROR_SEARCH_KEYWORD = "Keyword is missing";
    public final static String MSG_ERROR_INVALID_AIRPORT_ID = "Airport ID(s) missing";
    public final static String MSG_ERROR_RELOAD_DATA = "Reload data failed";
}
