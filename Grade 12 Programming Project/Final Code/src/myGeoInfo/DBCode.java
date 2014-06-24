package myGeoInfo;

import java.io.File;
/**
 * This code is partly my own and also found 
 * from the Grade 11 Text Book
 * and
 * http://www.exampledepot.com/egs/java.sql/UpdateData.html
 */
//import repositories
import java.sql.*;

public class DBCode {
	//Fields
	//Such as the error messages output
	private String errorMessages;
	Connection conn;
	//Constructor 
	//Sets up a connection to the database
	public DBCode()
	{
		//initialise errorMessages
		errorMessages ="";
		//set up connection to database
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException e)
		{
			//System.err.println ("Sun Databse Driver Error | Code 1");
			//JOptionPane.showMessageDialog(null, "Sun Databse Driver Error | Code 1","Error", JOptionPane.ERROR_MESSAGE);
			errorMessages += "Sun Databse Driver Error | Code 1\n";
		}
		try {
			//conn = DriverManager.getConnection("jdbc:odbc:DRIVER="+"{Microsoft Access Driver (*.mdb, *.accdb)};DBQ = GeoInfoAccess.mdb");
			conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=GeoInfoAccess.mdb");
			//System.out.println("The Database is now working | Code 0");
			//JOptionPane.showMessageDialog(null,"The Database is now working | Code 0");
			//errorMessages += "The Database is now working | Code 0\n";
		} catch (SQLException e) {
			//System.err.println("Database Connection Failed | Code 2 ");
			//JOptionPane.showMessageDialog(null, "Database Connection Failed | Code 2 ","Error", JOptionPane.ERROR_MESSAGE);

			errorMessages += "Database Connection Failed | Code 2\n";

		}

	}
	
	//spacing algorithm
	//this method creates the spacing rather than doing it within
	//the Display method
	//it is a private method, it is not needed to be accessed externally
	//inputed is the data to add spaces and the width ie the amount of spaces
	//outputted is the edited digest
	
	private String spaces(String input, int width)
	{
        String output=input;
        for (int i=input.length();i<width;i++){
            output+=" ";
        }
        return output;
    }
   
	//linespacing
	//line method
	//this method deals with the spacing of the underline part of the output from this class
	//it is a private method, it is not needed to be accessed externally
	//inputed is the data to add spaces and the width ie the amount of spaces
	//outputted is the edited digest
	private String line(String input, int width)
    {
        String output="";
        for (int i=0;i<input.length();i++){
            output+="-";
        }
        for (int i=input.length();i<width;i++){
            output+=" ";
        }
        return output;
    }
	
	//Display method
	//The method recieves the inputed query from the main class
	//the method outputs the exectued SQL from the database
	//This method aims to use the connection to the database to execute SQL code in order to get
	//information from the database and send it to the application for use
	public String Display(String QueryCode)
	{
		String output = "";

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QueryCode);
			ResultSetMetaData meta = rs.getMetaData();
			int size = meta.getColumnCount();

			//headings
			String underline ="";
			for (int i =1; i<= size; i++)
			{
				String heading = meta.getColumnName(i);
				//reprogrammed so that the output makes use of both private methods
				output += spaces(heading, heading.length()+15);
				underline += line(heading, heading.length()+15);
				
				//output = output + heading + "\t";
				//if the heading is more than 12 charcters ie one tab space
				//the underline will also have to be changed below
			//	if (meta.getColumnName(i).length()>10) 
			//	{
					//the tabulated size is too large so 4 spaces are added
					//output +="    ";
			//	}
			//	for (int j = 0; j<heading.length();j++)
			//	{
					//there is a problem with truetype font sets
					//the spacing of some characters is smaller than others
					//there is no truely difinitive way of solving this problem
					
			//		underline +="-";
			//	}
				
				
			//	underline +="\t";
				//if (meta.getColumnName(i).length()>10) 
				//{
					//the tabulated size is too large so 4 spaces are added
				//	underline +="    ";
				//}
			}
			
			output = output + "\n"+underline+"\n";
			
			//content?
			while (rs.next())
			{
				for (int i =1; i <=size; i++)
				{
					Object value = rs.getObject(i);
					String heading = meta.getColumnName(i);
					output += spaces(""+value, heading.length()+15);
					
					//	output = output + value + "\t";
					//if (meta.getColumnName(i).length()>10) 
					//{
						//the tabulated size is too large so 4 spaces are added
				//		output +="    ";
					//}
				}
				output = output + "\n";
			}

		} catch (SQLException e) {
			output = "SQL Exception, There is an error in the SQL Statement | Code 3 \n"+e;
			//JOptionPane.showMessageDialog(null, "SQL Exception, There is an error in the SQL Statement | Code 3\n"+e,"Error", JOptionPane.ERROR_MESSAGE);
			errorMessages += "SQL Exception, There is an error in the SQL Statement | Code 3\n";
		}



		return output;
	}
	
		//DisplaySurvey method
		//The method recieves the inputed query from the main class
		//the method outputs the exectued SQL from the database
		//This method aims to use the connection to the database to execute SQL code in order to get
		//information from the database and send it to the application for use
		//it also has extra programming to ensure only the survey file name is displayed and not its whole path 
		public String DisplaySurvey(String QueryCode)
		{
			String output = "";

			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(QueryCode);
				ResultSetMetaData meta = rs.getMetaData();
				int size = meta.getColumnCount();

				//headings
				String underline ="";
				for (int i =1; i<= size; i++)
				{
					String heading = meta.getColumnName(i);
					//reprogrammed so that the output makes use of both private methods
					output += spaces(heading, heading.length()+15);
					underline += line(heading, heading.length()+15);
					
				
				}
				
				output = output + "\n"+underline+"\n";
				
				//content
				while (rs.next())
				{
					for (int i =1; i <=size; i++)
					{
						Object value = rs.getObject(i);
						String heading = meta.getColumnName(i);
						//it must check which datra from which heading is being called up
						//if it is TextFileName then a method of the File repository must be used 
						//to only display the filename in the output
						//else it must just have the standard display algorith ie an if-statement
									
						if (heading.equalsIgnoreCase("TextFileName"))
						{
							File SurveyPath = new File(""+value);
							//reference .getName() code
							//http://www.gamedev.net/topic/448658-extracting-filename-from-filepath-in-java/
							output += spaces(""+SurveyPath.getName(), heading.length()+15);
						}
						else
						{
							output += spaces(""+value, heading.length()+15);
						}
						
						
						
					}
					output = output + "\n";
				}

			} catch (SQLException e) {
				output = "SQL Exception, There is an error in the SQL Statement | Code 3 \n"+e;
				//JOptionPane.showMessageDialog(null, "SQL Exception, There is an error in the SQL Statement | Code 3\n"+e,"Error", JOptionPane.ERROR_MESSAGE);
				errorMessages += "SQL Exception, There is an error in the SQL Statement | Code 3\n";
			}



			return output;
		}

	//Display Method without headings
	//It is the same as the one above but without headings
	//The method recieves the inputed query from the main class
	//the method outputs the exectued SQL from the database
	//This method aims to use the connection to the database to execute SQL code in order to get
	//information from the database and send it to the application for us
	//THIS IS USED MAINLY FOR the login username and password
	public String DisplayWithoutHeadings(String QueryCode)
	{
		String output = "";

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QueryCode);
			ResultSetMetaData meta = rs.getMetaData();
			int size = meta.getColumnCount();

			//content?
			while (rs.next())
			{
				for (int i =1; i <=size; i++)
				{
					Object value = rs.getObject(i);
					//remove spacing
					//the alogrithm in the main cloass accounts for awkward spacing 
					//but rather remove the extra spacing here.
					//output = output + value + "\t";
					output = output + value;
				}
				output = output + "\n";
			}

		} catch (SQLException e) {
			output = "SQL Exception, There is an error in the SQL Statement | Code 3 \n"+e;
			//JOptionPane.showMessageDialog(null, "SQL Exception, There is an error in the SQL Statement | Code 3\n"+e,"Error", JOptionPane.ERROR_MESSAGE);
			errorMessages += "SQL Exception, There is an error in the SQL Statement | Code 3\n";
		}



		return output;
	}

	//Code example found at: 
	//http://www.exampledepot.com/egs/java.sql/UpdateData.html
	//http://www.daniweb.com/software-development/java/threads/196549/java-sql-update-syntax
	//Execute Method
	//The method receives the inputed query from the main class
	//the method will return if there is an error or if the query executed correctly
	//It will also return if the SQL code is executed
	//This method aims to use the connection to the database to execute SQL code in order to add
	//information to the database 
	public String Execute(String QueryCode)
	{
		String out = "";

		try {
			Statement stmt = conn.createStatement();
			// Execute the insert statement
			//I was going to use an updateCount to count the update but
			//decided it was messy
			//int updateCount = stmt.executeUpdate(QueryCode);
			//updateCount contains the number of updated rows
			//later on in the main program it will determine if the 
			//SQL has been executed and will display an appropriate message
			//out = "SQL Executed, UpdateCount: "+updateCount;
			//stmt.executeQuery(QueryCode);
			stmt.executeUpdate(QueryCode);
			out = "SQL Executed";
		} catch (SQLException e) {
			//output = "SQL Exception, There is an error in the SQL Statement | Code 3 \n"+e;
			//JOptionPane.showMessageDialog(null, "SQL Exception, There is an error in the SQL Statement | Code 3\n"+e,"Error", JOptionPane.ERROR_MESSAGE);
			errorMessages += "SQL Exception, There is an error in the SQL Statement | Code 3\n";
			out = "SQL Exception, There is an error in the SQL Statement | Code 3\n";
		}


		return out;

	}
	//Getters And Setters
	public String getErrorMessages() {
		return errorMessages;
	}

}
