package org.usfirst.frc3620.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DataLogger {

	File parentDirectory;
	static final long DEFAULT_MINIMUM_INTERVAL = 100;
	long minimumInterval = DEFAULT_MINIMUM_INTERVAL;
	
	/* use any one of these. If you leave the directory of the minimum interval off, reasonable defaults are provided */
	
	public DataLogger () {
		this(LoggingMaster.getLoggingDirectory(), DEFAULT_MINIMUM_INTERVAL);
	}

	public DataLogger (long minimumInterval) {
		this(LoggingMaster.getLoggingDirectory(), minimumInterval);
	}

	public DataLogger (File directory) {
		this(directory, DEFAULT_MINIMUM_INTERVAL);
	}

	public DataLogger(File directory, long minimumInterval) {
		parentDirectory = directory;
		setMinimumInterval(minimumInterval);
	}

	List<String> dataNames = new ArrayList<>();
	List<String> dataValues = new ArrayList<>();

	public void addDataItem(String name, double value) {
		String valueString = String.valueOf(value);
		dataNames.add(name);
		dataValues.add(valueString);
	}

	public void addDataItem(String name, int value) {
		String valueString = String.valueOf(value);
		dataNames.add(name);
		dataValues.add(valueString);
	}

	public void addDataItem(String name, boolean value) {
		String valueString = String.valueOf(value);
		dataNames.add(name);
		dataValues.add(valueString);
	}

	public void addDataItem(String name, String value) {
		String valueString = String.valueOf(value);
		dataNames.add(name);
		dataValues.add(valueString);
	}

	PrintStream ps;
	long startTime;
	long timeUpdated;
	long timeSinceLog;

	public boolean shouldLogData() {
		long now = System.currentTimeMillis();
		if ((ps == null) || (now - timeSinceLog) > minimumInterval) {
			return true;
		} else {
			return false;
		}
	}

	public void saveDataItems() {
		if (shouldLogData()) {
			try {
				if (ps == null) {
					synchronized (this) {
						if (ps == null) {
							String timestampString = LoggingMaster
									.getTimestampString();
							if (timestampString != null) {
								File logFile = new File(parentDirectory,
										timestampString + ".csv");
								ps = new PrintStream(
										new FileOutputStream(logFile));
								ps.print("time,timeSinceStart");
								writelist(ps, dataNames);
								startTime = System.currentTimeMillis();
							}
						}
					}
				}
				if (ps != null) {
					timeUpdated = (System.currentTimeMillis() - startTime);
					ps.print(getDate());
					ps.print(',');
					ps.print(timeUpdated);
					writelist(ps, dataValues);
					ps.flush();
					timeSinceLog = System.currentTimeMillis();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		dataValues.clear();
		dataNames.clear();

	}

	private void writelist(PrintStream stream, List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			stream.print(',');
			stream.print(list.get(i));
		}
		stream.println();
	}

	public String getDate() {
		Date curDate = new Date();
		String DateToStr = format.format(curDate);
		return DateToStr;
	}

	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SS");

	public void setMinimumInterval(long minimumInterval) {
		this.minimumInterval = minimumInterval;
	}
}
