package com.aatrox.quartzserver.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
    private static Logger logger = LoggerFactory.getLogger(StreamGobbler.class);
    private InputStream inputStream;
    private String streamType;
    private StringBuilder buf;
    private volatile boolean isStopped = false;

    /**
     * @param inputStream the InputStream to be consumed
     * @param streamType  the stream type (should be OUTPUT or ERROR)
     */
    public StreamGobbler(final InputStream inputStream, final String streamType) {
        this.inputStream = inputStream;
        this.streamType = streamType;
        this.buf = new StringBuilder();
        this.isStopped = false;
    }

    /**
     * Consumes the output from the input stream and displays the lines consumed
     * if configured to do so.
     */
    @Override
    public void run() {
        try {
            String osName = System.getProperty("os.name");
            InputStreamReader inputStreamReader;
            if (osName.toUpperCase().contains("LINUX")) {
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            } else {
                inputStreamReader = new InputStreamReader(inputStream, "GBK");
            }
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                this.buf.append(line + "\n");
            }
        } catch (IOException ex) {
            logger.trace("Failed to successfully consume and display the input stream of type " + streamType + ".", ex);
        } finally {
            this.isStopped = true;
            synchronized (this) {
                notify();
            }
        }
    }

    public String getContent() {
        if (!this.isStopped) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ignore) {
                    logger.error("", ignore);
                }
            }
        }
        return this.buf.toString();
    }
}