/*
 * Author: Andres Santamaria
 */


import java.util.*;
import java.io.*;


public class RecoverPassword
{
	
	
	public static long hash(String str)
	{
		String l = str.substring(0, 7);
		String r = str.substring(7);
		long left = Long.parseLong(l);
		long right = Long.parseLong(r);
		
		return ((243 * left) + right) % 85767489;

	}
	public static void main(String [] args) throws IOException
	{
		if(args.length < 2)
		{
			System.out.println("ERROR: Correct syntax for running program is : java RecoverPassword <file_name> <hash_value>");
			System.exit(1);
		}
		Scanner dict = new Scanner(new File(args[0]));
		long hashVal = Long.parseLong(args[1]);
		System.out.println("\t----------------------------------------");
		System.out.println("\n\tPassword Recovery by Andres Santamaria");
		System.out.println("\n\t   Dictionary file name       : " + args[0]);
		System.out.println("\t   Salted password hash value : " + hashVal);
		System.out.println("\tIndex   Word   Unsalted ASCII equivalent");
		int cmbCnt = 0;
		int strCnt = 0;
		int salt = 0;
		boolean found = false;
		String reportPass = new String();
		String reportASCII = new String();
		int tested = 0;
		
		while(dict.hasNext())
		{
			strCnt++;
			StringBuilder ascii = new StringBuilder();
			String temp = dict.nextLine();
			int charVal;
			for(int i = 0; i < 6; i++)
			{
				charVal = (int) temp.charAt(i);
				ascii.append(charVal);
			}
			String cnt = String.format("%3d", strCnt);
			System.out.println("\t " + cnt + " :  " + temp + " " + ascii.toString());

			for(int i = 0; i < 1000; i++)
			{
				StringBuilder tempPass = new StringBuilder();
				cmbCnt++;
				String paddedSalt = String.format("%03d", i);
				tempPass.append(paddedSalt);
				tempPass.append(ascii.toString());
				long tempHash = RecoverPassword.hash(tempPass.toString());
				if(tempHash == hashVal)
				{
					found = true;
					reportPass = temp;
					reportASCII = ascii.toString();
					salt = i;
					tested = cmbCnt;
				}
			}
				
		}

		if(found)
		{
			System.out.println("\tPassword recovered:");
			System.out.println("\t   Password            : " + reportPass);
			System.out.println("\t   ASCII value         : " + reportASCII);
			System.out.println("\t   Salt value          : " + salt);
			System.out.println("\t   Combinations tested : " + tested);
		}
		else
		{
			System.out.println("\tPassword not found in dictionary");
			System.out.println("\tCombinations tested: " + cmbCnt);
		}
	}

	
}
