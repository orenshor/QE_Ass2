package system;

import org.junit.Test;

import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;

import static java.util.Arrays.copyOf;
import static org.junit.Assert.*;

public class FileSystemTest {


    //constructor:
    @Test
    public void fsConstructorShouldCreateSpaceObject() {
        FileSystem fs = new FileSystem(10);
        assertNotNull(fs.fileStorage);
    }

    //DirExist:
    @Test
    public void DirExistShouldReturnTheDirIfExist() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1"};
        try {
            fs.dir(firstPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        assertNotNull(fs.DirExists(firstPath));
    }

    @Test
    public void DirExistShouldReturnTreeObjectIfExist() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1"};
        try {
            fs.dir(firstPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        assertEquals("system.Tree", fs.DirExists(firstPath).getClass().getName());
    }

    @Test
    public void DirExistShouldReturnNullIfDirNotExist() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1"};
        assertNull(fs.DirExists(firstPath));
    }

    @Test
    public void DirExistShouldReturnNullIfPathGivenNotADir() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1", "file1"};
        try {
            fs.file(firstPath, 5);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        assertNull(fs.DirExists(firstPath));
    }

    //FileExist:
    @Test
    public void FileExistShouldReturnTheFileIfExist() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1", "file1"};
        try {
            fs.file(firstPath, 5);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        assertNotNull(fs.FileExists(firstPath));
    }

    @Test
    public void FileExistShouldReturnLeafObjectIfExist() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1", "file1"};
        try {
            fs.file(firstPath, 5);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        assertEquals("system.Leaf", fs.FileExists(firstPath).getClass().getName());
    }

    @Test
    public void FileExistShouldReturnNullIfFileNotExist() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1", "file1"};
        assertNull(fs.FileExists(firstPath));
    }

    @Test
    public void FileExistShouldReturnNullIfPathGivenNotAFile() {
        FileSystem fs = new FileSystem(10);
        String[] firstPath = {"root", "dir1"};
        try {
            fs.dir(firstPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        assertNull(fs.FileExists(firstPath));
    }

    //rmdir:
    @Test
    public void rmdirShouldRemoveIfTheGivenDirIsEmpty() {
        FileSystem fs = new FileSystem(10);
        String[] path = {"root", "dir1"};
        try {
            fs.dir(path);
            assertNotNull(fs.DirExists(path));
            fs.rmdir(path);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (DirectoryNotEmptyException e) {
            fail("Directory is Empty, Should Not Throw Exception!");
        }

        assertNull(fs.DirExists(path));
    }

    @Test(expected = DirectoryNotEmptyException.class)
    public void rmdirShouldThrowIfDirAreNotEmpty() throws DirectoryNotEmptyException {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] filePath = {"root", "dir1", "file1"};
        try {
            fs.dir(dirPath);
            fs.file(filePath, 5);
            fs.rmdir(dirPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }

    @Test
    public void rmdirShouldDoNothingToTheSystemTreeIfDirDoesntExist() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1", "dir2"};
        String[] filePath = {"root", "dir1", "dir2", "file1"};
        String[] notExistDirPath = {"root", "dir1", "dir3"};
        try {
            fs.dir(dirPath);
            fs.file(filePath, 5);
            fs.rmdir(notExistDirPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (DirectoryNotEmptyException e) {
            fail("Directory is Empty, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        String[] expected = {"file1"};
        assertArrayEquals(expected, fs.lsdir(dirPath));
    }

    @Test
    public void rmdirShouldNotRemoveIfGivenAPathToFile() {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "dir1", "dir2", "file1"};
        String[] dirPath = {"root", "dir1", "dir2"};
        try {
            fs.file(filePath, 5);
            fs.rmdir(filePath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (DirectoryNotEmptyException e) {
            fail("Directory is Empty, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        String[] expected = {"file1"};
        assertArrayEquals(expected, fs.lsdir(dirPath));
    }

    //rmfile:
    @Test
    public void rmfileShouldRemoveIfTheGivenFileExist() {
        FileSystem fs = new FileSystem(10);
        String[] path = {"root", "dir1", "file1"};
        try {
            fs.file(path, 5);
            assertNotNull(fs.FileExists(path));
            fs.rmfile(path);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        assertNull(fs.FileExists(path));
    }

    @Test
    public void rmfileShouldDoNothingToSystemTreeIfTheGivenFileDoesntExist() {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "dir1", "file1"};
        String[] dirPath = {"root", "dir1"};
        String[] notExistPath = {"root", "dir1", "file2"};
        try {
            fs.file(filePath, 5);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        fs.rmfile(notExistPath);
        String[] expected = {"file1"};
        assertArrayEquals(expected, fs.lsdir(dirPath));
    }

    @Test
    public void rmfileShouldDoNothingToSystemTreeWhenGivenAPathToDir() {
        FileSystem fs = new FileSystem(10);
        String[] subDirPath = {"root", "dir1", "dir2"};
        String[] dirPath = {"root", "dir1"};
        String[] notExistPath = {"root", "dir1", "file2"};
        try {
            fs.dir(subDirPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        fs.rmfile(subDirPath);
        String[] expected = {"dir2"};
        assertArrayEquals(expected, fs.lsdir(dirPath));
    }

    //lsdir:
    @Test
    public void lsdirShouldReturnNullIfPathDoesntExist() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        assertNull(fs.lsdir(dirPath));
    }

    @Test
    public void lsdirShouldReturnListOfDirContent() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] subDirPath = {"root", "dir1", "dir2"};
        String[] subFilePath = {"root", "dir1", "file1"};
        try {
            fs.dir(subDirPath);
            fs.file(subFilePath, 5);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        String[] expected = {"file1", "dir2"};
        Arrays.sort(expected);
        assertArrayEquals(expected, fs.lsdir(dirPath));
    }

    @Test
    public void lsdirShouldReturnListInTheSizeOfTheChildrenAmount() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        try {
            fs.dir(dirPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        int expected = 0;
        assertEquals(expected, fs.lsdir(dirPath).length);
    }

    //Dir:
    @Test(expected = BadFileNameException.class)
    public void dirShouldThrowIfPathDoesntStartWithRoot() throws BadFileNameException {
        FileSystem fs = new FileSystem(10);
        String[] wrongPath = {"toor", "dir1"};
        fs.dir(wrongPath);
    }

    @Test(expected = BadFileNameException.class)
    public void dirShouldThrowWhenCreateInExistFilePath() throws BadFileNameException {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "file1"};
        try {
            fs.file(filePath, 5);
            fs.dir(filePath);
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }

    @Test
    public void dirShouldDoNothingIfTheDirExist() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] rootPath = {"root"};
        String[] expected = null;
        String[] afterSecondDir = null;
        try {
            fs.dir(dirPath);
            expected = fs.lsdir(dirPath);
            fs.dir(dirPath);
            afterSecondDir = fs.lsdir(dirPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        assertArrayEquals(expected, afterSecondDir);
    }

    @Test
    public void dirShouldAddFolderToTheSystemTree() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] rootPath = {"root"};
        try {
            fs.dir(dirPath);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
        String[] expected = {"dir1"};
        assertArrayEquals(expected, fs.lsdir(rootPath));
    }

    //disk:
    @Test
    public void diskShouldSaveFilesPathInTheDiskArray() {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "dir1", "file1"};
        try {
            fs.file(filePath, 5);
            String[][] actualDisk = fs.disk();
            for (int i = 0; i < 5; i++) {
                assertArrayEquals(filePath, actualDisk[i]);
            }
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }

    @Test
    public void diskShouldSaveInTheArrayOnlyInTheFileSizeAneTheRestSetNull() {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "dir1", "file1"};
        try {
            fs.file(filePath, 5);
            String[][] actualDisk = fs.disk();
            for (int i = 5; i < 10; i++) {
                assertNull(actualDisk[i]);
            }
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }

    @Test
    public void diskShouldInitTheArrayWithNulls() {
        FileSystem fs = new FileSystem(10);
        String[][] actualDisk = fs.disk();
        for (int i = 0; i < 10; i++) {
            assertNull(actualDisk[i]);
        }
    }

    @Test
    public void diskShouldReturnAnArrayInTheGivenSize() {
        FileSystem fs = new FileSystem(10);
        String[][] actualDisk = fs.disk();
        assertEquals(10, actualDisk.length);
    }

    //file:
    @Test(expected = BadFileNameException.class)
    public void fileShouldThrowIfPathDoesntStartWithRoot() throws BadFileNameException, OutOfSpaceException {
        FileSystem fs = new FileSystem(10);
        String[] wrongPath = {"toor", "dir1", "file1"};
        fs.file(wrongPath, 5);
    }

    @Test(expected = OutOfSpaceException.class)
    public void fileShouldThrowIfThereIsNotEnoughFreeSpaceAndFileNotExist() throws OutOfSpaceException {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "dir1", "file1"};
        try {
            fs.file(filePath, 11);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
    }

    @Test
    public void fileShouldReplaceOldFileWithNewWhenThereIsFreeSpaceAfterReplace() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] filePath = {"root", "dir1", "file1"};
        try {
            fs.file(filePath, 5);
            String[] expectedLsdir = fs.lsdir(dirPath);
            fs.file(filePath, 6);
            String[] actualLsdir = fs.lsdir(dirPath);
            assertArrayEquals(expectedLsdir, actualLsdir);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }

    @Test(expected = OutOfSpaceException.class)
    public void fileShouldThrowIfRemoveOldFileIsNotEnoughSpaceToTheNew() throws OutOfSpaceException {
        FileSystem fs = new FileSystem(10);
        String[] filePath = {"root", "file1"};
        String[] otherFilePath = {"root", "file2"};
        try {
            fs.file(filePath, 5);
            fs.file(otherFilePath, 5);
            fs.file(filePath, 6);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        }
    }

    @Test
    public void fileShouldAddFileToDir(){
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] filePath = {"root", "dir1", "file1"};
        String[] expectedLsdir = {"file1"};
        try {
            fs.file(filePath, 5);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
        assertArrayEquals(expectedLsdir, fs.lsdir(dirPath));
    }

    @Test(expected = BadFileNameException.class)
    public void fileShouldThrowIfAExistDirGiven() throws BadFileNameException {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        fs.dir(dirPath);
        try{
            fs.file(dirPath, 5);
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }
    @Test
    public void fileShouldReplaceOldFileWithNewWhenThereIsFreeSpace() {
        FileSystem fs = new FileSystem(10);
        String[] dirPath = {"root", "dir1"};
        String[] filePath = {"root", "dir1", "file1"};
        try {
            fs.file(filePath, 5);
            String[] expectedLsdir = fs.lsdir(dirPath);
            fs.file(filePath, 4);
            String[] actualLsdir = fs.lsdir(dirPath);
            assertArrayEquals(expectedLsdir, actualLsdir);
        } catch (BadFileNameException e) {
            fail("Valid Path, Should Not Throw Exception!");
        } catch (OutOfSpaceException e) {
            fail("Valid Allocation, Should Not Throw Exception!");
        }
    }

}