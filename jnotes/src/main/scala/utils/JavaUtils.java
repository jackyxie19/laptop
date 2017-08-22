package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by wandepeng on 2017/7/21.
 */
public class JavaUtils {
    public static String list2String(List list, String separator){
        return StringUtils.join(list.toArray(),separator);
    }
}
