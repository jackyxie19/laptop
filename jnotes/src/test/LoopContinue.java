import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LoopContinue {
    Map<String,String> map = null;
    @Before
    public void init(){
        map = new HashMap<>(100);
    }
    @Test
    public void testMapSize(){
        System.out.println(map.size());
    }
    @Test
    public void testWhile(){

    }
}
