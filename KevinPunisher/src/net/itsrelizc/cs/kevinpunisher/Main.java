package net.itsrelizc.cs.kevinpunisher;

public class Main {
	
	public static void main(String args[]) {
//		System.out.println("[Punisher] Kevin Punisher. You are currently punishing Kevin.");
//		
//		DataIO.init();
//		DataIO.createSaveIfNotExist();
//		
//		DataIO.readDefaultValues();
//		
//		while (true) {
//			Display.menu();
//			Display.listBadActions();
//			Display.userOptions();
//			
//			Display.askUserOption();
//			System.out.println();
//		}
		
		int c = 100;
		
		depositMoney(c, 42);
		
		System.out.print(c);
	}
	
	public static void depositMoney(int bankAccount, int deposit)
	{
	bankAccount += deposit;
	}
	
}
