package Controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SafeScanTest {

   static String filename = "src/main/resources/test1.txt";
   static FileWriter fileWriter;

    @BeforeAll
    static void setUp() throws IOException {
        fileWriter = new FileWriter(filename);
        InputStream inputStream = new FileInputStream(filename);
        System.setIn(inputStream);
        SafeScan.getInstance().setScannerOut(inputStream);
    }

    @Test
    void getInstance() {
        SafeScan instance = SafeScan.getInstance();
        assertNotNull(instance);
        assertEquals(instance,SafeScan.getInstance());
    }

    @Test
    void scanIntWithoutNextLine() throws IOException {
        String input = "10";
        fileWriter.write(input);
        fileWriter.flush();
        int actual = SafeScan.getInstance().scanIntWithoutNextLine();
        assertEquals(10,actual);
    }

    @Test
    void safeScanInt() throws IOException {
        String input = "10\n";
        fileWriter.write(input);
        fileWriter.flush();
        int actual = SafeScan.getInstance().safeScanInt();
        assertEquals(10,actual);
    }

    @Test
    void safeScanIntInTheRange() throws IOException {
        String input = "11\n";
        fileWriter.write(input);
        fileWriter.flush();
        assertThrows(IllegalArgumentException.class,
                ()->SafeScan.getInstance().safeScanIntInTheRange(5,10));
    }

    @Test
    void nextLine() throws IOException {
        String string = "test\n";
        fileWriter.write(string);
        fileWriter.flush();
        assertEquals("test",SafeScan.getInstance().nextLine());
    }
}