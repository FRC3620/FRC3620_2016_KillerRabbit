package org.usfirst.frc3620.vision;

import java.util.Date;

public class VisionData {
		double image_height, image_width;
		Double x, y, difRatioX;  //removed "rt" mystery value
		long whenRecieved;

		public double getImageWidth() {
			return image_width;
		}

		public long getWhenRecieved() {
			return whenRecieved;
		}

		public Double getX() {
			return x;
		}
		
		public Double getY() {
			return y;
		}

		public Double getRatioX(){
			return difRatioX;
		}
			
		@Override
		public String toString() {
			return "VisionData [height=" + image_height + ", width=" + image_width + ", x="
					+ x + ", y=" + y + ", RatioX="+ difRatioX + ", count=" + ", whenRecieved=" + whenRecieved + "]";
		}
		// + ", rt=" + rt (removed from above)
			
			
		
		public double getAge(){
			return System.currentTimeMillis() - whenRecieved;
		}
	}