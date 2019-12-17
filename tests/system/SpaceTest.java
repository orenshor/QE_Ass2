package system;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    @Test(expected = OutOfSpaceException.class)
    public void allocBiggerSpaceFromLeafSizeShouldThrowExp() throws OutOfSpaceException {
        Space s = new Space(5);
        Leaf l = new Leaf("l", 10);
        s.Alloc(10, l);
    }

    @Test
    public void allocBigSpaceForSmallLeafShouldBeSuccessful() throws OutOfSpaceException {
        Space s = new Space(10);
        Leaf l = new Leaf("l", 5);
        s.Alloc(5, l);
    }

    @Test
    public void allocated5blocksAndCountFreeSpaceShouldReturn5() {
        Space s = new Space(10);
        assertEquals(10, s.countFreeSpace());
    }

    @Test
    public void allocated5BlocksOutOf10AndCountFreeSpaceShouldReturn5() throws OutOfSpaceException {
        Space s = new Space(10);
        Leaf l = new Leaf("l", 5);
        s.Alloc(5, l);
        assertEquals(5, s.countFreeSpace());
    }

    @Test
    public void deallocate5blocksAndCountFreeSpaceShouldReturn5() throws OutOfSpaceException {
        Space s = new Space(5);
        Leaf l = new Leaf("l", 5);
        l.parent = new Tree("t");
        s.Alloc(5, l);
        s.Dealloc(l);
        assertEquals(5, s.countFreeSpace());
    }

    @Test
    public void allocateLeafShouldBeShowedInTheSpaceBlocks() throws OutOfSpaceException {
        Space s = new Space(10);
        Leaf[] leafs = new Leaf[]{new Leaf("stub", 10)};
//        for (Leaf l : leafs){
//            s.Alloc(10, leafs[0]);
//        }

        s.Alloc(10, leafs[0]);
//        Leaf leaf1 = new Leaf("leaf1", 5);
//        Leaf leaf2 = new Leaf("leaf2", 10);
//        s.Alloc(5, leaf1);
//        s.Alloc(10, leaf2);
        Assert.assertArrayEquals(leafs, s.getAlloc());
    }



}