

Feature: Validating the SauceDemo application
	Background: To Launch the browser
    Given Open Browser
  
 
  Scenario: Scenario1: Checkout and validate the product details whether you are ordered the correct product.
  	Given Enter Application URL
  	When Enter UserName And Enter Password
  	Then Submit Login button
  	And Goto Product Page
  	Then Store the Product Details
  	Then Add the Product to cart and Verify Cart Qty
  	Then Navigate to Cart Page and Click to Checkout button
  	Then Enter Checkout information and Click to Continue button
  	And Verify Product Overview Page
  	And Validate the product details whether you are ordered the correct product
  

  Scenario: Scenario2: Get All the Product list and print in console.
  	Given Enter Application URL
  	When Enter UserName And Enter Password
  	Then Submit Login button
  	And Goto Product Page
  	Then Store All the Products And Print in console
  	And Logout from Application
  

  Scenario: Scenario3: Get All product price and remove dollar symbol 
  	and Validate Product & Price in the respective product indvidual page.
  	Given Enter Application URL
  	When Enter UserName And Enter Password
  	Then Submit Login button
  	And Goto Product Page
  	Then Get the price of all products and remove the dollar symbol
  	Then Validate product price and individual page against product listing page
  	And Logout from Application 
  	

  Scenario: Sceanrio4: Validate Invalid Username and Password Scenario
    Given Enter Application URL
    When Enter UserName And Enter InValidPassword
    Then Submit Login button
    And Validate Error Message

 
 