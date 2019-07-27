/* 
   Thomas Jones-Moore
   CSCI 405
   Programming Assignment 3   

   The purpose of this assignment is to find the optimal hotels to stay at when given
   a text file containing each hotel's distance from the start point and the goal is to
   drive 200 miles a day, or else you face a penalty for going above or below the 200 miles
   mark. My implementation uses a greedy approach that provides the optimal answers. It 
   keeps track of the current minimum penalty and hotel index (of the corresponding
   minimum penalty) for the current day. 

   From the start poiint of the current day, it linearly searches through the hotel
   distances array, keep track of the minimum penalty. Once the penalty of a hotel
   index is strictly GREATER than the current minimum penalty, we know that the LAST
   index that was checked contains the minimum penalty for the day. Updates starting index,
   resets minimum penalty, and continues. This algorithm runs in O(n + 2*(# hotel stops)). 
   The reason why the runtime contains '2 * # hotel stops' is because each time it finds an 
   optimal hotel stop, it calculates that value twice. 
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class minimizePenalty{

    public static void main(String[] args){
	if(args.length < 1){
	    System.out.println("Input text file.");
	    System.exit(1);
	}

	try{
	    //System.out.println("Input: "+readFile(args[0]));	
	    minPenalty(readFile(args[0]));
	}
	catch(Exception e){
	    System.out.println("Error: " + e);
	    System.exit(1);
	}
    }

    /* Reads inputted text file line by line and throws each line into a new spot
       in the sequence array which is returned if no exceptions are caught. 
       Requred the text file to have each word on its own line. */
    private static ArrayList<Integer> readFile(String fileName) throws Exception{
	ArrayList<Integer> sequences = new ArrayList<Integer>();
	File FILE = new File(fileName);

	try{
	    FileReader fr = new FileReader(FILE);
	}
	catch(Exception e){
	    System.out.println("Could not find file.");
	    System.exit(1);
	}

	Scanner sc = new Scanner(FILE);
	String line = "";
	while(sc.hasNextLine()){
	    line = sc.nextLine();
	    String[] lineArray = line.split("\t");
	    for(String t: lineArray){
		try{
		    sequences.add(Integer.parseInt(t));
		}
		catch(Exception e){
		    System.out.println("Bad input file syntax. Error: " + e);
		    System.out.println("Please enter filename with valid syntax. Exiting program.");
		    System.exit(1);
		}
	    }
	}
	return sequences;
    }

    /* Input: array of hotel distances from start
       Output: void

       This function traverses through each hotel in the array. It first makes a new Penalty
       object which holds the current smallest penalty from the current starting point, and 
       the index that sits at (i.e. which hotel it is). It traverses through each hotel 
       and when it finds a hotel who's penalty is less than the current smallest penalty,
       it updates the current smallest penalty to the one it just found, updates the inde
       at which it lies, and continues. The moment we find a penalty that is greater than
       the the current smallest penalty, we know that the optimal hotel to stay at for the 
       night has already been found (since the numbers are ascending). This process continues
       until we reach the last index (i.e. the last hotel). This is a greedy approach to
       the problem which actually provides optimal results.*/
    private static void minPenalty(ArrayList<Integer> hotelDistances){
	Penalty smallestPenalty = new Penalty(Integer.MAX_VALUE, 0);
	int tempMilesPen;	
	int startPoint = 0;
	boolean done = false;
	int i = 1;
	ArrayList<Integer> finalHotels = new ArrayList<Integer>();
	int totalPenalty = 0;
	finalHotels.add(0);
	
	while(done == false){	    	    	    	    
	    tempMilesPen = fx(hotelDistances.get(i) - hotelDistances.get(startPoint));
	    
	    if(tempMilesPen < smallestPenalty.getPenalty()){
		smallestPenalty.setPenalty(tempMilesPen);
		smallestPenalty.setIndex(i);

		if(i < hotelDistances.size() - 1 && fx(hotelDistances.get(i+1) -
		      hotelDistances.get(startPoint)) >= tempMilesPen && !finalHotels.contains(i)){
		    startPoint = i;
		    finalHotels.add(i);
		    totalPenalty += smallestPenalty.getPenalty();
		    smallestPenalty.setPenalty(Integer.MAX_VALUE);
		    smallestPenalty.setIndex(startPoint);
		}
	    }
	    
	    i++;

	    if(i == hotelDistances.size()){
		done = true;
		if(!finalHotels.contains(i-1)){
		    finalHotels.add(i-1);
		}
	    }
	}

	System.out.println("Total number of hotels: "+ hotelDistances.size());
	System.out.println("Indexes of optimal hotels: "+finalHotels);
	System.out.println("Final penalty: "+totalPenalty);
    }

    /* The function to calculate penalty using variable x. */
    private static int fx(int x){
	return( (200 - x) * (200 - x) );
    }

}
