package com.travel.toy3.domain.itinerary.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItineraryType {
    MOVING(1, "이동"),
    ACCOMMODATION(2, "숙박"),
    STAY(3, "체류");

    private final Integer code;
    private final String korean;
}
