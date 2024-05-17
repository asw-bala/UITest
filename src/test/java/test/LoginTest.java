package test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utilities.ConfigReader;
import utilities.JSONReader;

public class LoginTest {
	public WebDriver driver;
	private ConfigReader configReader;
	public String url;
	public Properties prop;

	@BeforeSuite
	public void getProperty() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
	}

	
	@BeforeTest
	public void launchBrowser() {
		String url = prop.getProperty("login_url");
		System.out.println(url);
		driver = new ChromeDriver();
		driver.get(url);
	}

	@Test
	public void LoginFuncTest() throws FileNotFoundException, InterruptedException {
		getJsonDataLogin();
		
	}
	
	@SuppressWarnings("unchecked")
	public void getJsonDataLogin() throws FileNotFoundException, InterruptedException {

		String filePath = prop.getProperty("LoginJsonDataPath");
		JSONReader jsonObj = new JSONReader();
		JSONArray list = jsonObj.jsonReaderData(filePath);
		// System.out.println(list);
		for (int i = 0; i < list.size(); i++) {
			JSONObject loginDetails = (JSONObject) list.get(i);
			// json object
			JSONObject user = (JSONObject) loginDetails.get("login");
			String username = (String) user.get("username");
			String password = (String) user.get("password");

			String result = login(username, password);
			//To write the result into the JSON file
			user.put("result", result);
			try (FileWriter file = new FileWriter(filePath)) {

				file.append(list.toJSONString());
				file.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}



		}
	}

	public String login(String username, String password) throws InterruptedException {		
		driver.findElement(By.id("id_username")).sendKeys(username);
		driver.findElement(By.id("id_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		String title = driver.getTitle();
		if (title.equals("NumpyNinja"))
		{	
			driver.findElement(By.linkText("Sign out")).click();
			driver.findElement(By.linkText("Sign in")).click();
			return "PASS";
		}	
			
		else
			return "FAIL"; 
		
	}

}
