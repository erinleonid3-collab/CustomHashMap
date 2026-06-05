package org.example.springexample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomHashMapTest {

    @Test
    void get() {
        CustomHashMap<String, String> testMap = new CustomHashMap<>();
        testMap.put("abcd","sdfdsdds");
        testMap.put("123","sdfdsdds");
        testMap.put("asdsadas","sdfdsdds");
    }
}