package org.usfirst.frc3620.logger;

public interface IFastLogger {
  public void setMaxWidth(int i);
  public void setMaxLength(double seconds);
  public void setInterval (int i);
  public void setDataProvider(IFastLoggerDataProvider iFastLoggerDataProvider);
  public void setFilename (String filename);
  public void addMetadata (String s, double d);
  public void setColumnNames (String[] s);
  public String start();
  public void done();
}
