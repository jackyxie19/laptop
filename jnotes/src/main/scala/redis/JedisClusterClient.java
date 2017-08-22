package redis;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;

public class JedisClusterClient {
    private static Logger LOGGER = LogManager.getLogger(JedisClusterClient.class);
    private JedisCluster jedisConn;
    private Config config = ConfigFactory.load();

    private static JedisClusterClient jedisClusterClient;
    private JedisClusterClient(){
        init();
    }

    static {
        jedisClusterClient = new JedisClusterClient();
    }

    public static JedisClusterClient getJedisCluster(){
        return jedisClusterClient;
    }

    private void init(){
        int MAX_ACTIVE = config.getInt("REDIS.MAX_ACTIVE");
        int MAX_IDLE = config.getInt("REDIS.MAX_IDLE");
        int MAX_WAIT = config.getInt("REDIS.MAX_WAIT");
        int TIME_OUT = config.getInt("REDIS.TIMEOUT");
        int MAX_REDIRECTION = config.getInt("REDIS.MAX_REDIRECTION");
        int MAX_ATTEMPTS = config.getInt("REDIS.MAX_ATTEMPTS");
        String PASSWORD = config.getString("REDIS.PASSWORD");
//        String[] hostsArray = config.getString("REDIS_CLUSTER_HOSTS").split(",");
//        String[] portsArray = config.getString("REDIS_CLUSTER_PORTS").split(",");

        List<String> hostAndPostList = config.getStringList("REDIS.HOST_AND_PORT_LIST");

        HashSet<HostAndPort> jedisClusterHostAndPort=new HashSet<HostAndPort>();
//        for(int i =0;i<hostsArray.length;i++){
//            jedisClusterHostAndPort.add(new HostAndPort(hostsArray[i],Integer.parseInt(portsArray[i])));
//        }

        for(int i =0;i<hostAndPostList.size();i++){
            String str = hostAndPostList.get(i);
            String[] split = str.split(config.getString("REDIS.HOST_AND_PORT_SEPARATOR"));
            String host = split[0];
            int port = Integer.parseInt(split[1]);
            jedisClusterHostAndPort.add(new HostAndPort(host,port));
        }

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(MAX_ACTIVE);
        poolConfig.setMaxIdle(MAX_IDLE);
        poolConfig.setMaxWaitMillis(MAX_WAIT);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

//        jedisConn = new JedisCluster(jedisClusterHostAndPort,TIME_OUT,MAX_REDIRECTION,MAX_ATTEMPTS,PASSWORD,poolConfig);
        jedisConn = new JedisCluster(jedisClusterHostAndPort,TIME_OUT,MAX_REDIRECTION,MAX_ATTEMPTS,poolConfig);
    }

    public JedisCluster getJedisConn() {
        return jedisConn;
    }

    public void returnJedisConn(){
        try{
            jedisConn.close();
        }catch (Exception e){
            LOGGER.error("Fail to return jedis connection MSG : "+ e.getMessage());
        }
    }
}
