package org.usfirst.frc3620.logger;

import org.usfirst.frc3620.logger.FastLoggerCollections;

import edu.wpi.first.wpilibj.DriverStation;

public class RandomFastLogger {
    IFastLogger iFastLogger;

    public RandomFastLogger() {
        iFastLogger = new FastLoggerCollections();
        iFastLogger.setInterval(1);
        iFastLogger.setMaxLength(5);
        iFastLogger.setFilename("random");
        iFastLogger.addMetadata("pi", Math.PI);
        iFastLogger.addMetadata("e", Math.E);
        
        iFastLogger.setColumnNames(new String[] {
               "voltage",
               "r1",
               "r2",
               "r3",   
        });

        iFastLogger.setDataProvider(new IFastLoggerDataProvider() {
            DriverStation driverStation = DriverStation.getInstance();
            public double[] fetchData() {
                return new double[] {
                   driverStation.getBatteryVoltage(),
                   Math.random(),
                   Math.random()*2.0,
                   Math.random()*3.0
                };
            }
        });
        
        iFastLogger.start();
    }
}
