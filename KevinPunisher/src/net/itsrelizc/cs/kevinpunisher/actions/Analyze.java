package net.itsrelizc.cs.kevinpunisher.actions;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import net.itsrelizc.cs.kevinpunisher.DataIO;
import net.itsrelizc.cs.kevinpunisher.RuleBreaker;

public class Analyze {
	
//	this.userOptionCode = 1;
//	this.actionName = "Add Rule";
//	this.actionDescription = "Add a rule since Kevin did something wrong again";

	public static void execute() {
		
		System.out.println("[Punisher] Analyzing...");
		
		int i = DataIO.kevinInfos;
		int w = DataIO.kevinWarns;
		int v = DataIO.kevinVios;
		
		int points = i + (w * 2) + (v * 5);
		System.out.println("[Punisher] Kevin Annoyingness: " + points);
		
		if (points == 0) {
			System.out.println("[Punisher] Cannot believe he is so behaving now... Paradise");
		} else if (points <= 10) {
			System.out.println("[Punisher] A bit annoying... I guess you can hold your anger for a bit more...");
		} else if (points <= 20) {
			System.out.println("[Punisher] Okay, Kevin has to be warned. Tell him to stop.");
		} else {
			System.out.println("[Punisher] We all had enough of him. You should probably " + new String[] {
					"call his parents.",
					"send him to Mr. Shen. ",
					"lock him in a room and ground him. ",
					"give him 0 on his test grade. ",
					"put him on the Hall of Shame ",
					"make him sing a song. ",
			}[new Random().nextInt(6)]);
		}
	}
	
	
	
}
