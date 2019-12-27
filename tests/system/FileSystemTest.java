package system;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileSystemTest {

    @Test
    public void fsConstructorShouldCreateSpaceObject(){
        FileSystem fs = new FileSystem(10);
        assertNotNull(fs.fileStorage);
    }

    @Test(expected = BadFileNameException.class)
    public void createADirInNonExistPathShouldThrow() throws BadFileNameException {
        FileSystem fs = new FileSystem(10);
        String[] wrongPath = {"toor"};
        fs.dir(wrongPath);
    }


}