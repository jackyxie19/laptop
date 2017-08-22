import org.junit.Test;
import redis.JedisClusterClient;
import redis.clients.jedis.JedisCluster;

public class TestJedisClusterClient {
    @Test
    public void test(){
        JedisClusterClient jedisCluster = JedisClusterClient.getJedisCluster();
        JedisCluster conn = jedisCluster.getJedisConn();
        conn.set("hahaha","jacky");
        String hahaha = conn.get("hahaha");
        System.out.println(hahaha);
    }
}
