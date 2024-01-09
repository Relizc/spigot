package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProfileGenerator {
	
	public static boolean preprocessData() {
		System.out.print("Hold on, I am loading a list of names... ");
		
		File names = new File("name_info.dat");
		Scanner fr;
		try {
			fr = new Scanner(names);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error! name_info.dat is not in your current directory! It contains the essential list of names for this machine to run properly.");
			return false;
		}
		
		/* There are total of 3 lines in name_info.dat:
		 * 
		 * 1. Women First Names
		 * 2. Men First Names
		 * 3. Surnames
		 * 
		 * Men and Women first names are from 
		 * https://www.cs.cmu.edu/Groups/AI/areas/nlp/corpora/names/female.txt
		 * https://www.cs.cmu.edu/Groups/AI/areas/nlp/corpora/names/male.txt
		 * 
		 * Surnames are from
		 * https://www.powershellgallery.com/packages/TelligentCommunitySample/0.1.1/Content/Surnames.txt
		 * 
		 */
		
		int l = 0;
		
		String buffer = "";
		
		while (fr.hasNextLine()) {
			String line = fr.nextLine();
			
			for (int i = 0; i < line.length(); i ++) {
				char cur = line.charAt(i);
				
				if (cur == '.') {
					if (l == 0) Profile.addGirl(buffer);
					else if (l == 1) Profile.addBoy(buffer);
					else if (l == 3) Profile.addState(buffer);
					else Profile.addSur(buffer);
					
					// System.out.println(buffer + " " + l);
					
					buffer = ""; // Clear Buffer
				} else {
					buffer += cur;
				}
			}
			
			l ++;
		}
		
		System.out.println("SUCESS!");
		System.out.println("Loaded " + Profile.countGirl() + " girl names, " + Profile.countBoy() + " boy names, " + Profile.countSur() + " surnames, and 50 US States.\n");
		
		return true;
		
		
	}
	
	public static int readIntCatchErr(Scanner in) {
		int value;
		try {
			value = in.nextInt();
		} catch (Exception e) {
			System.out.println("Error! You need to enter a valid input choice.");
			return -1;
		}
		if (value < 1) {
			System.out.println("Error! Your input has to be at least 1.");
			return -1;
		}
		return value;
	}

	public static void main(String[] args) {
//    	
//    	if (!preprocessData()) return;
//    	
//    	Scanner input = new Scanner(System.in);
//        
//    	System.out.println("How many profiles would you like to generate?");
//    	System.out.print("Enter your choice: ");
//    	
//    	int length = readIntCatchErr(input);
//    	if (length == -1) return; // If it returns -1, an error occurred and this process should be stopped.
//    	
//    	System.out.println("\nGenerating " + length + " profile(s).\n");
//    	
//    	for (int i = 0; i < length; i ++) {
//    		Profile profile = Profile.random();
//    		
//    		System.out.println("---------- Result (" + (i + 1) + "/" + length + ") ----------");
//    		System.out.println(profile);
//    		System.out.println("----------------------------------");
//    		
//    		System.out.println("\n");
//    	}
//    	
//    	System.out.println("Completed all generations. Thanks for using this device!");
//    	System.out.print("Press ENTER to quit");
//    	
//    	input.nextLine();
//    	input.nextLine();
		
		Greetings hello = new Greetings();
		hello.hello();
		hello.translate();
		hello.greeting();
    }

}