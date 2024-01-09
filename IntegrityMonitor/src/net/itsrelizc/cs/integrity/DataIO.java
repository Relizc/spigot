package net.itsrelizc.cs.integrity;

import java.io.File;
import java.io.IOException;

public class DataIO {
	
	public static void checkDataExistsThenCreate() {
		File f = new File("IntegrityMonitor.data");
		if (!f.exists()) {
			System.out.println("[IntegrityMonitor] Data file not found! Initializing new data file...");
			
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
}
