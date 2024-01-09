package net.itsrelizc.cs.kevinpunisher.actions;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import net.itsrelizc.cs.kevinpunisher.DataIO;
import net.itsrelizc.cs.kevinpunisher.RuleBreaker;

public class SaveLogs {
	
//	this.userOptionCode = 1;
//	this.actionName = "Add Rule";
//	this.actionDescription = "Add a rule since Kevin did something wrong again";

	public static void execute() {
		
		System.out.println("[Punisher] Attempting to save logs...");
		
		try {
			DataIO.writeCurrentVales();
		} catch (IOException e) {
			System.out.print("[Punisher] Uh oh, this did not work well... Error:");
			e.printStackTrace();
			System.out.print("[Punisher] You might need to re-run this program.\n");
		}
		
		
		System.out.print("[Punisher] Success!\n");
		
	}
	
	
	
}
