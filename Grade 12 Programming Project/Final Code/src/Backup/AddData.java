package myGeoInfo;

public class AddData {
	//class outline
	//this may not be completely representative of the actual programming
	//see the comments directly before each method for more
	//Accurate information

	//This class adds the data from the input from the main class
	//to the data base
	//It is also reponsiable for the bulk of the data
	//validation in this project
	//It makes sure the data entered is valid and the right variable type
	//it will return an error if it is not
	//It will then send the data to the database
	//If there is a survey it will ask the user to identify it in the database
	//if it is not there than a similar algorithm to the on to add the survey to the system
	//will run
	//This is done with some extra methods in this class which return specific Strings
	//each table of data is handled by a seperate class since not every table has to be added
	//there will be a parmaterised consrtuctor
	//which will accept the inputed data

	//AddUser - method
	//inputted is the user name, password, and true name
	//also inputed is the email although this is not required to add a user
	//to the system
	//outputted is either an error message or a confirmation message that
	//the user was entered into the system
	//the inputed email can be either a space or null
	public String AddUser (String usrname, String password, String email, String truename)
	{
		String output = "";


		return output;
	}

	//AddSite - Method
	//must check if site already exists
	public String AddSite (String SiteName, String SiteLocation, String SurveyDate)
	{
		String output = "";


		return output;
	}

	//
	public String AddEcology (double phosphates, double sulphates, double nitrates, int PlantAmnt, int AnimalAmnt)
	{
		String output = "";


		return output;
	}

	//
	public String AddClimate (double RainfallAmnt, double WindSpd, double AirPressure, double PerCentCloudCover)
	{
		String output = "";


		return output;
	}

	//
	public String AddGermorph (double nitrates, double bacteria, double pH, double RiverVelocity, double RiverDepth, double RiverWidth)
	{
		String output = "";


		return output;
	}




}
