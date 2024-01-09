package net.itsrelizc.cs.kevinpunisher.actions;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import net.itsrelizc.cs.kevinpunisher.DataIO;
import net.itsrelizc.cs.kevinpunisher.RuleBreaker;

public class ClearLogs {
	
//	this.userOptionCode = 1;
//	this.actionName = "Add Rule";
//	this.actionDescription = "Add a rule since Kevin did something wrong again";

	public static void execute() {
		
		System.out.println("[Punisher] Attempting to clear logs...");
		
		RuleBreaker.all.clear();
		DataIO.kevinInfos = 0;
		DataIO.kevinWarns = 0;
		DataIO.kevinVios = 0;
		
		System.out.println("[Punisher] Sucess! You might need to save the file in order to clear the data on the file.");
		System.out.println("[Punisher] Did not mean to clear file? That is fine! Just close this program and re-run!");
		
	}
	
	
	
}
