package com.ps.resource_inventory.util;

import java.util.concurrent.TimeUnit;

public class Utility {

    public static enum TimeUnitEnum {
        SECONDS('s', TimeUnit.SECONDS.toSeconds(1)),
        MINUTES('m', TimeUnit.MINUTES.toSeconds(1)),
        HOURS('h', TimeUnit.HOURS.toSeconds(1)),
        DAYS('d', TimeUnit.DAYS.toSeconds(1));

        private final char unit;
        private final long secondsMultiplier;

        TimeUnitEnum(char unit, long secondsMultiplier) {
            this.unit = unit;
            this.secondsMultiplier = secondsMultiplier;
        }

        public long toSeconds() {
            return secondsMultiplier;
        }

        public static TimeUnitEnum fromUnit(char unit) {
            for (TimeUnitEnum t : values()) {
                if (t.unit == unit) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Unsupported time unit: " + unit);
        }
    }

    // Parse expiration time and convert to seconds
    public long getExpirationTimeInSeconds(String expirationTime) {
        if (expirationTime == null || expirationTime.isEmpty()) {
            throw new IllegalArgumentException("Expiration time cannot be null or empty");
        }

        long duration = Long.parseLong(expirationTime.substring(0, expirationTime.length() - 1));
        char unit = expirationTime.charAt(expirationTime.length() - 1);

        TimeUnitEnum timeUnitEnum = TimeUnitEnum.fromUnit(unit);
        return duration * timeUnitEnum.toSeconds();
    }
}
