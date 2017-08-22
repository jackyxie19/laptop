import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

public class JavaTest {

    public Config config;

    @Before
    public void init(){
        config = ConfigFactory.load();
    }

    @Test
    public void testSplit(){
        String str = config.getString("APP_ONE.FILTER_NUM");
        String sep = config.getString("APP_ONE.FILTER_NUM_SEPARATOR");
//        String[] split = str.split("|");
        String[] split = str.split(sep);
        for(int i = 0; i<split.length;i++){
            System.out.println(split[i]);
        }
    }
}
