package system;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void rootNodeShouldBeEmptyArray(){
        Tree t = new Tree("t");
        assertArrayEquals(new String[0], t.getPath());
    }

    @Test
    public void pathOfSubTreeShouldGetUntilRootNodeNotInclude(){
        Tree t1 = new Tree("t1");
        Tree t2 = t1.GetChildByName("t2");
        Tree t3 = t2.GetChildByName("t3");
        String[] expectedPath = {t2.name, t3.name};
        assertArrayEquals(expectedPath, t3.getPath());
    }

}