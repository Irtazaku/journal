package com.journal.util;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class EntityHelper {

    public static <T> List<T> makeNotNull(List<T> list) {
        return list != null ? list : new ArrayList<T>();
    }

    public static boolean notNull(Object o) {
        return o != null;
    }

    public static boolean allNotNull(Object... o) {
        if (o == null) {
            return false;
        }
        for (Object o1 : o) {
            if (o1 == null) {
                return false;
            }
        }

        return true;
    }


    public static boolean isStringSet(String str) {
        if ((str != null) && !str.isEmpty()){
            return true;
        }
        return false;
    }

    public static String setDefaultStringIfEmptyOrNull(String str) {
        if ((str != null) && !str.isEmpty()){
            return str;
        }
        return "-";
    }

    public static boolean isIdSet(Integer... ids) {
        if (ids == null){
            return false;
        }
        for (Integer id : ids) {
            if ( !((id != null) && (id > 0)) )
                return false;
        }

        return true;
    }


    public static boolean idIsNotSet(Integer... ids) {
        if (ids == null) {
            return false;
        }
        for (Integer id : ids) {
            if (isIdSet(id)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIdSet(String id) {
        return isStringSet (id);
    }

    public static boolean isSet(Boolean b) {
        return b != null && b.equals(Boolean.TRUE);
    }

    public static boolean isSet(String s) {
        return isIdSet(s);
    }

    public static boolean isSet(Integer i) {
        return isIdSet(i);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }


    public static boolean isNotNull(Object... o) {
        for (Object o1 : o) {
            if (o1 == null){
                return false;
            }
        }
        return true;
    }

    public static boolean anyNull(Object... os) {
        for (Object o : os) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean allNull(Object... os) {
        for (Object o : os) {
            if (o != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean anyEmpty(String... os) {
        for (String o : os) {
            if (!isSet(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean allEmpty(String... os) {
        for (String o : os) {
            if (isSet(o)) {
                return false;
            }
        }
        return true;
    }

    public static <T> List<T> safeList(List<T> list) {
        return list == null ? Arrays.<T>asList() : list;
    }

    public static Boolean isListPopulated(List<?> list) {
        return list != null && !list.isEmpty();
    }

}
