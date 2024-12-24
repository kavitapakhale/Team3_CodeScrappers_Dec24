package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;
import pageObject.RecipeAtoD;
import pages.*;
import utils.LoggerLoad;

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
	@Test(priority=3)
	public void RecipeItoLTest() throws Exception {
		DbConnection db= new DbConnection();
		db.DbSetup();
		RecipeItoL recipe = new RecipeItoL(TestBase.getDriver());
		recipe.click_AtoZ_recipes();
		recipe.getRecipeInfo();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();

	}

}
