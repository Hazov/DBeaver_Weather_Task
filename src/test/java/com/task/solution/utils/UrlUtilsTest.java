package com.task.solution.utils;


import com.task.solution.utils.url.UrlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class UrlUtilsTest {

    @Test
    void ex() {
        String result = UrlUtils.makeRequestWithParam("address.com/v1", "value1=1", "value2=2", "value3=3", "value4=4");
        String expected = "address.com/v1?value1=1&value2=2&value3=3&value4=4";
        Assertions.assertEquals(expected, result);
    }

}
