package org.usfirst.frc3620.logger;

import org.usfirst.frc3620.logger.FastLoggerCollections;

import edu.wpi.first.wpilibj.CANTalon;

public class TalonSRXPositionLogger {
    CANTalon talon;
    IFastLogger iFastLogger;

    public TalonSRXPositionLogger(CANTalon _talon) {
        talon = _talon;
        iFastLogger = new FastLoggerCollections();
        iFastLogger.setInterval(1);
        iFastLogger.setMaxLength(5.0);
        iFastLogger.setFilename("talonPosition");
        iFastLogger.addMetadata("P", talon.getP());
        iFastLogger.addMetadata("I", talon.getI());
        iFastLogger.addMetadata("D", talon.getD());
        iFastLogger.addMetadata("F", talon.getF());
        
        iFastLogger.setColumnNames(new String[] {
               "error",
               "setpoint",
               "encPosition",   
               "outputVoltage",
        });

        iFastLogger.setDataProvider(new IFastLoggerDataProvider() {
            
            @Override
            public double[] fetchData() {
                return new double[] {
                   talon.getError(),
                   talon.getSetpoint(),
                   talon.getEncPosition(),    
                   talon.getOutputVoltage()
                };
            }
        });
        iFastLogger.start();
    }
}
