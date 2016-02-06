package org.usfirst.frc3620.logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

abstract public class FastLoggerBase implements IFastLogger {
    IFastLoggerDataProvider iFastLoggerDataProvider;
    
    String[] names;
    Map<String, Double> metadata = new TreeMap<>();
    double maxLengthInSeconds = 5;
    int interval = 1;
    String filename;

    double t0 = getTimeInSeconds();
    
    @Override
    public void setMaxWidth(int i) {
    }

    @Override
    public void setMaxLength(double seconds) {
        maxLengthInSeconds = seconds;
    }

    @Override
    public void setInterval(int milliseconds) {
        interval = milliseconds;
    }

    @Override
    public void setDataProvider(
            IFastLoggerDataProvider _iFastLoggerDataProvider) {
        iFastLoggerDataProvider = _iFastLoggerDataProvider;
    }
    
    @Override
    public void setFilename (String _filename) {
        filename = _filename;
    }

    @Override
    public void addMetadata(String s, double d) {
        metadata.put(s, d);
    }
    
    @Override
    public void setColumnNames (String[] s) {
        names = s;
    }
    
    File outputFile;
    Timer timer;
    
    @Override
    public String start() {
    	
        SimpleDateFormat formatName = new SimpleDateFormat(
                "yyyyMMdd-HHmmss");
        String _timestampString = formatName.format(new Date());
        String fullFilename = filename + _timestampString + ".csv";
        String logMessage = String.format("filename for fastLog is %s\n", fullFilename);
        System.out.print (logMessage);
    	//Exception e = new Exception("traceback");
    	//e.printStackTrace();
        
        outputFile = new File (LoggingMaster.getLoggingDirectory(), fullFilename);
        
        t0 = getTimeInSeconds();
        timer = new Timer();
        timer.schedule(new FastLoggerTimerTask(), 0, interval);
        
        return _timestampString;
    }
    
    class FastLoggerTimerTask extends TimerTask {
        @Override
        public void run() {
            double t = (getTimeInSeconds() - t0);
            if (t > maxLengthInSeconds) done();

            logData (t, iFastLoggerDataProvider.fetchData());
        }
    }

    @Override
    public void done() {
        timer.cancel();
        System.out.println("fastLogger done");
        try {
            PrintWriter w = new PrintWriter(new FileWriter(outputFile));
            
            w.print("timestamp");
            for (String n : names) {
                w.print(",");
                w.print(n);
            }
            w.println();
            
            for (String n: metadata.keySet()) {
                w.print("# ");
                w.print(n);
                w.print(" = " );
                w.print(metadata.get(n));
                w.println();
            }
            
            writeData(w);
            
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    abstract void logData (double timestamp, double[] d);
    
    abstract void writeData(PrintWriter w);
    
    double getTimeInSeconds() {
        return edu.wpi.first.wpilibj.Timer.getFPGATimestamp();
    }
}
