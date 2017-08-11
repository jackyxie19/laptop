package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jacky on 2017/7/21.
 */
public class JavaUtils {
    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("hello");
        arr.add("world");
        arr.add("tom");
        arr.add("jerry");
        String[] arr2 = new String[]{"hello", "world", "tom", "jerry"};
        String join = StringUtils.join(arr.toArray(), '|');
        String join2 = StringUtils.join(arr2, '|');
        System.out.println(join);
        System.out.println(join2);
    }

    public static String listToString5(List list, char separator) {
        return org.apache.commons.lang3.StringUtils.join(list.toArray(), separator);
    }

    public static String setToString5(Set set, char separator) {
        return org.apache.commons.lang3.StringUtils.join(set.toArray(), separator);
    }
}
