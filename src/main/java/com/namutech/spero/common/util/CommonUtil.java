package com.namutech.spero.common.util;

public class CommonUtil {
        public static Double convertByteToGigaByte(Long byteValue) {
        return (double) byteValue / (1024 * 1024 * 1024);
    }

    public static Double convertGigaByteToTeraByte(Double gigaByteValue) {
        return gigaByteValue / 1024;
    }

    public static Double convertByteToTeraByte(Long byteValue) {
        return convertByteToTeraByte(byteValue, 100);
    }

    // overload method
    public static Double convertByteToTeraByte(Long byteValue, int round) {
            Double gigaByteValue = convertByteToGigaByte(byteValue);

        return (double) Math.round(gigaByteValue / 1024 * round) / round;
    }

    public static String getVendorFromCloudId(String cloudId) {
        return cloudId.split("-")[1];
    }
}
