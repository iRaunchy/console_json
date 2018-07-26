package com.techtask.console_json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsoleJsonApplicationTests {

    @Autowired
    CommandLineJSONProcessor commandLineJSONProcessor;

    private static final String filePath = "./src/test/resources/test_file/test_json.json";

    @Test
    public void verifyJSONProcessorTest() {
        String expectedResult = "*** Found 3 objects with field childCount equals 2 **** \n" +
                "*** Found 5 childCount ***";
        String actualResult = commandLineJSONProcessor.processJSONWithParameters(filePath, "childCount", "2");
        Assert.assertEquals("The result of processing JSON is incorrect",expectedResult, actualResult);
    }

}
