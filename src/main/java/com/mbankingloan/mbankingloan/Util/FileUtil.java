package com.mbankingloan.mbankingloan.Util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileUtil {

    public static String generateFileName(String originalFileName) {

        String newName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFileName);

        return String.format("%s.%s", newName, extension);
    }

    public static String extractExtension(String originalFileName) {
        int lastDotIndex = originalFileName.lastIndexOf(".");
        return originalFileName.substring(lastDotIndex + 1);
    }
     public long parseSize(String size) {
        size = size.toUpperCase();
        if (size.endsWith("MB")) {
            return Long.parseLong(size.replace("MB", "")) * 1024 * 1024;
        } else if (size.endsWith("KB")) {
            return Long.parseLong(size.replace("KB", "")) * 1024;
        } else {
            return Long.parseLong(size); // Bytes
        }
    }



}
