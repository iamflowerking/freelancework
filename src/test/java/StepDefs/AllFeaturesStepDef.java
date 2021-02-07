package StepDefs;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Pages.Login;
import WebDriverManager.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.en.*;



public class AllFeaturesStepDef extends WebDriverManager{

	Login lg = null;
	String sScenario1ExpProduct="";
	String sScenario1ExpProductPrice="";
	String sScenario1ExpQty	="";
	
	public AllFeaturesStepDef() {
		lg = new Login();
	}
	
	@After
	public void QuitBrowser() {
		try {
			closeDriver();
			quiteBrowser();
			System.out.println("Browser Closed & Killed.");
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Given("Open Browser")
	public void open_Browser() {
	    setUpDriver("Chrome");
	}

	@Given("Enter Application URL")
	public void enter_Application_URL() {
	    getDriver().get(getPropertyKey("AppURL"));
	}

	@When("Enter UserName And Enter InValidPassword")
	public void enter_UserName_And_Enter_InValidPassword() {
	    lg._login_Enter_UserName("standard_user");
	    lg._login_Enter_Password("secret_sauce12");
	    
	}

	@Then("Submit Login button")
	public void submit_Login_button() {
	    lg._login_Click_LoginButton();
	}

	@Then("Validate Error Message")
	public void validate_Error_Message() {
		String sExpMessage = "Epic sadface: Username and password do not match any user in this service";
	    String sResult = lg._login_ErrorMessage();
	    if(sExpMessage.equals(sResult)) {
	    	 System.out.println("Expected Result = "+sExpMessage+" ,Actual Result = "+sResult);
	    }
	    
	}
	
	@When("Enter UserName And Enter Password")
	public void enter_UserName_And_Enter_Password() {
		lg._login_Enter_UserName("standard_user");
	    lg._login_Enter_Password("secret_sauce");
	}

	@Then("Goto Product Page")
	public void goto_Product_Page() {
	    try {
	    	// Wait to display the Product Detail page
			PerformActionOnElement("Prd_Link_SouceLabsBackpack", "WaitForElementDisplay", "");
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Then("Store the Product Details")
	public void store_the_Product_Details() {
		 try {
			// Store the Product details in the global variable
			sScenario1ExpProduct = FindAnElement("Prd_Link_SouceLabsBackpack").getText();
			sScenario1ExpProductPrice = FindAnElement("Prd_Price_SouceLabsBackpack").getText();
			sScenario1ExpQty ="1";
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Then("Add the Product to cart and Verify Cart Qty")
	public void add_the_Product_to_cart_and_Verify_Cart_Qty() {
		try {
			// add to cart to below selected product
			PerformActionOnElement("Prd_Button_AddToCart_SouceLabsBackpack", "Click", "");
			//wait for the qty to display in the cart
			PerformActionOnElement("Prd_Count_Cart", "WaitForElementDisplay", "");
			String sActualQtyInCart = FindAnElement("Prd_Count_Cart").getText().trim();
			//Verify the Expected & Actual Qty in the Cart Display.
			Assert.assertEquals(sActualQtyInCart, sScenario1ExpQty);

		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Then("Navigate to Cart Page and Click to Checkout button")
	public void navigate_to_Cart_Page_and_Click_to_Checkout_button() {
		try {
			// Click on Cart Icon
			PerformActionOnElement("Prd_Link_Cart", "Click", "");
			//wait for your cart page display
			PerformActionOnElement("Cart_Title", "WaitForElementDisplay", "");
			// Click to Checkout button
			PerformActionOnElement("Cart_Link_Checkout", "Click", "");

		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Then("Enter Checkout information and Click to Continue button")
	public void enter_Checkout_information_and_Click_to_Continue_button() {
		try {
			//wait for your cart: your information
			PerformActionOnElement("CheckOut_Title", "WaitForElementDisplay", "");
			// Enter FirstName, Lastname and PostalCode
			PerformActionOnElement("Checkout_FirstName", "EnterText", "Poovarasan");
			PerformActionOnElement("Checkout_LastName", "EnterText", "Murugan");
			PerformActionOnElement("Checkout_PostalCode", "EnterText", "088");
			// Click to Continue button
			PerformActionOnElement("Checkout_Button_Continue", "Click", "");

		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Then("Verify Product Overview Page")
	public void verify_Product_Overview_Page() {
		try {
	    	// Wait to display the Product Overview page after checkout.
			PerformActionOnElement("CheckOutOverview_Title", "WaitForElementDisplay", "");
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}

	@Then("Validate the product details whether you are ordered the correct product")
	public void validate_the_product_details_whether_you_are_ordered_the_correct_product() {
		try {
	    	// final qty
			String sFinalQty = FindAnElement("CheckoutOverview_Qty").getText().trim();
			// final product
			String sFinalProduct = FindAnElement("CheckoutOverview_Prd_Desc").getText().trim();
			// final product price
			String sFinalProductQty = FindAnElement("CheckoutOverview_Prd_Price").getText().trim();
			
			Assert.assertEquals(sFinalQty, sScenario1ExpQty);
			Assert.assertEquals(sFinalProduct, sScenario1ExpProduct);
			Assert.assertEquals(sFinalProductQty, sScenario1ExpProductPrice);
			//RemoveCart and Logout
			boolean bActual = RemoveCartQty_Logout();
			Assert.assertEquals(bActual, true);
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}
	@Then("Store All the Products And Print in console")
	public void Store_All_the_Products_And_Print_in_console() {
		try {
			List<WebElement> getAllProducts 		= FindAnElementList("Prd_All_Products");
			List<WebElement> getAllProductsPrice	= FindAnElementList("Prd_All_Products_Prices");
			
			int iProductSize = getAllProducts.size();
			for (int i = 0; i < iProductSize; i++) {

				String getProductName = getAllProducts.get(i).getText().trim();
				String getProductPrice = getAllProductsPrice.get(i).getText().trim();
				System.out.println("Product Name : "+getProductName+" & price : "+getProductPrice);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}
	@Then("Get the price of all products and remove the dollar symbol")
	public void Get_the_price_of_all_products_and_remove_the_dollar_symbol() {
		try {
			List<WebElement> getAllProducts 		= FindAnElementList("Prd_All_Products");
			List<WebElement> getAllProductsPrice	= FindAnElementList("Prd_All_Products_Prices");
			
			int iProductSize = getAllProducts.size();
			for (int i = 0; i < iProductSize; i++) {

				String getProductName = getAllProducts.get(i).getText().trim();
				String getProductPrice = getAllProductsPrice.get(i).getText().replace("$", " ").trim();
				System.out.println("Product Name : "+getProductName+" & Removed Dollar Price : "+getProductPrice);
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}
	
	@Then("Validate product price and individual page against product listing page")
	public void Validate_product_price_and_individual_page_against_product_listing_page(){
		try {
			List<WebElement> getAllProducts 		= FindAnElementList("Prd_All_Products");
			List<WebElement> getAllProductsPrice	= FindAnElementList("Prd_All_Products_Prices");
			
			int iProductSize = getAllProducts.size();
			
			for (int i = 0; i < iProductSize; i++) {
				getAllProducts 		= FindAnElementList("Prd_All_Products");
				getAllProductsPrice	= FindAnElementList("Prd_All_Products_Prices");
				String getProductName = getAllProducts.get(i).getText().trim();
				String getProductPrice = getAllProductsPrice.get(i).getText().trim();
				System.out.println("Product Name : "+getProductName+" & Dollar Price : "+getProductPrice);
				
				// Click on the Product Name Link to go individual page
				WebElement weGetProduct = getAllProducts.get(i);
				if(weGetProduct != null) {
					weGetProduct.click();
					PerformActionOnElement("IndPrd_ProductName", "WaitForElementDisplay", "");
					String getIndProductName = FindAnElement("IndPrd_ProductName").getText().trim();
					String getIndProductPrice = FindAnElement("IndPrd_ProductPrice").getText().trim();
					Assert.assertEquals(getIndProductName, getProductName);
					Assert.assertEquals(getIndProductPrice, getProductPrice);
				
					//Click to back button to go back product listing page
					PerformActionOnElement("IndPrd_Link_Back", "Click", "");
					
				}else {
					System.out.println("Product Name Element is not found.");
				}
				
				
				
			}
			
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}
	@Then("Logout from Application")
	public void Logout_from_Application() {
		try {
			// Open Menu
			PerformActionOnElement("OpenMenu", "Click", "");
			//wait for Logout link
			PerformActionOnElement("LogoutLink", "WaitForElementDisplay", "");
			// Click Logout link
			PerformActionOnElement("LogoutLink", "Click", "");
			//Wait for Login page
			PerformActionOnElement("Login_Textbox_UserName", "WaitForElementDisplay", "");
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
	}
	
	public boolean RemoveCartQty_Logout() {
		boolean bResult = false;
		try {
			// Click on Cart Icon
			PerformActionOnElement("Prd_Link_Cart", "Click", "");
			//wait for your cart page display
			PerformActionOnElement("Cart_Title", "WaitForElementDisplay", "");
			// Click to Remove Cart button
			PerformActionOnElement("Cart_Prd_Button_Remove", "Click", "");
			// Click to Continue Shopping button
			PerformActionOnElement("Cart_Link_ContinueShopping", "Click", "");
			// Open Menu
			PerformActionOnElement("OpenMenu", "Click", "");
			//wait for Logout link
			PerformActionOnElement("LogoutLink", "WaitForElementDisplay", "");
			// Click Logout link
			PerformActionOnElement("LogoutLink", "Click", "");
			//Wait for Login page
			PerformActionOnElement("Login_Textbox_UserName", "WaitForElementDisplay", "");
			bResult = true;
		} catch (Exception e) {
			throw new IllegalArgumentException("Exception : "+e.getLocalizedMessage());
		}
		return bResult;
	}
	
}
