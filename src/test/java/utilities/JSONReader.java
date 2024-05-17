package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {

	public JSONArray jsonReaderData(String filePath) throws FileNotFoundException
	{
		
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filePath);
		//Read JSON file
		Object obj = null;
		try {
			obj = jsonParser.parse(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray List = (JSONArray) obj;
		//System.out.println("Entire Json File "+List); //This prints the entire json file
		return List;
        

	}
	
	
		
}	
