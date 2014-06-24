package myGeoInfo;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AddData {
	//class outline
	//this may not be completely representative of the actual programming
	//see the comments directly before each method for more
	//Accurate information

	//This class adds the data from the input from the main class
	//to the data base
	//It is also responsible for the bulk of the data
	//validation in this project
	//It makes sure the data entered is valid and the right variable type
	//it will return an error if it is not
	//It will then send the data to the database
	//If there is a survey it will ask the user to identify it in the database
	//if it is not there than a similar algorithm to the on to add the survey to the system
	//will run
	//This is done with some extra methods in this class which return specific Strings
	//each table of data is handled by a separate class since not every table has to be added
	//there will be a parameterized constructor
	//which will accept the inputed data
	//data handling will occur in the methods

	//fields
	private String QueryCode;
	private int SiteID;
	//there is a problem with adding the site every time
	private boolean SiteIsValid = true;
	//call the classes needed in the below methods
	DBCode DBLink = new DBCode();
	OpenFile openfile = new OpenFile();
	//constructor
	public AddData()
	{
		//initialise QueryCode
		QueryCode = "";
	}

	//AddUser - method
	//inputed is the user name, password, and true name
	//also inputed is the email although this is not required to add a user
	//to the system
	//outputed is either an error message or a confirmation message that
	//the user was entered into the system
	//the inputed email can be either a space or null
	public String AddUser (String usrname, String password, String email, String truename)
	{
		String output = "";
		boolean isValid = true;
		//the data cannot be null, empty or a space
		if (usrname == null && password == null && truename == null)
		{
			output = "User Data is empty";
			isValid = false;
		}
		else if (usrname == "" && password == "" && truename == "")
		{
			output = "User Data is empty";
			isValid = false;
		}
		else if (usrname == " " && password == " " && truename == " ")
		{
			output = "User Data is empty";
			isValid = false;
		}

		//check if the data is valid
		if (isValid == true)
		{
			//add data to the db
			QueryCode = "INSERT INTO tblUser (Username, Password, email, TrueName) VALUES ('"+usrname+"', '"+password+"', '"+email+"', '"+truename+"' );";
			DBLink.Execute(QueryCode);
			QueryCode = "";
			output = "User has been added into the database.";
		}
		else
		{
			output = "User Data is Invalid";
		}
		return output;
	}


	//AddSite - Method
	//inputed is the sitename, sitelocation and surveydate and also UserID
	//the same site can be done on different days ie different siteID
	//outputed is a error message or confirmation of adding to the db.
	//It checks if the data is empty or not 
	//and then adds it to the database if the data is valid
	//it also updates the siteID Field
	public String AddSite (int UserID, String SiteName, String SiteLocation)
	{
		String output = "";
		//check that none of the data inputed is empty
		SiteIsValid = true;
		//the data cannot be null, empty or a space
		if (SiteName == null && SiteLocation == null )
		{
			output = "Site Data is empty";
			SiteIsValid = false;
		}
		else if (SiteName == "" && SiteLocation == "" )
		{
			output = "Site Data is empty";
			SiteIsValid = false;
		}
		else if (SiteName == " " && SiteLocation == " " )
		{
			output = "Site Data is empty";
			SiteIsValid = false;
		}


		//check if the data is valid
		if (SiteIsValid == true)
		{
			//add data to db.
			QueryCode = "INSERT INTO tblSite (SiteName, SiteLocation, UserID) VALUES ('"+SiteName+"', '"+SiteLocation+"', "+UserID+");";
			DBLink.Execute(QueryCode);
			QueryCode = "";
			output = "Site has been added into the database.";
		}

		else
		{
			output = "Site Data Is Invalid.\nNo Data added to the database.";
		}


		return output;
	}

	//AddEcology - Method
	//Inputed is the amount of phosphates, sulphates, nitrates, plant amounts, animal amounts
	//It is all Strings because the type is checked within the method
	//Also inputted is the SurveyId and USerID which are found/given from the main class
	//and also the boolean for the checkbox
	//outputed is either an error message or a confirmation message that the data was
	//added to the db
	//the method check firstly if the data inserted is null or contains whitespaces
	//the method checks if the inserted variables are valid types to that of the data base
	public String AddEcology (int UserID,  String SurveyId, boolean SurveyIsSelected, String phosphates, String sulphates, String nitrates, String PlantAmnt, String AnimalAmnt)
	{
		String output = "";

		//ask user for site ID because the site is added seperately
		SiteID = Integer.parseInt(JOptionPane.showInputDialog("Enter The Site ID for the data to be \ninputted into the system, \nfound in data tasks"));

		String AddEcologyQuery = "";
		boolean isValid = true;
		//check to see if the inputed data IS ALL null or one whitespace
		if (phosphates == null && sulphates == null && nitrates == null && PlantAmnt == null && AnimalAmnt == null)
		{
			output = "Data is empty";
			isValid = false;
		}
		else if (phosphates == " " && sulphates == " " && nitrates == " " && PlantAmnt == " " && AnimalAmnt == " ")
		{
			output = "Data is empty";
			isValid = false;
		}
		else if (phosphates == "" && sulphates == "" && nitrates == "" && PlantAmnt == "" && AnimalAmnt == "")
		{
			output = "Data is empty";
			isValid = false;
		}
		//run the method if not empty input
		else
		{
			//phosphate, sulphate and nitrate have to be double
			//plantamnt and animalamnt have to be integers
			//use the isDouble method
			if (isDouble(phosphates) == true && isDouble(sulphates) == true 
					&& isDouble(nitrates) == true && isInteger(PlantAmnt) == true 
					&& isInteger(AnimalAmnt) == true)
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
				output = "Data is not valid";
			}
		}
		//call + instantiate variables used to typecast Strings
		double Dphosphates = 0.00;
		double Dsulphates = 0.00;
		double Dnitrates = 0.00;
		int IPlantAmnt = 0;
		int IAnimalAmnt = 0;
		//Data Validation
		//check if data is currently valid and then check if it meets the rules
		//All values must not be negative ie greater than 0
		if (isValid == true)
		{
			//convert Strings to their respective variable type
			Dphosphates = Double.parseDouble(phosphates);
			Dsulphates = Double.parseDouble(sulphates);
			Dnitrates = Double.parseDouble(nitrates);
			IPlantAmnt = Integer.parseInt(PlantAmnt);
			IAnimalAmnt = Integer.parseInt(AnimalAmnt);

			if (Dphosphates >= 0 && Dsulphates >= 0 && Dnitrates >= 0
					&& IPlantAmnt >= 0 && IAnimalAmnt >= 0)
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
				output = "Data is invalid";
			}
		}

		// have to check if the data is valid before continuing
		if(isValid == true)
		{
			//begin to create the insert query
			AddEcologyQuery += "INSERT INTO tblEcology (SiteID, ";


			//check if the SiteID and SurveyID are valid and in the db
			//if survey has been selected in addData frame
			QueryCode = "SELECT SiteID FROM tblSite";
			//firstly get a list of all the site IDS
			String Site = DBLink.DisplayWithoutHeadings(QueryCode);
			QueryCode = "";

			//get a list of survey IDS


			//check if the site id is valid if not return an error 
			if(Site.contains(""+SiteID))
			{
				if (SurveyIsSelected == true)
				{
					//now check if the survey ID is valid
					//if not then it must run a similar algorithm to the main class


					QueryCode = "SELECT SurveyID FROM tblSurvey";
					//get a list of all survey ids
					String Survey = DBLink.DisplayWithoutHeadings(QueryCode);
					QueryCode = "";
					if (!Survey.contains(""+SurveyId))
					{
						JOptionPane.showMessageDialog (null, "The Survey ID you selected was not found in the Database.\n" +
								"You will now have to add the survey to the database now.", "Incorrect Survey ID", JOptionPane.INFORMATION_MESSAGE);
						//create frame and jfilechooser
						JFrame ChooseFileFrame = new JFrame();
						JFileChooser AddSurveyJFileChooser2 = new JFileChooser();
						AddSurveyJFileChooser2.showOpenDialog(ChooseFileFrame);
						File SurveyPath = AddSurveyJFileChooser2.getSelectedFile();

						//code from main class =
						//open the open dialogue which will open a survey file

						//use the OpenFile class to open the survey and put it on the MainTExtArea
						//if there is no error display the survey in maintextarea
						String SurveyP = openfile.OpenSurvey(SurveyPath);
						if (!SurveyP.contains("Error"))
						{
							output+="Survey has been loaded and path saved to database.\n\nSurvey:\n\n"+Survey;
							//will add to maintextarea so user can chooser a site to use
							QueryCode = "SELECT SiteID, SiteName, SiteLocation FROM tblSite";
							String SiteList = DBLink.Display(QueryCode);
							QueryCode = "";
							output+="\n\nSite List to Use:\n"+SiteList;
							//use the DBCode class to update databse the survey table
							int surveySiteID = Integer.parseInt(JOptionPane.showInputDialog("Enter the SiteID for the survey you would like to add to the system"));
							QueryCode = "INSERT INTO tblSurvey (TextFileName, UserID, SiteID) VALUES ('"+SurveyPath+"', "+UserID+", "+surveySiteID+")";//'dd',1);";
							DBLink.Execute(QueryCode);
							QueryCode = "";
							JOptionPane.showMessageDialog (null, "Survey was Added to the Database");
							QueryCode = "SELECT SurveyID FROM tblSurvey  WHERE TextFileName like '"+SurveyPath.getName()+"'";
							System.out.println("surveyName: "+SurveyPath.getName());
							System.out.println("Survey ID: "+DBLink.Execute(QueryCode));
							//there should be no problem parseing the int because the surveyID has been created before
							//it is needed later on.
							SurveyId = DBLink.Execute(QueryCode);
							QueryCode = "";
							//this will include the survey ID in the insert query
							AddEcologyQuery += "SurveyID, Phosphates, Sulphates, Nitrates, PlantAmnt, AnimalAmnt) VALUES (";
							//add the siteID and survey ID here
							AddEcologyQuery += " "+SiteID+", "+SurveyId;

						}
						else
						{
							JOptionPane.showMessageDialog(null, "The survey file which was selected is invalid" +
									"\nPlease select a valid survey file.","Survey File is Invalid",JOptionPane.ERROR_MESSAGE);
						}
						//= end of that code
					}
				}
				else 
				{

					//when survey id is 0 it is not included in the db
					//SurveyId = 0;
					//this will not include the survey ID in the insert query
					AddEcologyQuery += "Phosphates, Sulphates, Nitrates, PlantAmnt, AnimalAmnt) VALUES (";
					//add the siteID here
					AddEcologyQuery += " "+SiteID;
				}

			}
			else
			{
				//return error
				output = "Invalid Site ID";
				isValid = false;
			}

			//ADD DATA IF data is VALID
			//use the DBCode class, execute method (called in the fields) to execute SQL to add the ecology data to db
			//add the rest of the data to AddEcologyQuery
			AddEcologyQuery += ", "+Dphosphates+", "+Dsulphates+", "+Dnitrates+", "+IPlantAmnt+", "+IAnimalAmnt+" );";
			DBLink.Execute(AddEcologyQuery);	
			output = "Ecology Data has been added to the database.";



		}
		//if data is not valid than it is invalid or empty
		//else
		//{
		//	if (!output.contains("empty"))
			//{
			//	output = "Ecology data is invalid";
			//}



	//	}

		return output;
	}

	////AddClimate - Method
	//Inputed is the amount of rainfall, wind speed, air pressure and the percentage of cloud cover of an area.
	//It is all Strings because the type is checked within the method
	//Also inputted is the SiteID
	//it must be found in the main method
	//outputed is either an error message or a confirmation message that the data was
	//added to the db
	//the method check firstly if the data inserted is null or contains whitespaces
	//the method checks if the inserted variables are valid types to that of the data base
	public String AddClimate (String RainfallAmnt, String WindSpd, String AirPressure, String PerCentCloudCover)
	{
		String output = "Climate Data is functioning so far...";
		//ask user for site ID because the site is added seperately
		SiteID = Integer.parseInt(JOptionPane.showInputDialog("Enter The Site ID for the data to be \ninputted into the system, \nfound in data tasks"));

		String AddClimateQuery = "";
		boolean isValid = true;
		//check to see if the inputed data IS ALL null or one whitespace
		if (RainfallAmnt == null && WindSpd == null && AirPressure == null && PerCentCloudCover == null )
		{
			output = "Data is empty";
			isValid = false;
		}
		else if (RainfallAmnt == "" && WindSpd == "" && AirPressure == "" && PerCentCloudCover == "" )
		{
			output = "Data is empty";
			isValid = false;
		}
		else if (RainfallAmnt == " " && WindSpd == " " && AirPressure == " " && PerCentCloudCover == " ")
		{
			output = "Data is empty";
			isValid = false;


		}
		//run the method if not empty input
		//All variables except siteID have to be doubles
		//SiteID is an int
		//use the isDouble method
		else if (isDouble(RainfallAmnt) == false || isDouble(WindSpd) == false 
				|| isDouble(AirPressure) == false || isDouble(PerCentCloudCover) == false )
			//|| isInteger(""+SiteID) == false)
		{
			isValid = false;
			output = "Data is not valid \nThe Variables are of the incorrect type.";
		}


		//variables used to typecast if the data is valid

		double DRainfallAmnt = 0.00;
		double DWindSpd = 0.00;
		double DAirPressure = 0.00;
		double DPerCentCloudCover = 0.00;

		//data validation
		//only change variables if the data is currently valid. ie typecast into new variable
		if (isValid == true)
		{
			DRainfallAmnt = Double.parseDouble(RainfallAmnt);
			DWindSpd = Double.parseDouble(WindSpd);
			DAirPressure = Double.parseDouble(AirPressure);
			DPerCentCloudCover = Double.parseDouble(PerCentCloudCover);
			//check if the percentage of cloud cover is a percentage ie 0. and not above zero or negative
			//Percentage of cloud cover is done is quarters
			//therefore it must be divisble by 4
			//if (DPerCentCloudCover>=0 && DPerCentCloudCover<=100 && DPerCentCloudCover % 4 == 0)
			//{
			//isValid = true;
			//}
			//else
			//{
			//	isValid = false;
			//	output = "Data is not valid\n" +
			//			"Percentage of Cloud cover is invalid.";
			//}
			//check if the wind speed is below 50kts and not negative. Unlikely to have a higher wind spd than 50kts
			//50kts is extremely strong hurricane winds (50kts = 500 km/h approx.)
			if (DWindSpd >= 0 && DWindSpd <= 50)
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
				output = "Data is not valid\n" +
						"Wind Speed is invalid.";
			}
			//check to make sure airpressure is not negative. It could theoretically be 0
			//but it is usually about 1002 hPa at normal Pr.
			if(DAirPressure > 0)
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
				output = "Data is not valid\n" +
						"Air Pressure is invalid";
			}
			//check rainfall amount is above 0 ie not negative
			if (DRainfallAmnt > 0)
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
				output = "Data is not valid\n" +
						"Rainfall Amount is invalid";
			}
		}

		//check to see if the site ID is in the db else return an error
		//should always work since the site is added to the db every time
		QueryCode = "SELECT SiteID FROM tblSite";
		//firstly get a list of all the site IDS
		String Site = DBLink.DisplayWithoutHeadings(QueryCode);
		QueryCode = "";
		//int test = Integer.parseInt(""+SiteID);
		if (Site.contains(""+SiteID))
		{
			System.out.println("Isvalid: "+isValid+"\nOutput: "+output+"\nsiteID"+SiteID);
			//also check if the data is valid
			if (isValid == true)
			{
				//add data to the database
				AddClimateQuery = "INSERT INTO tblClimate (SiteID, [RainfallAmnt(mm)], " +
						"[WindSpeed(kts)], [AirPressure(hPa)], PercentageCloudCover) " +
						"VALUES ( "+SiteID+", "
						+RainfallAmnt+", "+WindSpd+", "+AirPressure+", "+PerCentCloudCover+")";
				DBLink.Execute(AddClimateQuery);
				output = "Climate data has been added to the database.";
			}
			//if (!output.contains("empty"))
			//{
			//	output = "Climate data is invalid";
			//}


		}
		else
		{
			output = "Invalid Site ID";
		}



		return output;
	}

	////AddGermorph - Method
	//Inputed is the amount of Nitrates in the soil, Bacteria, pH, River Velocity, The River depth and the river width.
	//It is all Strings because the type is checked within the method
	//Also inputted is the SiteID
	//it must be found in the main method
	//outputed is either an error message or a confirmation message that the data was
	//added to the db
	//the method check firstly if the data inserted is null or contains whitespaces
	//the method checks if the inserted variables are valid types to that of the database
	public String AddGermorph (String nitrates, String bacteria, String pH, String RiverVelocity, String RiverDepth, String RiverWidth)
	{
		String output = "";
		//ask user for site ID because the site is added seperately
		SiteID = Integer.parseInt(JOptionPane.showInputDialog("Enter The Site ID for the data to be \ninputted into the system, \nfound in data tasks"));

		String AddGermorphQuery = "";
		boolean isValid = true;
		//check to see if the inputed data IS ALL null or one whitespace
		if (nitrates == null && bacteria == null && pH == null && RiverVelocity == null && RiverDepth == null && RiverWidth == null )
		{
			output = "Data is empty";
			isValid = false;

		}
		else if (nitrates == "" && bacteria == "" && pH == "" && RiverVelocity == "" && RiverDepth == "" && RiverWidth == "" )
		{
			output = "Data is empty";
			isValid = false;
		}
		else if (nitrates == " " && bacteria == " " && pH == " " && RiverVelocity == " " && RiverDepth == " " && RiverWidth == " " )	
		{
			output = "Data is empty";
			isValid = false;
		}
		//run the method if not empty input
		else
		{
			//All variables except siteID have to be doubles
			//SiteID is an int
			//use the isDouble method
			if (isDouble(nitrates) == true && isDouble(bacteria) == true 
					&& isDouble(pH) == true && isDouble(RiverVelocity) == true 
					&& isDouble(RiverVelocity) == true && isDouble(RiverDepth) == true
					&& isDouble(RiverWidth) ==true )
			{
				//isValid = true;

			}
		}
		//Data Validation
		//variables used to typecast if the data is valid
		double Dnitrates = 0.00;
		double Dbacteria = 0.00;
		double DpH = 0.00;
		double DRiverVelocity = 0.00;
		double DRiverDepth = 0.00;
		double DRiverWidth = 0.00;
		if (isValid == true )
		{
			//convert variables in respective types ie typecast
			//the D denotes that it is a double type
			Dnitrates = Double.parseDouble(nitrates);
			Dbacteria = Double.parseDouble(bacteria);
			DpH = Double.parseDouble(pH);
			DRiverVelocity = Double.parseDouble(RiverVelocity);
			DRiverDepth = Double.parseDouble(RiverDepth);
			DRiverWidth = Double.parseDouble(RiverWidth);
			//check to see if the pH is correct
			//ie between 0 and 14
			//pH runs from 1 to 14 but a decimal is theoretically possible
			if (DpH <= 14.00 && DpH >= 0.00 )
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
			}
			//make sure none of the other variables are not smaller than 0 ie negative
			if(Dnitrates >= 0 && Dbacteria >= 0 && DpH >= 0 && DRiverVelocity >= 0 && DRiverDepth >= 0 && DRiverDepth >= 0)
			{
				//isValid = true;
			}
			else
			{
				isValid = false;
			}
		}

		//check to see if the site ID is in the db else return an error
		//should always work since the site is added to the db every time
		QueryCode = "SELECT SiteID FROM tblSite";
		//firstly get a list of all the site IDS
		String Site = DBLink.DisplayWithoutHeadings(QueryCode);
		QueryCode = "";
		if (isValid == true)
		{
			if (Site.contains(""+SiteID))
			{


				//add data to the database
				AddGermorphQuery = "INSERT INTO tblGermorph (SiteID, Nitrates, Bacteria, pH, [RiverVelocity(M/S)], [RiverDepth(M)], [RiverWidth(M)]) VALUES ("
						+SiteID+", "+Dnitrates+", "+Dbacteria+", "+DpH+", "+DRiverVelocity+", "+DRiverDepth+", "
						+DRiverWidth+" );" ;
				DBLink.Execute(AddGermorphQuery);	
				output = "Germorphological data has been added to the database.";
			}
			//if (!output.contains("empty"))
			//{
			//	output = "Germorphological data is invalid";
			//}
		}
		else
		{
			output = "Invalid Site ID";
		}




		return output;
	}


	//These methods determine the type of variable of a given string
	//inputed into every method of this purpose is the input string
	//outputted is the boolean if the variable is that type ie true or otherwise false
	//each of these methods are private since they will only be accessed from
	//within the class
	//the name of each class is self explanatory so this is the summary for the
	//following methods

	private boolean	isInteger (String str)
	{
		boolean isInt = true;
		//check if the string is not null
		//more for redundant checking since it will already be checked
		if (str == null)
		{
			isInt = false;
			return isInt;
		}
		//check if string is empty but instanciated
		if (str.length() == 0)
		{
			isInt = false;
			return isInt;
		}
		//check if the first character is - which means negative than the Int is valid
		//start making the count for the for loop

		//int count = 0;
		//if(str.charAt(0) == '-')
		//{
		//if the length of the string is only 1 place than it is a string and not a number
		//	if (str.length() == 1)
		//	{
		//		isInt = false;
		//	}
		//if the string has a - and is still valid as an int than the count for the loop
		//must be 1 because the - must be skipped by the loop
		//	count=1;
		//}
		//this loop will run the length of the string
		//	for (;count < str.length(); count++)

		//revised if stmnt
		if(str.charAt(0) == '-' && str.length() == 1)
		{
			isInt = false;
			System.out.println("STR:"+str+"\nreturn: "+isInt+"\n");
			return isInt;
		}

		//referenece code Derrick Chalom
		int count = (str.charAt(0)=='-')?1:0;

		//the method returns a value if the str is 1 char and it is -

		//int count = 0;
		//	for (;count < str.length(); count++)
		while (count < str.length() && isInt == true)
		{
			//a character will be called to be the place of that count of the string
			char c = str.charAt(count);
			//check to see if the character is a digit
			//than the Int is true else it is false
			//System.out.println("int char: "+c);
			if (!Character.isDigit(c))
			{
				isInt = false;
				return isInt;
				//System.out.println("int false");
			}
			count++;
		}
		//System.out.println("return: "+isInt+"/n");
		return isInt;
	}

	private boolean	isDouble (String str)
	{
		boolean isDouble = true;
		String C = "";

		try {
			//check if the string is not null
			//more for redundant checking since it will already be checked
			if (str == null)
			{
				isDouble = false;
				return isDouble;
			}
			//check if string is empty but instanciated

			//if (str.length() == 0)
			//{
			//	isDouble = false;
			//}
			//check if the first character is - which means negative than the double is valid
			//start making the count for the for loop
			//int count = 0;
			//if(str.charAt(0) == '-')
			//{
			///	//if the length of the string is only 1 place than it is a string and not a number
			//	if (str.length() == 1)
			//	{
			//		isDouble = false;
			//	}
			//if the string has a - and is still valid as an double than the count for the loop
			//must be 1 because the - must be skipped by the loop
			//	count=1;
			//}
			//this loop will run the length of the string
			//System.out.println("\nreturn before loop: "+isDouble+"\n");

			//revised if stmnt
			if(str.charAt(0) == '-' && str.length() == 1)
			{
				isDouble = false;
				System.out.println("STR:"+str+"\nreturn: "+isDouble+"\n");
				return isDouble;
			}

			//referenece code Derrick Chalom
			int count = (str.charAt(0)=='-')?1:0;

			//the method returns a value if the str is 1 char and it is -

			//int count = 0;
			//	for (;count < str.length(); count++)
			while (count < str.length() && isDouble == true)
			{
				//a character will be called to be the place of that count of the string
				char c = str.charAt(count);
				C+=c;
				//check to see if the character is a digit
				//System.out.println("double char: "+c);
				//System.out.println("isDigit Return: "+Character.isDigit(c));
				if (!Character.isDigit(c) && c != '.' && c != '/')
				{
					isDouble = false;
					return isDouble;
					//System.out.println("not digit");
				}
				//also check for characters allowed in a double
				//Standard computer data handling means that ',' is not used
				//as a seperator unless specific geographical data is set in the OS
				//for the purposes of this project that will not be assumed in this method
				count++;


			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//there is a problem
			JOptionPane.showMessageDialog(null, "There Has been an unknown error\n"+"\nError trace"+e);
		}

		System.out.println("STR:"+str+"\nreturn: "+isDouble+"\n");

		return isDouble;
	}


}
