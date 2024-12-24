package pageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import utils.DbConnection;
import utils.ExcelReaderCode;
import utils.RecipeDetailsDBUtil;

public class RecipeAtoD  extends TestBase{

	List<String> LFV_EliminateItemList=new ArrayList<String>();
	List<String> LFV_AddItemList=new ArrayList<String>();
	
	List<String> LCHF_EliminateItemList = new ArrayList<String>();	
	List<String> LCHF_AddItemList = new ArrayList<String>();
	
	List<String> cuisineDataList = new ArrayList<String>();
	String rec_Category;
	String food_Category;
	String ingredient_List = "";
	
	
	public RecipeAtoD(WebDriver driver) 
	{
		PageFactory.initElements(driver,this);
	}
	
	public void click_AtoZ_recipes()
	{
		driver.findElement(By.xpath("//div[@id='toplinks']/a[text()='Recipe A To Z']")).click();
		System.out.println("A to Z is clicked..");
	}

	int pageCount=0;
	@SuppressWarnings("deprecation")
	public void getRecipeInfo() throws Exception {
				
		List<WebElement> menuAtoZWebElements=driver.findElements(By.xpath("//table[@class='mnualpha ctl00_cntleftpanel_mnuAlphabets_5 ctl00_cntleftpanel_mnuAlphabets_2']/tbody/tr/td[@onmouseover='Menu_HoverStatic(this)']//a[1]"));
					
		//Read Elimination data from excel and store it into arraylist
		this.read_LFV_Elimination_Excel();
		this.read_LFV_Add_Excel();
		System.out.println("LFV Add size "+LFV_AddItemList.size());
		
		this.read_LCHF_Elimination_Excel();
		this.read_LCHF_Add_Excel();
		System.out.println("LFV Add size "+LCHF_AddItemList.size());
		
		this.read_CuisineCategoryData_Excel();		
		
		
		Map<String, Object[]> recipes_LCHF_Elimination = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LFV_Elimination = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LCHF_Add = new TreeMap<String, Object[]>(); 
		
		Map<String, Object[]> recipes_LFV_Add = new TreeMap<String, Object[]>();
		
		int size=menuAtoZWebElements.size();
		
		int LCHFCounter = 1;
		
		int LFVCounter = 1;
		
		System.out.println("There are "+size+" number of links ordered alphabetically.");
		
		//Recipes from A to D
		for(int i=1; i<4; i++) 
		{
			click_AtoZ_recipes();
			WebElement AlphabetLink=driver.findElement(By.xpath("//table[@id='ctl00_cntleftpanel_mnuAlphabets']/tbody/tr/td[@id='ctl00_cntleftpanel_mnuAlphabetsn"+i+"']//a"));
			String alphabet=AlphabetLink.getText();
			System.out.println("----- Starts with alphabet : "+alphabet+"  ------------");
			AlphabetLink.click();
			
			List<WebElement> pages = driver.findElements(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a"));
			
			//Added all page links url to the arraylist (1,2,3...)
			ArrayList<String> pageLinks=new ArrayList<>(30);
			for(WebElement url: pages) 
			 {
				String pageLink=url.getAttribute("href");
				//System.out.println("page link "+pageLink);
				pageLinks.add(pageLink);
		     }
						 
			try 
			 {
				String s=driver.findElement(By.xpath("//div[@style='text-align:right;padding-bottom:15px;'][1]/a")).getText();					
				pageCount=Integer.parseInt(s);
				System.out.println("Toal page count is: "+pageCount);
			 }
			catch (Exception e)
			{
					System.out.println(e.getMessage());
			}
				
			
			System.out.println("Totla PageLinks "+pageLinks);	
			
			//Retrive pagelinks url from arraylist and navigate to load each recipe
			int recipePageCount = 1;
			for(String pageLink: pageLinks) 
			 {				
				System.out.println("PageLinks is "+pageLink+" page number is "+recipePageCount);	
				try 
				 {					
					driver.navigate().to(pageLink);
					System.out.println("******  Alphabet is "+alphabet+"  **** Current page is: "+recipePageCount+"  *********");
				
					recipePageCount = recipePageCount+1;
				 }				
				catch(StaleElementReferenceException e) 
				{	
					e.printStackTrace();
				}
							
				List<WebElement> recipes_url=driver.findElements(By.className("rcc_recipename"));
				int no_of_cards=recipes_url.size();
				
				//added all reciepe url to the arraylist
				ArrayList<String> links=new ArrayList<>(30);
				for(WebElement url: recipes_url) 
				 {
					String recipesLink=url.findElement(By.tagName("a")).getAttribute("href");
					links.add(recipesLink);
			     }
										
				 for(Object eachRecipe:links)
				  {
					try 
					 {																				
						String recipeURL="";
						String id="";
						String recipe_id="";
						String recipeName="";
						String prepTime="";
						String cookTime="";
						String Servings="";
						String noOfServings="";
						String tags="";
						String desc="";
						String method="";
						
						driver.navigate().to((String)eachRecipe);
						//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
						try {
						recipeURL = eachRecipe.toString();
						//System.out.println("Recipe URL : "+(String)eachRecipe);
						id = recipeURL.substring(recipeURL.lastIndexOf("-")+1);
						
						recipe_id = id.substring(0,id.length()-1);
						//System.out.println("Recipe ID : "+ recipe_id);
						} catch (Exception e) {
							
						}
						//recipe name
						try {
						recipeName = driver.findElement(By.xpath(("//span[@id='ctl00_cntrightpanel_lblRecipeName']"))).getText();
						System.out.println("Recipe Name : "+ recipeName);
						} catch (Exception e) {
							
						}
						// fetching recipe details
						
						//preparation time
						try {
						WebElement preptimeEle = driver.findElement(By.xpath("//time[@itemprop='prepTime']"));
						prepTime = preptimeEle.getText();
						} catch (Exception e) {									
						}
						
						//cooking time
						try {
						WebElement cookTimeEle = driver.findElement(By.xpath("//time[@itemprop='cookTime']"));
						cookTime = cookTimeEle.getText();
						} catch (Exception e) {									
						}
						
						//No of servings
						try {
						WebElement noOfServingsEle = driver.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblServes']"));
						Servings = noOfServingsEle.getText();
						noOfServings = Servings.substring(1, Servings.length());
						} catch (Exception e) {									
						}
						// fetching recipe tags
						try {
						WebElement tagEle=driver.findElement(By.xpath("//div[@id='recipe_tags']/a"));
						tags=tagEle.getText();
						} catch (Exception e) {									
						}
							
							//Recipe category
						
							rec_Category = "";
							if (tags.contains("breakfast")) {
								rec_Category = "Breakfast";
								break;
							} else if (tags.contains("dinner")) {
								rec_Category = "Dinner";
								break;
							} else if (tags.contains("snack")) {
								rec_Category = "Snacks";
								break;
							}
							else if (tags.contains("lunch")) {
								rec_Category = "Lunch";
								break;
							}
							else
							rec_Category = "Not mentioned";
							
							
							//Food Category
							food_Category = "";
							if ((tags.contains("veg")) && (!tags.contains("non veg")||!tags.contains("non-veg"))) {
								food_Category = "Vegetarian";
								break;
							} else if (tags.contains("non veg")||tags.contains("non-veg")) {
								food_Category = "Non-Vegetarian";
								break;
							} else if (tags.contains("egg")) {
								food_Category = "Eggitarian";
								break;
							} else if (tags.contains("jain")) {
								food_Category = "Jain";
								break;
							}else if (tags.contains("vegan")) {
								food_Category = "Vegan";
								break;
							}	
							else
							food_Category = "Not mentioned";
						
                             
							// fetching Cuisine category
							String cuisineCategory = "";
							for(String cuisine : cuisineDataList) {		
							if(tags.contains(cuisine)) {
								cuisineCategory = cuisine;
									break;
									
								} else {
									cuisineCategory = "not available";
								}									
							}							

							// fetching Recipe Description
							try {
							WebElement recDesEle = driver.findElement(By.xpath("//div[@id='recipe_details_left']/section/p/span"));
							desc=recDesEle.getText();
							} catch (Exception e) {									
							}
					
							// fetching Preparation method
							try {
							WebElement preMehodEle = driver.findElement(By.xpath("//div[@id='recipe_small_steps']/span"));
							method=preMehodEle.getText();
							} catch (Exception e) {									
							}
							
						   // fetching Nutrient values
							List<WebElement>  nutValueEle = driver.findElements(By.xpath("//table[@id='rcpnutrients']/tbody/tr"));
							String nutritionValue="";
							/*for (WebElement row : nutValueEle) 
							 {								 
								WebElement cols = row.select("td");
							   nutritionValue = nutritionValue + cols.getText();
					         } */
							
							// fetching Ingredients 
							List<WebElement> ingredientsEle = driver.findElements(By.xpath("//div[@id='rcpinglist']/div//a"));							
							
							
							ingredient_List="";
							 for(WebElement ingredient: ingredientsEle) {
							  ingredient_List = ingredient_List+","+ingredient.getText().toLowerCase();
							 }
							 if(ingredient_List.isEmpty())
								 ingredient_List=ingredient_List;
							 else
								 ingredient_List=ingredient_List.substring(1);				

							
							boolean validLFVRecipe = true;
														
							//Retrieve data from Elimination arraylist using for loop, 
							for(String eliminatedItem: LFV_EliminateItemList) 
							{								
								//Then compare each value with Ingredients.
								if(ingredient_List.contains(eliminatedItem))
								{
									//System.out.println("Item invalid: " +eliminatedItem);
									validLFVRecipe = false;
									break;
								}									
							}
							
							
							if(validLFVRecipe) 
							{
								  recipes_LFV_Elimination.put( Integer.toString(LFVCounter) , new Object[] { recipe_id, recipeName,
										  rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
										  noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL,""});

								 								  
								System.out.println("Valid recipe Ingredients for LFV "+ingredient_List);
								//Retrieve data from Add arraylist using for loop, 
								for(String addItem: LFV_AddItemList) 
								{								
									//Then compare each value with Ingredients.
									if(ingredient_List.contains(addItem))
									{
											System.out.println("LFV Add Item valid: " +addItem);
											
											recipes_LFV_Add.put( Integer.toString(LFVCounter) , new Object[] { recipe_id, recipeName,
													  rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
													  noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL, addItem });
											break;
									}										
								}
																							
								LFVCounter = LFVCounter + 1;
							}
							
							// Iterate LCHF Elimination array list using for loop and compare each value with Ingredients to filter recipes
							boolean validLCHFRecipe = true;
							  for(String eliminatedItem : LCHF_EliminateItemList) {
							  
								  if(ingredient_List.contains(eliminatedItem)) {
							  
									  validLCHFRecipe = false;
							  
									  break; 
									  } 
								  }
							  
							  	if(validLCHFRecipe) {
							  
							  		recipes_LCHF_Elimination.put( Integer.toString(LCHFCounter) , new Object[] { recipe_id, recipeName,
							  				rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
							  				noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL,"" });
							  		
							  		System.out.println("Valid recipe Ingredients for LCHF "+ingredient_List);
							  		for(String addItem : LCHF_AddItemList) {
							  			
							  			if(ingredient_List.contains(addItem)) {
							  				System.out.println("LCHF Add Item valid: " +addItem);
							  				recipes_LCHF_Add.put( Integer.toString(LCHFCounter) , new Object[] { recipe_id, recipeName,
							  						rec_Category, food_Category, ingredient_List, prepTime,cookTime, tags,
							  						noOfServings, cuisineCategory, desc,method, nutritionValue, recipeURL,addItem });
							  
							  				break; 
							  				} 
							  			}
							  
							  		LCHFCounter  = LCHFCounter  + 1; 
							  }
							
						 } 						
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//To click on each recipe
					//}//End If 
						
				}//End for				
			 }//End pagination								
			
		}
		
		System.out.println("Total Valid LFV Recipe(Elimination Check) = " + recipes_LFV_Elimination.size());
		System.out.println("Total Valid LFV Recipe(Add Check) = " + recipes_LFV_Add.size());
		
		System.out.println("Total Valid LCHF Recipe(Elimination Check) = " + recipes_LCHF_Elimination.size());
		System.out.println("Total Valid LCHF Recipe(Add Check) = " + recipes_LCHF_Add.size());	
		
		//write data to database
		DbConnection db=new DbConnection();
		
		//Insert data for LFV Elimination Recipes into PostgreSQL
		db.insertRow(RecipeDetailsDBUtil.getConnection(), "lfv_recipes_with_eliminateitems", recipes_LFV_Elimination);
		
		//Insert data for LFV Add Recipes into PostgreSQL
		db.insertRow(RecipeDetailsDBUtil.getConnection(), "lfv_recipes_with_addon_items", recipes_LFV_Add);
		
		//Insert data for LCHF Elimination Recipes into PostgreSQL
		db.insertRow(RecipeDetailsDBUtil.getConnection(), "lchf_recipes_with_eliminateitems", recipes_LCHF_Elimination);
		
		//Insert data for LCHF Add Recipes into PostgreSQL
		db.insertRow(RecipeDetailsDBUtil.getConnection(), "lchf_recipes_with_addon_items", recipes_LCHF_Add);
	}

	public void read_LFV_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 76; i++) {
			String testData = reader.getCellData("Final list for LFV Elimination ", 0, i);
			LFV_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_LFV_Add_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LFV Elimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 90; i++) {
			String testData = reader.getCellData("Final list for LFV Elimination ", 1, i);
			LFV_AddItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_LCHF_Elimination_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LCHFElimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 92; i++) {
			String testData = reader.getCellData("Final list for LCHFElimination ", 0, i);
			LCHF_EliminateItemList.add(testData.toLowerCase());
			//System.out.println(testData);
		}
	}
	
	public void read_LCHF_Add_Excel() {
		ExcelReaderCode reader = new ExcelReaderCode("./src/test/resources/IngredientsAndComorbidities-ScrapperHackathon.xlsx");
		Boolean sheetCheck = reader.isSheetExist("Final list for LCHFElimination ");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck);
			for (int i = 3; i <= 34; i++) {
			String testData = reader.getCellData("Final list for LCHFElimination ", 1, i);
			LCHF_AddItemList.add(testData.toLowerCase());
			
		}
	}
	
	public void read_CuisineCategoryData_Excel() {
		
		ExcelReaderCode FoodCategoryreader = new ExcelReaderCode("./src/test/resources/Recipe-filters-ScrapperHackathon.xlsx");
		Boolean sheetCheck1 = FoodCategoryreader.isSheetExist("Food Category");
		System.out.println("Is the Datasheet exist? -  " + sheetCheck1);
			for (int f = 2; f <= 32; f++) {
			String cuisineData = FoodCategoryreader.getCellData("Food Category", 1, f);
			cuisineDataList.add(cuisineData);			
			}
	}
	

}