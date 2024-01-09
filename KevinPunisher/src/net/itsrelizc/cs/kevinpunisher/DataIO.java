package net.itsrelizc.cs.kevinpunisher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataIO {
	
	public static int kevinInfos;
	public static int kevinWarns;
	public static int kevinVios;
	
	public static void init() {
		kevinInfos = 0;
		kevinWarns = 0;
		kevinVios = 0;
	}
	
	public static void createSaveIfNotExist() {
		
		File file = new File("KevinPunisher.data");
		try {
			if (file.createNewFile()) {
				System.out.println("[Punisher] Hello new user! A new save file is generated for you.");
				writeDefaultValues(file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void writeDefaultValues(File file) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer.write("0 0 0\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeCurrentVales() throws IOException {
		File file = new File("KevinPunisher.data");
		if (!file.exists()) {
			System.out.println("[Punisher] Looks like the file is missing! You need to re-run this program.");
			System.exit(0);
		}
		FileWriter writer = new FileWriter(file);
		writer.write(DataIO.kevinInfos + " " + DataIO.kevinWarns + " " + DataIO.kevinVios + "\n");
		
		for (RuleBreaker r : RuleBreaker.all) {
			writer.write(r.date + " " + r.severe + " " + r.name + "\n");
		}
		
		writer.close();
	}
	
	public static void readDefaultValues() {
		File file = new File("KevinPunisher.data");
		if (!file.exists()) {
			System.out.println("[Punisher] Looks like the file is missing! You need to re-run this program.");
			System.exit(0);
		}
		
		
		
		try {
			Scanner input = new Scanner(file);
			
			int info = input.nextInt();
			int warn = input.nextInt();
			int vio = input.nextInt();
			input.nextLine();
			
			while (input.hasNextLine()) {
				long millis = input.nextLong();
				int severe = input.nextInt();
				String name = input.nextLine();
				
				RuleBreaker.addToList(new RuleBreaker(name, severe, millis));
			}
			
			kevinInfos = info;
			kevinWarns = warn;
			kevinVios = vio;
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
