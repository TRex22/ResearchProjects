package myGeoInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *This code is made by Jason Chalom 12S
 *I am using programming techniques which I learned in class
 *
 */
public class OpenFile {
	//This first method opens survey data
	//There is no need for arrays since the 
	//data may not have any logical and uniform 
	//structure so it must just check if the survey file
	//is correct, e.g. data validation
	//It must then make the survey file if it is valid
	//into a String variable 
	
	//OpenSurvey method
	//input the file path from the importjfilechooser in the gui main method
	//determine if the file is valid
	//return the file as a String
	public String OpenSurvey(File SurveyFile)
	{
		String surveyData = "";
		String FileLocation = ""+SurveyFile;
		boolean SurveyISValid = true;
		//A rule for a valid survey is that it must be a .txt extension
		//determine if it is a txt file
		if (FileLocation.contains(".txt"))
		{
			//if it is a txt file then 
			//the second rule must be determined
			//this rule makes any survey file which is blank invalid
			//this is also where the survey data will also be read if
			//the file has data in it
			
			//Scanner to scan the file
			try {
				Scanner scFile = new Scanner (SurveyFile);
				//this determines if there is data in the survey file
				//or it is not valid
				if (scFile.hasNext())
				{
					//loop to run through every line in the file
					//and add it to the return string
					while (scFile.hasNext())
					{
						//Scanner for the line
						//the String must have \n to denote next lines
						surveyData += scFile.nextLine()+"\n";
						//close the stream
						//scLine.close();
					}
				}
				else
				{
					//if there is no data in the survey file it is invalid
					SurveyISValid=false;
				}
				//close the stream
				scFile.close();
			} catch (FileNotFoundException e) {
				// if the scanner fails then the survey file is also invalid 
				//rule 3
				e.printStackTrace();
				SurveyISValid = false;
			}
		}
		else
		{
			SurveyISValid = false;
		}
		
		if (SurveyISValid == true)
		{
			return surveyData;
		}
		else
		{
			return "Error. Survey was not valid.";
		}
		
		
	}
	
	
	
	
}
