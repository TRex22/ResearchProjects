package myGeoInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SaveFile {
	//fields
	private String ErrorMessages;
	
	//To write a file is to in the syllabus
	//I learned this code since it was in the notes my teacher gave me
	//as extra information. Like a side note
	//This programming technique is therefore hard to reference since it is not 
	//externally learned code but not truly internally learned code either
	
	//method -ExportData This method is able to save the textfile
	//Inputed is the file to write to and its path (one variable)
	//Also inputed is the data to write to the file
	//outputted is a message which either return a job comnpleted
	//or an error message
	public String ExportData (File file, String DataIn)
	{
		String out = "";
		//call a FileWriter Stream to write to the file
		try {
			FileWriter FW = new FileWriter(file);
			//printwriter to print data onto file per line
			PrintWriter PW = new PrintWriter(FW);
			//scanner to read each line of the inputted data
			Scanner scData = new Scanner (DataIn);
			
			//loop until end of line (ie end of data, the last line)
			while (scData.hasNext())
			{
				//create file with data
				// the\n is for next line and \r for end of line
				PW.println(scData.nextLine()+"\r\n");
				
				
			}
			//send confirmation message
			out = "File: "+file+" saved successfully.";
			//close the streams
			scData.close();
			PW.close();
			FW.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			//will return the errormessage
			//not in the output
			//out = "Error occured creating the file\nError Message:\n"+e;
			ErrorMessages = "Error occured creating the file: "+file+"\nError Message:\n"+e;
			System.err.println(ErrorMessages);
		}
		
		
		return out;
	}
	
	
	//method - gerErrorMessages()
	//no input
	//will return the field ErrorMessages
	//Acessor
	public String getErrorMessages()
	{
		return ErrorMessages;
	}
	
}
