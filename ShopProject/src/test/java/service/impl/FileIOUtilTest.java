package service.impl;

import org.informatics.utils.fileio.FileIOUtil;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.informatics.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class FileIOUtilTest {

    private static final String TEST_DIR = "test_invoices";
    private static final String TEST_FILE = "Invoice";
    private static final long TEST_SHOP_ID = 99L;
    private static final String TEST_CONTENT = "Hello, this is a test.";

    private TestFileIOUtil testUtil;

    @BeforeEach
    void setUp() {
        testUtil = new TestFileIOUtil();
    }

    @Test
    void writeAndRead_shouldMatchOriginalContent() throws IOException {
        testUtil.write(TEST_DIR, TEST_FILE + INVOICE_FILE_NAME_DELIMITER + TEST_SHOP_ID, TEST_CONTENT);

        String readContent = testUtil.read(TEST_DIR, TEST_FILE, TEST_SHOP_ID).trim();

        assertEquals(TEST_CONTENT, readContent);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_DIR + PATH_DELIMITER + TEST_FILE + INVOICE_FILE_NAME_DELIMITER + TEST_SHOP_ID));
        Files.deleteIfExists(Paths.get(TEST_DIR));
    }

    private static class TestFileIOUtil extends FileIOUtil {
        @Override
        public String read(String directoryName, String fileName, long shopId) throws IOException {
            return super.read(directoryName, fileName, shopId);
        }

        @Override
        public void write(String directoryName, String fileName, String objectToSave) throws IOException {
            super.write(directoryName, fileName, objectToSave);
        }
    }
}