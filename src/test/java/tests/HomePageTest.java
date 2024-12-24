package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;
import pageObject.RecipeAtoD;
import pageObject.RecipeAtoZ;
import pageObject.RecipeEtoH;
import pageObject.RecipeItoL;
import pageObject.RecipeMtoP;
import pageObject.RecipeQtoU;
import pageObject.RecipeVtoZ;
import pages.*;
import utils.DbConnection;

public class HomePageTest extends TestBase {

	HomePage homePage;

	public HomePageTest() {
		super();

	}

	@BeforeClass
	public void setup() {

		TestBase.initialization();
		homePage = new HomePage();
	}
	
	
	@Test(priority=1)
	public void RecipeAtoDTest() throws Exception {
		DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeAtoD recipe = new RecipeAtoD(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}
	

	
	@Test(priority=2)
	public void RecipeEtoHTest() throws Exception {
	DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeEtoH recipe = new RecipeEtoH(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}
	
	@Test(priority=3)
	public void RecipeItoLTest() throws Exception {
		DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeItoL recipe = new RecipeItoL(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}
	
	@Test(priority=4)
	public void RecipeMtoPTest() throws Exception {
		DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeMtoP  recipe = new RecipeMtoP(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}
	
	@Test(priority=5)
	public void RecipeQtoUTest() throws Exception {
		DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeQtoU recipe = new RecipeQtoU(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}
	
	@Test(priority=6)
	public void RecipeVtoZTest() throws Exception {
		DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeVtoZ recipe = new RecipeVtoZ(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();

	}

}
