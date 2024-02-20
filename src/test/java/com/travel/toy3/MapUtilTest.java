package com.travel.toy3;

import com.travel.toy3.domain.util.Geocoding;
import com.travel.toy3.domain.util.GoogleMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MapUtilTest {
    @Test
    void mapTest() throws IOException {
        GoogleMap a = Geocoding.getGeoInfo("서울특별시 마포구 와우산로26길 9");
        System.out.println("========================================================================");
        System.out.println(a);
        System.out.println("========================================================================");
    }
}
