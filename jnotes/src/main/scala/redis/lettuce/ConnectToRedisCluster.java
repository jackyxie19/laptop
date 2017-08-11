package redis.lettuce;

import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;
import com.lambdaworks.redis.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import com.lambdaworks.redis.cluster.api.sync.RedisAdvancedClusterCommands;

import java.util.List;

public class ConnectToRedisCluster {
    public static void main(String[] args){
        // Syntax: redis://[password@]host[:port]
        RedisClusterClient redisClient = RedisClusterClient.create("redis://password@localhost:7379");

        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();

        RedisAdvancedClusterCommands<String, String> clusterCommands = connection.sync();
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = connection.async();
        asyncCommands.setAutoFlushCommands(false);
        asyncCommands.set("a","b");
        asyncCommands.flushCommands();
        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }
}
