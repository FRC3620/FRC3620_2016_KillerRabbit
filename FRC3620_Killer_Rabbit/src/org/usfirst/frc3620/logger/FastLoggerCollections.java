package org.usfirst.frc3620.logger;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FastLoggerCollections extends FastLoggerBase {
    List<double[]> data = new ArrayList<>();
    List<Double> timestamps = new ArrayList<>();

    @Override
    void logData (double timestamp, double[] d) {
        timestamps.add(timestamp);
        data.add(d);
    }

    @Override
    void writeData(PrintWriter w) {
        for (int i = 0; i < data.size(); i++) {
            w.print(timestamps.get(i));
            double[] row = data.get(i);
            for (int c = 0; c < row.length; c++) {
                w.print(",");
                w.print(row[c]);
            }
            w.println();
        }
    }

}
