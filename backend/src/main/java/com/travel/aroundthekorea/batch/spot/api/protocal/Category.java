package com.travel.aroundthekorea.batch.spot.api.protocal;

import java.util.Arrays;

public enum Category {
    TouristAttraction(12),
    CulturalFacilities(14),
    FestivalsPerformancesEvents(15),
    TravelCourse(25),
    LeisureSports(28),
    Lodging(32),
    Shopping(38),
    Food(39);
    private final int code;

    Category(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Category find(int categoryCode) {
        return Arrays.stream(Category.values()).filter(c -> c.getCode() == categoryCode)
                .findAny()
                .orElseGet(() -> null);
    }
}
