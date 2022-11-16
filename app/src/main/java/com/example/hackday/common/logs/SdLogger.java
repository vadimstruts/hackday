package com.example.hackday.common.logs;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SdLogger  {

    final static String DefaultFileName = "hackday.log";
    final static String SdCardFolderName = "hackday";

    private static SdLogger instance;
    public static synchronized SdLogger getInstance(){
        if(null == instance) {
            instance = new SdLogger();
        }

        return instance;
    }

    ExecutorService threadPool = null;

    File outFile = null;
    final static Object setupOutFileLock = new Object();

    public synchronized void AppendLine(String logLine){
        if(null == threadPool)
            threadPool = Executors.newSingleThreadExecutor();

        queueLineAppending(logLine);
    }

    public synchronized void ShutDown(){
        if(null != threadPool) {
            shutdownAndAwaitTermination(threadPool);
            threadPool = null;
        }
    }

    private void queueLineAppending(final String logLine){
        threadPool.submit(() -> WriteOneLine(logLine));
    }

    private void WriteOneLine(String logLine){

        try {
            if(null == outFile)
                setupOutFile();

            final FileOutputStream f = new FileOutputStream(outFile, true);
            final PrintWriter pw = new PrintWriter(f);
            pw.println(logLine);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
                Log.e("SdLogger", String.format("Could not find file: %s Error: %s", outFile.getAbsolutePath(), e));
        } catch (IOException e) {
            Log.e("SdLogger", String.format("Could not write the line to file: %s Error: %s", outFile.getAbsolutePath(), e));
        }
    }

    private void setupOutFile() throws IOException {
        synchronized (setupOutFileLock) {
            outFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), SdCardFolderName);

            if(!outFile.exists() && !outFile.mkdirs())
                throw new IOException(String.format("Could not create folder on the SD card. %s",outFile));

            outFile = new File(outFile, DefaultFileName);

            if(!outFile.exists()){
                if(!outFile.createNewFile())
                    throw new IOException(String.format("Could not create file: %s", outFile.getAbsolutePath()));
            }
        }
    }

    private static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(10, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
