package com.mowitnow.util;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

class FileReaderTest {

    FileReader fileReader = new FileReader();

    @Test
    void testLoadFile() {
        String filename = "C:\\Users\\Olivia\\Documents\\MowItNow\\src\\main\\resources\\mower_1.txt";
        List<String> lines = fileReader.loadFile(filename);
        assertEquals("[5 5, 1 2 N, GAGAGAGAA]", lines.toString());
    }
}