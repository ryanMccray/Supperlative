/*
 * Made by Team: The Welp
 * 
 * Project Name: Supper-lative
 * 
 * Including: Ryan McCray, Kim Hsun, Jessica Zhang, Joyce Wu
 * 
 */

package Supperlative;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;

public class Main {
	
		static Scanner kb;	// scanner connected to keyboard input, or input file
		private static String inputFile;	// input file, used instead of keyboard input if specified
		static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
		private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
		private static boolean DEBUG = false; // Use it or not, as you wish!
		static PrintStream old = System.out;	// if you want to restore output to console
    
		
		
	    /**
	     * Main method.
	     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
	     * and the second is test (for test output, where all output to be directed to a String), or nothing.
	     */
	    public static void main(String[] args) { 
	    	if (args.length != 0) {
	            try {
	                inputFile = args[0];
	                kb = new Scanner(new File(inputFile));			
	            } catch (FileNotFoundException e) {
	                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
	                e.printStackTrace();
	            } catch (NullPointerException e) {
	                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
	            }
	            if (args.length >= 2) {
	                if (args[1].equals("test")) { // if the word "test" is the second argument to java
	                    // Create a stream to hold the output
	                    testOutputString = new ByteArrayOutputStream();
	                    PrintStream ps = new PrintStream(testOutputString);
	                    // Save the old System.out.
	                    old = System.out;
	                    // Tell Java to use the special stream; all console output will be redirected here from now
	                    System.setOut(ps);
	                }
	            }
	        } else { // if no arguments to main
	            kb = new Scanner(System.in); // use keyboard and console
	        }
	    	
	    	//how do i want to store supplier, requester
	    	//supplier gives location, perishables/non perishable pounds
	    	//requester gives location, pound demand
	    	
	    	String supplierFile = "SupplierRecords.txt";
	    	String requesterFile = "RequesterRecords.txt";


	    	boolean reuse = true;
	    	
	    	if(!reuse) {
	    	try {
				PrintWriter SupplierDetails = new PrintWriter(supplierFile);
				try {
					PrintWriter RequesterDetails = new PrintWriter(requesterFile);
					
					//*******************************************************
					System.out.println("Welcome to Supperlative! \n");
			    	
			    	boolean flag =  true;
			    	while(flag) {
			    	 System.out.print("Supplier or Requester? Enter \"end\" to close the program. ");
			         String comp = kb.nextLine();
			         
			    	if(comp.equals("Supplier")) {
			    		
			    		System.out.print("What is your company name? ");
				        String next = kb.nextLine();
				        SupplierDetails.print(next + " ");
			    		
				        System.out.print("How many pounds of perishables do you have? ");
				        next = kb.nextLine();
				        SupplierDetails.print(next + " ");
				        
				        System.out.print("How many pounds of non-perishables do you have? ");
				        next = kb.nextLine();
				        SupplierDetails.print(next + " ");
				        
				        System.out.print("What is your address? (In x,y format) ");
				        next = kb.nextLine();
				        SupplierDetails.print(next + " ");
			    		
			    		
			    		
			    		
			    		SupplierDetails.println("");
			    		
			    		System.out.println("Thanks for the food!");
			    		
			    	}else if(comp.equals("Requester")) {
			    		
			    		System.out.print("What is your company name? ");
				        String next = kb.nextLine();
				        RequesterDetails.print(next + " ");
			    		
				        System.out.print("How many pounds of food do you need? ");
				        next = kb.nextLine();
				        RequesterDetails.print(next + " ");
				        
				        System.out.print("What is your address? (In x,y format) ");
				        next = kb.nextLine();
				        RequesterDetails.print(next + " ");
				        
				        RequesterDetails.println("");

			    		System.out.println("Thanks for the request!");
			    		
			    	}else if(comp.equals("end")){
			    		
			    		System.out.println("Thanks for using Supperlative!");
			    		flag = false;
			    		
			    	}else{
			    		System.out.println("Please input a valid company identifier");
			    	}
			    	
			    }
					
					
				RequesterDetails.close();
					
				} catch (FileNotFoundException e) {
					System.out.println("Can't find RequesterRecords");
				}
				
				SupplierDetails.close();
				
			} catch (FileNotFoundException e) {
				System.out.println("Can't find SupplierRecords");
			}
	    	}
	    	
	    	Scanner requester = new Scanner("RequesterRecords.txt");
	    	Scanner supplier = new Scanner("SupplierRecords.txt");
	    	
	    	
	    	ArrayList<SupplierNode> suppliers =  new ArrayList<SupplierNode>();
	    	ArrayList<RequesterNode> requesters = new ArrayList<RequesterNode>();
	    	
	    	while(supplier.hasNextLine()) {
	    		String title = supplier.next();
	    		String perish = supplier.next();
	    		String nonperish = supplier.next();
	    		String xy = supplier.next();
	    		String x = xy.substring(0, 1);
	    		String y = xy.substring(2);
	    		
	    		SupplierNode hold = new SupplierNode(title,Integer.parseInt(perish), Integer.parseInt(nonperish),
	    				Integer.parseInt(x),Integer.parseInt(y));
	    		
	    		suppliers.add(hold);
	    		
	    		
	    	}
	    	
	    	while(requester.hasNext()) {
	    		String title = requester.next();
	    		String pounds = requester.next();
	    		String xy = supplier.next();
	    		String x = xy.substring(0, 1);
	    		String y = xy.substring(2);
	    		
	    		RequesterNode hold = new RequesterNode(title,Integer.parseInt(pounds),
	    				Integer.parseInt(x),Integer.parseInt(y));
	    		
	    		requesters.add(hold);
	    		
	    	}
	    	
	    	while(!suppliers.isEmpty()) {
	    	double[] dist = new double[requesters.size()];
	    	int min = 0; //location of closest requester
	    	int max = 0; //location of furthest requester
	    	for(int i = 0; i < dist.length; i++) {
	    		dist[i] = suppliers.get(0).SupplierDistance(requesters.get(i).x,requesters.get(i).y);
	    		if(dist[i] < dist[min]) {
	    			min = i;
	    		}
	    		if(dist[i] > dist[max]) {
	    			max = i;
	    		}
	    	}
	    	boolean minFlag = false;
	    	int balance = suppliers.get(0).perish - requesters.get(min).pounds;
	    	if(balance > 0) {
	    		System.out.println(suppliers.get(0).name + "should deliver " + requesters.get(min).pounds +
	    				" pounds of perishables to " + requesters.get(min).name + ", fulfilling their request.");
	    		requesters.remove(min);
	    	}else {
	    		requesters.get(min).pounds = requesters.get(min).pounds - suppliers.get(0).perish;
	    	}
	    	
	    	suppliers.remove(0);
	    	
	    	//want an array for unmet needs of requesters
	    	
	    	//i want to determine distance between each supplier and requester
	    	//go through list
	    	
	    	//end of day calculations go here
	    	
	    	}
	    	
	    }

}
