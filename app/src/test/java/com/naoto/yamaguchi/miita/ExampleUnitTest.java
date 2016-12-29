package com.naoto.yamaguchi.miita;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        InputStream in = getClass().getClassLoader().getResourceAsStream("all_item_response.json");

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder strbuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = br.readLine()) != null) {
            strbuilder.append(inputStr);
        }
        br.close();

        System.out.print("JSON:" + strbuilder.toString());
    }
}