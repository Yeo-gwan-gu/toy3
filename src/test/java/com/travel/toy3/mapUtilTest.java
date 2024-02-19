package com.travel.toy3;

import com.travel.toy3.domain.util.Geocoding;
import com.travel.toy3.domain.util.GoogleMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class mapUtilTest {
    @Test
    void mapTest() throws IOException {
        GoogleMap a = Geocoding.getGeoInfo("삼성동");
        System.out.println("========================================================================");
        System.out.println(a);
        System.out.println("========================================================================");
    }
}
