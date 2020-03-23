package com.aatrox.common.utils;

import java.io.*;

/**
 *
 * Java I/O 常用操作的封装
 *
 * @since 1.0
 */
public class IOUtils {

    /**
     * Closes the given stream. The same as calling {@link InputStream#close()}, but errors while
     * closing are silently ignored.
     */
    public static void closeSilently(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        catch (IOException ignore) {
            // Exception is silently ignored
        }
    }


    /**
     * Closes the given stream. The same as calling {@link OutputStream#close()}, but errors while
     * closing are silently ignored.
     */
    public static void closeSilently(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        }
        catch (IOException ignore) {
            // Exception is silently ignored
        }
    }


    public static void closeSilently(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        }
        catch (IOException ignore) {

        }
    }


    public static void closeSilently(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        }
        catch (IOException ignore) {

        }
    }

}
