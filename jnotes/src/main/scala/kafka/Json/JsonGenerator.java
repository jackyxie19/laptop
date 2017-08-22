package kafka.Json;

import net.sf.json.JSONObject;

import java.util.Random;

public class JsonGenerator {


    private static  String someNames = "Churchill,Stalin,Hitler";
    private static  String statusStr="smoking,reading,sleeping";
    private static  String[] names = someNames.split(",");
    private static  String[] status = statusStr.split(",");


    public static JSONObject getOneJson(){
        JSONObject json = new JSONObject();
        getRandomNum(2,0);
        json.put("name",names[randomNum()]);
        json.put("status",status[randomNum()]);
        json.put("time",String.valueOf(System.currentTimeMillis()));
        System.out.println(json);
        return json;
    }

    private static int randomNum(){
        return getRandomNum(2,0);
    }

    public static int getRandomNum(int max, int min){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

    public static void main(String[] args){
        System.out.println();
        getOneJson();
    }
}
