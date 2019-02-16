package com.unrealdinnerbone.simplefireworks.lib;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileHelper
{
    public static void writeStringToFile(String info, File file) {
        try {
            FileUtils.writeStringToFile(file, info, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            System.out.println("There was and error while writing string to file " + file.getName());
        }
    }
}
