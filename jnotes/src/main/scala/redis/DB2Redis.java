package redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DB2Redis {
    private static Logger LOGGER = LogManager.getLogger();
    private String DRIVER = "com.ibm.db2.jcc.DB2Driver";
    private String user;
    private String url;
    private String password;
    private Connection conn = null;
    private static int BATCH_AMT = 100000;

    public DB2Redis(String dbIP,String dbPort, String dbName, String dbUser, String dbPwd) {
        url = "jdbc:db2://"+dbIP+":"+dbPort+"/"+dbName;
        user = dbUser;
        password=dbPwd;
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url,user,password);
        } catch (Exception e){
            LOGGER.error("Fail to get connection from DB2 MSG: " + e.getMessage());
        }
    }

    public void close(){
        try{
            conn.close();
        } catch (Exception e){
            LOGGER.error("Fail to close connection from DB2 MSG : "+e.getMessage());
        }
    }

    public static int loadDB2Redis(String dbIP,String dbPort, String dbName, String dbUser, String dbPwd,
                                   String dbSchema, String dbTabName, String dbTabKey, String dbTabField, String dbFilter){
        try{
            String redisHashKey = "";
            int dbTabKeyNum = 0;
            int flag = 0;
            int index = 0;
            int[] dbTabFieldArrayIndex = new int[200];
            int[] dbTabKeyArrayIndex = new int[200];
            String[] dbTabFieldArray = new String[200];
            String[] dbTabKeyArray = new String[200];
            Map<String,String> data = new HashMap<String, String>();

            dbTabKeyArray = dbTabKey.replaceAll(" ","").toLowerCase().split(",");
            dbTabFieldArray = dbTabField.replaceAll(" ","").toLowerCase().split(",");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date());

            JedisClusterClient jcc = JedisClusterClient.getJedisCluster();
            JedisCluster jedis = jcc.getJedisConn();

            JedisClusterPipeline jcp = JedisClusterPipeline.pipelined(jedis);

            String sql = "";
            DB2Redis db2Redis = new DB2Redis(dbIP,dbPort,dbName,dbUser,dbPwd);
            Statement stmt = db2Redis.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            /*
             * 取出db2中数据,通过jcp注入redis.
             */
            //jcp.hmset();
            //jcp.sync();
            jcc.returnJedisConn();
        }catch (Exception e){
            LOGGER.error("Fail to load DB2 data MSG : " + e.getMessage());
            return 1;
        }
        return 0;
    }

    public static void main(String[] args){

    }
}
