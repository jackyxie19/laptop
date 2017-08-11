package redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class TestRedisson {
    public static void main(String[] args){
        Config config = new Config();
        RedissonClient client = Redisson.create(config);
        RMap<Object, Object> haha = client.getMap("haha");
        Object put = haha.put("a", "b");
    }
}
