package net.itsrelizc.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;

import net.itsrelizc.networking.Communication;
import net.itsrelizc.networking.CommunicationType;

public class ULogHandler {
	
	public static void initlize(final Properties prop) {
		
		
		
		Handler handler = new Handler() {

			@Override
			public void publish(LogRecord record) {
				if (record.getLevel() == Level.SEVERE) {
					Communication com = new Communication(CommunicationType.ACTIVE_ERROR_BROADCAST);
					byte b = 0x00;
					if (prop.getProperty("rid").equalsIgnoreCase("t")) {
						b = 0x00;
					} else if (prop.getProperty("rid").equalsIgnoreCase("s")) {
						b = 0x01;
					} else if (prop.getProperty("rid").equalsIgnoreCase("m")) {
						b = 0x02;
					} else if (prop.getProperty("rid").equalsIgnoreCase("b")) {
						b = 0x03;
					} else if (prop.getProperty("rid").equalsIgnoreCase("g")) {
						b = 0x04;
					}
					com.writeByte(b);
					com.writeString(prop.getProperty("sid"));
					com.writeString(record.getMessage());
					try {
						com.sendMessage();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Communication com = new Communication(CommunicationType.ACTIVE_LOG);
				byte b = 0x00;
				if (prop.getProperty("rid").equalsIgnoreCase("t")) {
					b = 0x00;
				} else if (prop.getProperty("rid").equalsIgnoreCase("s")) {
					b = 0x01;
				} else if (prop.getProperty("rid").equalsIgnoreCase("m")) {
					b = 0x02;
				} else if (prop.getProperty("rid").equalsIgnoreCase("b")) {
					b = 0x03;
				} else if (prop.getProperty("rid").equalsIgnoreCase("g")) {
					b = 0x04;
				}
				com.writeByte(b);
				com.writeString(prop.getProperty("sid"));
				if (record.getLevel() == Level.WARNING) {
					com.writeByte((byte) 0x01);
				} else if (record.getLevel() == Level.SEVERE) {
					com.writeByte((byte) 0x02);
				} else {
					com.writeByte((byte) 0x00);
				}
				com.writeString(record.getMessage());
				try {
					com.sendMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void flush() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void close() throws SecurityException {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		Bukkit.getLogger().addHandler(handler);
	}
	
}
