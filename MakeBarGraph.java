//package GeneralCodes;
/**
 *This class is a bar graph maker. It takes in user input data 
 *and churns it into a bar graph. There are many setters that 
 *set the data so that the program would be able to draw the 
 *graph
 *
 *This class is flexible with its scale and its input data
 *you can add strings or integers to as the data, and it will
 *make the graoh for you. 
 *
 * @author   Hritanshu Rath
 * @version  1.0
 * @since    9/11/2020
 */

import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

public class MakeBarGraph {

	int [] dataNums;							//this is NOT USED in CoronaVirusData
	int max, scale, pH;			
	/*
	 * max = max out of all the states, will be used for scale
	 * scale = the scale of the graph, it will always be in the scale of powers of 10
	 * pH - placeHolder, can be used for multiple things, in this case, the index of ',' 
	 */
	int countMax, countData, countCol;
	/*
	 * These are the counts, and they just count everytime a method is loaded,
	 * they are directly referrable to their names and methods.
	 */

	double barScale;

	String dataStrings, excess;
	/*
	 * dataStrings is the combination of all the state names
	 * excess is the excess data that is inputed from CoronaVirusData
	 */
	Color col;
	/*
	 * makes changing colors easy		NOT IMPLEMENTED YET
	 */
	boolean excessDraw;
	/*
	 * this checks to see if there is excess data, if not, it just draws the graph
	 */

	public MakeBarGraph()
	{
		max = 0;
		scale = 0;

		countMax = 0;
		countData = 0;
		countCol = 0;

		barScale = 0;

		dataStrings = "";
		excess = "";

		excessDraw = false;
	}

	/*
	 * main method, so that not all classes have to be static
	 */
	public static void main(String [] args)
	{
		MakeBarGraph bart = new MakeBarGraph();
		bart.draw();
	}

	/**
	 * NOT USED
	 * This class initializes the array of integers
	 * @param n		enter how long your list of data is
	 */
	public void setNumArray(int n) //sets up the data array
	{
		dataNums = new int[n];
	}

	/**
	 * This method adds the data the user inputs, the countData
	 * goes up everytime as to not overlap the array
	 * @param n		enter the data, one at a time
	 */
	public void addData(int n)
	{
		dataNums[countData] = n;
		//System.out.println(dataNums[countData]);
		countData++;

	}

	/**
	 * This method checks the max number to set the scale of the
	 * graph. It takes the max number and turns it into a 1's 
	 * digit to later turn it into a power of 10 multiplied by the 
	 * max
	 * @param n 	enter the largest number in your data
	 */
	public void setMax(int n)  //sets max number for scale
	{
		max = n;
		scale = n;
		barScale = n;
		if (scale <10)
			countMax = 1;
		//System.out.println(scale);

		while (scale >10)
		{
			countMax++;
			scale = scale/10;
			barScale = barScale/10;
		}
		//System.out.println(scale + ", " + countMax);
	}

	/**
	 * This method adds Strings for data instead of ints.
	 * it is comma separated to make the data dissection 
	 * easier.
	 * @param s		enter the names of your data 
	 */
	public void addDataString(String s)  //
	{
		dataStrings = dataStrings + "," + s;
	}

	/**
	 * not implemented yet
	 * @param s
	 */
	public void setBackgroundCol(String s)
	{

	}

	/**
	 * Not implemented yet
	 * @param s
	 */
	public  void setBarColors(String s) //sets colors of bars
	{

	}

	/**
	 * This sets up the [program window size] with the
	 * select sizes and the [background color] as 
	 * well as the [font]
	 */
	public void setUpCanvas ( )
	{
		StdDraw.setCanvasSize(1400, 900);
		StdDraw.setXscale(0, 1400);
		StdDraw.setYscale(0, 900);
		StdDraw.clear(StdDraw.BLACK);

		Font font = new Font("Arial", Font.BOLD, 16);
		StdDraw.setFont(font);

		drawGraph();
		//StdDraw.enableDoubleBuffering();
	}

	/**
	 * This draws the graph after taking the scale and the max
	 * it does not draw the bars yet.
	 */
	public void drawGraph()
	{
		StdDraw.setPenColor(Color.DARK_GRAY);
		int offset = 60 - countMax*7;

		for (int i = 0; i <= scale; i++)
		{
			StdDraw.line(50 + i*1000/barScale, 0, 50+i*1000/barScale, 850);
			if (i == 0)
			{
				StdDraw.textLeft(47 + i*1000/barScale, 
						865, 
						"" + (int) (i*Math.pow(10, countMax)));
			} else {
				if (countMax >=3)
				{
					StdDraw.textLeft(offset + i*1000/barScale, 
							865, 
							"" + (int) (i*Math.pow(10, countMax-3))+"k");
				}
				else {
					StdDraw.textLeft(offset + i*1000/barScale, 
							865, 
							"" + (int) (i*Math.pow(10, countMax)));
				}

			}

		}

		drawBars();
	}

	/**
	 * This one takes in all the data and starts making the graph
	 * 
	 * (confuzzle) ===> this puts the data into the scale, since the 
	 * 			the graph size is 1000, it needs to fit the scale into 
	 * 			the scale provided in drawGraph()
	 * 
	 * (if statement) ===> This one checks if it is the last data in
	 * 			dataStrings so that it doesnt throw an error.
	 */
	public void drawBars()
	{
		for (int i = 0; i<dataNums.length; i++)
		{
			StdDraw.setPenColor(cycleColors());
			//StdDraw.setPenColor(Color.RED);
			StdDraw.filledRectangle(dataNums[i]*500/(barScale*Math.pow(10, countMax))+50,  //(confuzzle)
					810 - 80*i, 
					dataNums[i]*500/(barScale*Math.pow(10, countMax)),
					30);

			StdDraw.setPenColor(Color.WHITE);

			String dataString = "" +dataNums[i];
			//System.out.println(dataString);
			StdDraw.textRight(dataNums[i]*1000/(barScale*Math.pow(10, countMax))+40,
					810 - 80*i, 
					""+dataNums[i]);


			if(i + 1< dataNums.length)													// (if statement)
			{
				StdDraw.textLeft(dataNums[i]*1000/(barScale*Math.pow(10, countMax))+60,
						810 - 80*i,
						dataStrings.substring(pH+1, dataStrings.indexOf(',', pH+1)));
			} else {
				StdDraw.textLeft(dataNums[i]*1000/(barScale*Math.pow(10, countMax))+60,
						810 - 80*i,
						dataStrings.substring(pH+1));
			}
			pH = dataStrings.indexOf(',', pH+1);

		}
		pH = 0;
	}

	/**
	 * This just turns excess data into true
	 * so that the program will draw it
	 * 
	 * @param s		any input you'd like to print on the graph, separate with '|' for next line (still to be implemented)
	 */
	public void setExcessDraw(String s)
	{
		excessDraw = true;
		excess = s;
	}

	/**
	 * any extra details you want to add will be in this method. In this case
	 * it is the block of information in the bottom right of the program.
	 * the offset is just to size up where the block should be.
	 */
	public void drawExcess() 
	{
		String stemp = excess;

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledRectangle(1250, 100, 150, 100);

		Font font = new Font("Arial", Font.BOLD, 32);
		StdDraw.setFont(font);

		int offset = -60;
		StdDraw.setPenColor(Color.RED);
		stemp = excess.substring(0, excess.indexOf('|'));
		StdDraw.textLeft(1100, 200+offset, stemp);
		stemp = excess.substring(excess.indexOf('|')+1);
		StdDraw.textLeft(1100, 170+offset, stemp);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.textLeft(1100, 140+offset, "CORONAVIRUS");
		StdDraw.textLeft(1100, 110+offset, "Cases by State");
		Font font1 = new Font("Arial", Font.BOLD, 16);
		StdDraw.setFont(font1);
	}

	/**
	 * this one cycles through a couple of colors
	 * for diversity in the visual of the graph
	 * @return	a Color object
	 */
	public Color cycleColors()
	{
		countCol++;
		if(countCol == 8)
			countCol = 1;
		switch (countCol)
		{
		case 1 : return Color.RED;		
		case 2 : return Color.BLUE;		 
		case 3 : return Color.GREEN;		 
		case 4 : return Color.DARK_GRAY;		 
		case 5 : return Color.GRAY;		 
		case 6 : return Color.CYAN;		
		case 7 : return Color.PINK;		
		}
		return Color.BLUE;
	}

	/**
	 * this method starts up the process of drawing
	 * the graph. Other methods can be added here like
	 * excessDraw() or any of your own creations you want
	 *  to add
	 */
	public void draw()
	{
		//System.out.println(dataStrings);
		//setUpCanvas();

		drawGraph();
		if (excessDraw) drawExcess();
	}

}




