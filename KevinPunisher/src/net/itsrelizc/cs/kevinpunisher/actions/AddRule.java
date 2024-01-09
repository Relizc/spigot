package net.itsrelizc.cs.kevinpunisher.actions;

import java.util.Random;
import java.util.Scanner;

import net.itsrelizc.cs.kevinpunisher.DataIO;
import net.itsrelizc.cs.kevinpunisher.RuleBreaker;

public class AddRule {
	
//	this.userOptionCode = 1;
//	this.actionName = "Add Rule";
//	this.actionDescription = "Add a rule since Kevin did something wrong again";

	public static void execute() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("What did Kevin did wrong? Enter: ");
		String bad = input.nextLine();
		
		if (bad.trim().length() == 0) {
			System.out.println("Seems like you entered nothing... I assume Kevin was just being naughty.\n");
			bad = "Being " + new String[] {
					"",
					"Annoyingly ",
					"Ridiculously ",
					"Amazingly ",
					"Stupidly ",
					"Agressively ",
					"Kindly "
			}[new Random().nextInt(7)] + "Naughty";
		}
		
		int evil = -1;
		
		while (true) {
			System.out.print("How severe it is on a scale of 1-3: ");
			evil = input.nextInt();
			input.nextLine();
			
			if (evil == 1) {
				DataIO.kevinInfos ++;
			} else if (evil == 2) {
				DataIO.kevinWarns ++;
			} else if (evil == 3) {
				DataIO.kevinVios ++;
			} else {
				System.out.println("You must enter 1, 2, or 3! Lets try again...\n");
				continue;
			}
			
			break;
		}
		
		System.out.println("Sucessfully added rule-breaking record!");
		
		RuleBreaker add = new RuleBreaker(bad, evil);
		RuleBreaker.addToList(add);
		
		
	}
	
	
	
}
