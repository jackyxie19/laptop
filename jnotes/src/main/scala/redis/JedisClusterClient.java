package redis;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

public class JedisClusterClient {
    private static Logger LOGGER = LogManager.getLogger(JedisClusterClient.class);
    private JedisCluster jedisConn;
    private Config config = ConfigFactory.load();

    public JedisClusterClient(){
        init();
    }

    private void init(){
        int MAX_ACTIVE = config.getInt("MAX_ACTIVE");
        int MAX_IDLE = config.getInt("MAX_IDLE");
        int MAX_WAIT = config.getInt("MAX_WAIT");
        int TIME_OUT = config.getInt("TIME_OUT");
        int MAX_REDIRECTION = config.getInt("MAX_REDIRECTION");
        int MAX_ATTEMPTS = config.getInt("MAX_ATTEMPTS");
        String PASSWORD = config.getString("PASSWORD");
        String[] hostsArray = config.getString("REDIS_CLUSTER_HOSTS").split(",");
        String[] portsArray = config.getString("REDIS_CLUSTER_PORTS").split(",");

        HashSet<HostAndPort> jedisClusterHostAndPort=new HashSet<HostAndPort>();
        for(int i =0;i<hostsArray.length;i++){
            jedisClusterHostAndPort.add(new HostAndPort(hostsArray[i],Integer.parseInt(portsArray[i])));
        }

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(MAX_ACTIVE);
        poolConfig.setMaxIdle(MAX_IDLE);
        poolConfig.setMaxWaitMillis(MAX_WAIT);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        jedisConn = new JedisCluster(jedisClusterHostAndPort,TIME_OUT,MAX_REDIRECTION,MAX_ATTEMPTS,PASSWORD,poolConfig);
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
