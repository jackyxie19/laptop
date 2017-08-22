package redis.lettuce;


import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.ClusterClientOptions;
import com.lambdaworks.redis.cluster.RedisAdvancedClusterAsyncConnection;
import com.lambdaworks.redis.cluster.RedisAdvancedClusterConnection;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.TimeUnit;

public class ConnectToRedisCluster {
    static Config conf;
    static RedisURI redisUri;
    static RedisClusterClient client;
    static {
        conf = ConfigFactory.load();
        redisUri = RedisURI.Builder.redis("192.168.8.231")
                .withPort(7000)
//                .withPassword("authentication")
//                .withDatabase(2)
                .build();
        client = RedisClusterClient.create(redisUri);
        client.setOptions(new ClusterClientOptions.Builder()
                .refreshClusterView(true)
                .refreshPeriod(1, TimeUnit.MINUTES)
                .build());
    }
    public static RedisAdvancedClusterAsyncConnection getAsynConn(){
        RedisAdvancedClusterAsyncConnection<String, String> conn = client.connectClusterAsync();
        return conn;
    }
    public static RedisAdvancedClusterConnection getSynConn(){
        RedisAdvancedClusterConnection<String, String> conn = client.connectCluster();
        return conn;
    }
    public static void main(String[] args){
        RedisURI redisUri = RedisURI.Builder.redis("192.168.8.231")
                .withPort(7000)
//                .withPassword("authentication")
//                .withDatabase(2)
                .build();
        RedisClusterClient client = RedisClusterClient.create(redisUri);
        client.setOptions(new ClusterClientOptions.Builder()
                .refreshClusterView(true)
                .refreshPeriod(1, TimeUnit.MINUTES)
                .build());
        RedisAdvancedClusterAsyncConnection<String, String> conn = client.connectClusterAsync();
        System.out.println("Connected to Redis");
        conn.set("key","Hello, Redis");
        conn.close();
        client.shutdown();
    }
    public static void test1(){

    }
}
