//package GeneralCodes;
/**
 *	This class is meant to be dissect the information
 *from data.txt and input it into another class called 
 *MakeBarGraph. It takes in an input array of state names
 *and proceeds to go through data.txt to find the names
 *and separately enter them into a MakeBarGraph instance.
 *
 *
 * @author   Hritanshu Rath
 * @version  2.0
 * @since    9/24/2020
 */


import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

public class CoronaVirusData
{	
	//  Provide comments for your fields and methods.
	int [] stateNums = {6, 48, 12, 36, 17}; 				// This is NOT USED, it's just there for me
	String [] stateNames = {"California", "Florida", 		// This is to check the data for these state names
			"Illinois", "New York","Texas"};
	int [] maxArray = new int[stateNames.length];

	String dateCurrent, year, month, monthName, day; 	//this is the current date in txt format along with the rest of the variables
	String initDay;									 	//This mainly checks 
	int initMonth;

	int pH;									//this is placeHolder, can be used for multiple things
	int tempInt;							//temporary integer, can be used for multiple things
	int totalCases, totalDeaths;			//this is the counter for total cases and deaths
	int countMaxArray;						//this is used to find the max for an array

	boolean check, moreInfo;				//these are checkers to see if there is more info to be read

	Scanner inFile;							//scanner for file to be read

	public CoronaVirusData ( )
	{
		pH = 0;
		tempInt = 0;
		totalCases = 0;
		countMaxArray = 0;
		maxArray = new int[stateNames.length];
		day = "01";
		initDay = "01";
		check = true; moreInfo = true;
		inFile = OpenFile.openToRead("data3.txt");
	}

	public static void main(String [] args)   
	{
		//  Just a couple of lines; create an instance of CoronaVirusData,
		//  and call up to three methods.  Don't forget to set up the canvas
		//  (MeCollage may be helpful).

		CoronaVirusData bart = new CoronaVirusData();
		bart.runProg(1);
		//bart.test();
	}


	/**
	 * This runs a stationary picture or an animation. The first one 
	 * just takes in info and shows the graph, the second one, or the else
	 * statement takes in data and shows different frames every couple 
	 * milliseconds.
	 * @param n   if 0: picture, if 1: animation
	 */
	public void runProg(int n)
	{
		String temp = inFile.nextLine();
		setUpCanvas();
		if (n == 0)
		{
			inputInfo();
			StdDraw.show();
		}

		else if(n==1)
		{
			while(moreInfo)
			{
				totalCases = 0;
				inputInfo();
				changeDate();

				StdDraw.show();
				StdDraw.pause(100);/////////////////////////////////////////////////FIX THE SCALEMOVEMENT///////////////////////////////
			}
		}

	}

	/**
	 * This methods changes the day depending on the month that 
	 * was read from the data. the if statements check what month
	 * it is and change the max day accordingly. This method is only
	 * present to changes the date so the next line can be read.
	 */
	public void changeDate()
	{

		int max = 0;
		int tmepInt = initMonth;
		if (tmepInt == 2)
			max = 28;
		else if (tmepInt%2 == 0 && tmepInt<8)
			max = 30;
		else if (tmepInt%2 == 1 && tmepInt>7)
			max = 30;
		else max = 31;

		tempInt = Integer.parseInt(initDay);
		if (tempInt+1 > max)
			tempInt = 1;
		else tempInt++;

		if (tempInt<10)
			initDay = "0"+tempInt;
		else initDay = ""+tempInt;
		day = initDay;

		check = true;
		initMonth = Integer.parseInt(month);
	}

	/**
	 * sets up the canvas/screen where the graph will be 
	 * drawn. Double buffer is on for animation.
	 */
	public void setUpCanvas ( )
	{
		StdDraw.setCanvasSize(1400, 900);
		StdDraw.setXscale(0, 1400);
		StdDraw.setYscale(0, 900);
		StdDraw.clear(StdDraw.BLACK);

		Font font = new Font("Arial", Font.BOLD, 16);
		StdDraw.setFont(font);

		StdDraw.enableDoubleBuffering();
	}

	/**
	 * This method is going to be commented in parts
	 * (1) reading the file. This section reads the file given
	 * 			and the entire line is taken as [temp]
	 * (1.5) If loop - Just checks if it has more to read
	 * 			if not, it will stop reading.
	 * (2) while loop. This section is mainly the while loop.
	 * 			this part goes through all the lines and finds 
	 * 			the required data as long as another line is available
	 * (3) placeHolder [pH] ===> this variable is used as a placeHolder
	 * 			for the indexes of ','. It is used here to find the cases
	 * 			then is parsed and added to totalCases
	 * (4) [pH] and for loop ===> this is used this time to find the state 
	 * 			names in data.txt. If they one  state is part of the array 
	 * 			declared above, then it goes into (5).
	 * (5) if loop. This loop picks apart the one line that was passed in. 
	 * 			it takes the data and inputs it into the MakeBarGraph class
	 * (6) String excess ===> this is any excess data that we want to print 
	 * 			on the graph. The method in the MakeBarGraph class is not 
	 * 			required and can be changed to will. It is meant as a changeable
	 * 			class, although I don't know how to make that type of code yet
	 * 
	 * Variable pH = constantly changes to account for the changing index of ','
	 */
	public void inputInfo()
	{	

		// (1)

		MakeBarGraph graph = new MakeBarGraph();

		graph.setNumArray(stateNames.length); //sets array size on MakeBarGraph

		String temp = null;
		countMaxArray = 0;

		//(1.5)
		
		if (inFile.hasNext())
		{
			// (2)

			while (inFile.hasNext() && check)
			{

				temp = inFile.nextLine();
				nextDate(temp);
				setMonth();
				setDay();

				// (3)

				String notemp;
				pH = temp.indexOf(','); 
				pH = temp.indexOf(',', pH+1);
				pH = temp.indexOf(',', pH+1);
				notemp = temp.substring(pH+1, temp.indexOf(',', pH+1));
				totalCases+= Integer.parseInt(notemp.trim()) ;

				notemp = temp;													//temporary temporary string because temp needs to be trimmed for data

				// (4)

				pH = temp.indexOf(',');  		//placeholder for ',' index
				temp = temp.substring(pH+1, temp.indexOf(',', pH+1));
				temp.trim();


				for (int i = 0; i<stateNames.length; i++)			//if it is one of the numbers of the states, it gets passed to the graph obj
				{
					//System.out.println(stateNames[i]);
					//System.out.println(temp);
					if(temp.equalsIgnoreCase(stateNames[i]))
					{
						// (5)
						pH = notemp.indexOf(',');
						pH = notemp.indexOf(',', pH+1);
						pH = notemp.indexOf(',', pH+1);
						int integ = Integer.parseInt(notemp.substring(pH+1, notemp.indexOf(',', pH+1)));
						addMax(integ);

						//System.out.println(integ);
						graph.addData(integ);
						pH = notemp.indexOf(',');

						graph.addDataString(notemp.substring(pH+1, notemp.indexOf(',', pH+1)));
						//System.out.println(notemp.substring(pH+1, notemp.indexOf(',', pH+1)));
					}
				}
				if (Integer.parseInt(initDay) != Integer.parseInt(day))
					check = false;
			}



			//String date = printDate();		//make method to turn date into string

			String excessInput = "" + monthName + " " + day + ", " + year + "|US Total: " + totalCases;
			int tempMax = findMax();
			graph.setMax(tempMax);
			graph.setExcessDraw(excessInput);
			StdDraw.clear(StdDraw.BLACK);
			graph.draw(); 						//draws the graph
		} else moreInfo = false;

	}

	/**
	 * This method adds the max data from the list of 
	 * data that is read. This sets the scale for the 
	 * graph.
	 * @param n		the largest data value that you have
	 */
	public void addMax(int n)
	{
		if(countMaxArray>=stateNames.length)
			countMaxArray = 0;
		maxArray [countMaxArray] = n;
		countMaxArray++;
	}


	/**
	 * this method finds the max value of the data that was
	 * provided. It finds the max between the two and returns 
	 * the largest value
	 * @return		the largest value of the data value set given.
	 */
	public int findMax()
	{
		int max = 0;
		for (int i = 0; i < maxArray.length; i++)
		{
			if(max<maxArray[i])
				max = maxArray[i];
		}

		return max;
	}

	/**
	 * This sets up the date to be printed by the program. It
	 * uses indexOf('-') to partition each by day, month and year.
	 * @param input
	 */
	public void nextDate(String input)
	{

		input = input.substring(0, input.indexOf(','));

		year = input.substring(0, input.indexOf('-'));
		int temp = input.indexOf('-');
		month = input.substring(temp+1, input.indexOf('-', temp+1) );
		temp = input.indexOf('-', temp+1);
		day = input.substring(temp+1);


	}

	/**
	 * This method just changes the monthName that
	 * corresponds with its number.
	 */
	public void setMonth()
	{
		switch(month)
		{
		case "01": monthName = "January"; 	break;
		case "02": monthName = "February";	break;
		case "03": monthName = "March";		break;
		case "04": monthName = "April";		break;
		case "05": monthName = "May";		break;
		case "06": monthName = "June";		break;
		case "07": monthName = "July";		break;
		case "08": monthName = "August";	break;
		case "09": monthName = "September";	break;
		case "10": monthName = "October";	break;
		case "11": monthName = "November";	break;
		case "12": monthName = "December";	break;
		}
	}

	/**
	 * takes out the zero from the day if it has one
	 * before a number.
	 */
	public void setDay()
	{
		if (day.substring(0,1).equalsIgnoreCase("0"))
		{
			day = day.substring(1);
		}
	}




































	/**
	 * this is the previous code that did the same thing as the one above, except this 
	 * checked for the fips of the states instead of the names when checking 
	 * for the lines
	 */
	public void test()
	{
		/*
		while(inFile.hasNext())
		{

			temp = inFile.nextLine();
			String notemp = temp;

			pH = temp.indexOf(',');
			pH = temp.indexOf(',', pH+1);		//sets the index to the second ","

			//System.out.println(temp + "\n");
			temp = temp.substring(pH+1, temp.indexOf(',', pH+1));
			temp.trim();
			tempInt = Integer.parseInt(temp);
			//System.out.println(tempInt + "\n");

			for (int i = 0; i<5; i++)			//if it is one of the numbers of the states, it gets passed to the graph obj
			{
				if(tempInt == stateNums[i])
				{
					pH = notemp.indexOf(',', pH+1);
					int integ = Integer.parseInt(notemp.substring(pH+1, notemp.indexOf(',', pH+1)));
					//System.out.println(integ);
					graph.addData(integ);
					pH = notemp.indexOf(',');
					graph.addDataString(notemp.substring(pH+1, notemp.indexOf(',', pH+1)));
					//System.out.println(notemp.substring(pH+1, notemp.indexOf(',', pH+1)));
				}
			}
		}
		 */
	}



}











