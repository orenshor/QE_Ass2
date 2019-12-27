package system;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    @Test(expected = OutOfSpaceException.class)
    public void allocSmallerSpaceFromLeafSizeShouldThrowExp() throws OutOfSpaceException {
            Space s = new Space(5);
            FileSystem.fileStorage = s;
            Leaf l = new Leaf("l", 10);
            s.Alloc(10, l);
    }

    @Test
    public void allocBigSpaceForSmallLeafShouldBeSuccessful() throws OutOfSpaceException {
        Space s = new Space(10);
        FileSystem.fileStorage = s;
        Leaf l = new Leaf("l", 5);
        s.Alloc(5, l);
    }

    @Test
    public void allocated5blocksAndCountFreeSpaceShouldReturn5() {
        Space s = new Space(10);
        FileSystem.fileStorage = s;
        assertEquals(10, s.countFreeSpace());
    }

    @Test
    public void allocated5BlocksOutOf10AndCountFreeSpaceShouldReturn5() throws OutOfSpaceException {
        Space s = new Space(10);
        FileSystem.fileStorage = s;
        Leaf l = new Leaf("l", 5);
//        s.Alloc(5, l);
        assertEquals(5, s.countFreeSpace());
    }

    @Test
    public void deallocate5blocksAndCountFreeSpaceShouldReturn5() throws OutOfSpaceException {
        Space s = new Space(5);
        FileSystem.fileStorage = s;
        Leaf l = new Leaf("l", 5);
        l.parent = new Tree("t");
//        s.Alloc(5, l);
        s.Dealloc(l);
        assertEquals(5, s.countFreeSpace());
    }

    @Test
    public void deallocateLeafShouldDeleteItFromChildren() throws OutOfSpaceException {
        Space s = new Space(5);
        FileSystem.fileStorage = s;
        Leaf l = new Leaf("l", 5);
        l.parent = new Tree("t");
//        s.Alloc(5, l);
        s.Dealloc(l);
        assertFalse(l.parent.children.containsKey(l));
    }

    @Test
    public void allocateLeafShouldBeShowedInTheSpaceBlocks() throws OutOfSpaceException {
        Space s = new Space(10);
        FileSystem.fileStorage = s;
        Leaf[] leafs = new Leaf[]{new Leaf("stub", 10)};
        for (int i = 0; i < 10; i++) {
            assertEquals(leafs[0], s.getAlloc()[i]);
        }

    }


}