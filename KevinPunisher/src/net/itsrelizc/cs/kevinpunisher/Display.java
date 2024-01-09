package net.itsrelizc.cs.kevinpunisher;

import java.util.Scanner;

import net.itsrelizc.cs.kevinpunisher.actions.AddRule;
import net.itsrelizc.cs.kevinpunisher.actions.Analyze;
import net.itsrelizc.cs.kevinpunisher.actions.ClearLogs;
import net.itsrelizc.cs.kevinpunisher.actions.SaveLogs;

public class Display {
	
	public static void menu() {
		
		System.out.println("=============================================================");
		System.out.println("Informs: " + DataIO.kevinInfos + ", Warnings: " + DataIO.kevinWarns + ", Violations: " + DataIO.kevinVios);
		System.out.println("=============================================================");
	}
	
	public static void listBadActions() {
		System.out.println();
		if (RuleBreaker.all.size() == 0) {
			System.out.println(":D Kevin is being a good boy. He did nothing wrong.");
		} else {
			for (RuleBreaker r : RuleBreaker.all) {
				System.out.println(r);
			}
		}
	}
	
	public static void userOptions() {
		System.out.println();
		System.out.println("=============================================================");
		System.out.println("1: Add rule breaking log    - since Kevin did something wrong again");
		System.out.println("2: Save Logs                - It will auto save every time you add an log too");
		System.out.println("3: Clear Logs               - Clear all the bad things that Kevin did");
		System.out.println("4: Analyze punishments      - Analyze what kevin did and determine a punishment");
		System.out.println("=============================================================");
		
	}
	
	public static void askUserOption() {
		System.out.println();
		int option;
		
		while (true) {
			option = -1;
			
			try {
				Scanner input = new Scanner(System.in);
				
				System.out.print("Enter your option: ");
				option = input.nextInt();
				input.nextLine();
			} catch (Exception e) {
				System.out.println("Invalid value, enter option again.");
				System.out.println();
				continue;
			}
			
			if (option > 6 || option < 1) {
				System.out.println("Invalid value, enter option again.");
				System.out.println();
				continue;
			}
			
			break;
		}
		
		
		
		if (option == 1) {
			AddRule.execute();
		} else if (option == 2) {
			SaveLogs.execute();
		} else if (option == 3) {
			ClearLogs.execute();
		} else if (option == 4) {
			Analyze.execute();
		}
	}
	
}
