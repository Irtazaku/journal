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
}
