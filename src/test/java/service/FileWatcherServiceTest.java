package service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import java.io.IOException;

public class FileWatcherServiceTest {
    private final FileWatcherService fileWatcherService = new FileWatcherService();

    public FileWatcherServiceTest() throws IOException {
    }

    @Test
    public void test_Validate_Input_FileName_Should_Equal_True() {
        boolean result = this.fileWatcherService.validateFileName("orders24.xml");
        boolean expected = true;

        assertEquals(expected, result);
    }


    @Test
    public void test_Validate_Input_FileName_Should_Equal_False() {
        boolean result = this.fileWatcherService.validateFileName("orders.xml");
        boolean expected = false;

        assertEquals(expected, result);
    }

    @Test
    public void test_Validate_DirectoryFilePath_Should_Equal_NotNull() {
        String directoryFilePath = fileWatcherService.getDirectoryFilePath();
        assertNotNull(directoryFilePath);
    }
}
