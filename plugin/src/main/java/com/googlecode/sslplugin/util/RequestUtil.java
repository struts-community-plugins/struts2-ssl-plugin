package com.googlecode.sslplugin.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class RequestUtil {

    public static String buildQueryString(HttpServletRequest request) {
        // add query string, if any
        String queryString = request.getQueryString();
        StringBuilder finalQs = new StringBuilder();

        if (queryString != null && queryString.length() != 0) {
            finalQs.append(queryString);
        } else {
            queryString = RequestUtil.getRequestParameters(request);
            if (queryString != null && queryString.length() != 0) {
                finalQs.append(queryString);
            }
        }

        return finalQs.length() == 0 ? null : finalQs.toString();
    }

    public static String getRequestParameters(HttpServletRequest aRequest) {
        return createQueryStringFromMap(aRequest.getParameterMap(), "&").toString();
    }

    public static StringBuffer createQueryStringFromMap(Map m, String ampersand) {
        StringBuffer aReturn = new StringBuffer("");
        Set aEntryS = m.entrySet();
        for (Object entry : aEntryS) {
            Map.Entry aEntry = (Map.Entry) entry;
            Object value = aEntry.getValue();
            String[] aValues = new String[1];
            if (value == null) {
                aValues[0] = "";
            } else if (value instanceof List) { // Work around for Weblogic 6.1sp1
                List aList = (List) value;
                aValues = (String[]) aList.toArray(new String[aList.size()]);
            } else if (value instanceof String) {  // Single value from Struts tags
                aValues[0] = (String) value;
            } else { // String array, the standard returned from request.getParameterMap()
                aValues = (String[]) value;  // This is the standard
            }
            for (String aValue : aValues) {
                append(aEntry.getKey(), aValue, aReturn, ampersand);
            }
        }
        return aReturn;
    }

    private static StringBuffer append(Object key, Object value, StringBuffer queryString, String ampersand) {
        if (queryString.length() > 0) {
            queryString.append(ampersand);
        }

        try {
            queryString.append(URLEncoder.encode(key.toString(), "UTF-8"));
            queryString.append("=");
            queryString.append(URLEncoder.encode(value.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {
        }

        return queryString;
    }

}
