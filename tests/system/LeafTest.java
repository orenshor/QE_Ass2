package system;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeafTest {

    @Test
    public void leafConstructorShouldSetTheLeafName(){
        FileSystem.fileStorage = new Space(10);
        try {
            assertEquals("l", new Leaf("l", 5).name);
        }catch (OutOfSpaceException e) {
            fail("Not expected to throw: " + e.getMessage());
        }
    }

    @Test
    public void createLeafWithSmallerSizeThenFileStorageShouldNotThrow() {
        FileSystem.fileStorage = new Space(10);
        try {
            new Leaf("l", 5);
        }catch (OutOfSpaceException e) {
            fail("Not expected to throw: " + e.getMessage());
        }
    }

    @Test(expected = OutOfSpaceException.class)
    public void createLeafWithBiggerSizeThenFileStorageShouldThrow() throws OutOfSpaceException {
        FileSystem.fileStorage = new Space(10);
            new Leaf("l", 20);
            fail("Expected OutOfSpaceException");
    }




}