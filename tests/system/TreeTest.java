package system;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void treeConstructorShouldSetTheTreeName(){
        assertEquals("t", new Tree("t").name);

    }

    @Test
    public void getChildByNameReturnTreeIfNotExist() {
        Tree t = new Tree("t");
        assertNotNull(t.GetChildByName("t2"));
    }

    @Test
    public void getChildByNameReturnTreeIfExist() {
        Tree t = new Tree("t");
        t.GetChildByName("t2");
        assertNotNull(t.GetChildByName("t2"));
    }

    @Test
    public void depthOfChildSouldByPlusOne() {
        Tree t = new Tree("t1");
        Tree t2 = t.GetChildByName("t2");
        int fatherDepth = t.depth;
        int subDepth = t2.depth;
        assertEquals(subDepth, fatherDepth + 1);
    }

    @Test
    public void rootParentIsNull() {
        Tree t = new Tree("t");
        assertNull(t.parent);
    }

    @Test
    public void rootDepthIsNull() {
        Tree t = new Tree("t");
        int depth = t.depth;
        assertEquals(0, depth);
    }
}
