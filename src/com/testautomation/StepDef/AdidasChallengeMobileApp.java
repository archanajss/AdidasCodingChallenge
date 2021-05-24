package com.testautomation.StepDef;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdidasChallengeMobileApp {
    private AndroidDriver driver = null;

    @Given("User opens challenge app")
    public void userOpensChallengeApp() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "My Device");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("appPackage", "com.example.challenge");
        caps.setCapability("appActivity", "com.example.challenge.MainActivity");
        try {
            driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @When("Home page loads")
    public void homePageLoads() {
        Assert.assertNotNull(driver.findElementById("com.example.challenge:id/recyclerview"));
    }

    @Then("Validate tiles in home page")
    public void validateTilesInHomePage() {
        // assert that products list is not empty
        Assert.assertFalse(driver.findElementsByClassName("android.view.ViewGroup").isEmpty());
    }

    @Then("User closes challenge app")
    public void userClosesChallengeApp() {
        driver.closeApp();
    }

    @When("User types in a search string {string}")
    public void userTypesInASearchString(String searchText) {
        // Bug - Search button missing
        driver.findElementById("com.example.challenge:id/search").click();
        driver.findElementById("com.example.challenge:id/search").sendKeys(searchText);
    }

    @Then("Filtered list of products are displayed with type {string}")
    public void filteredListOfProductsAreDisplayed(String searchText) {
        // searchText should be present in name or description
        List<WebElement> products = driver.findElementsByClassName("android.view.ViewGroup");
        for (WebElement prod : products) {
            List<WebElement> textControls = prod.findElements(By.className("android.widget.TextView"));
            WebElement prodNameCtrl = textControls.stream()
                    .filter(x -> x.getAttribute("id").equals("productName"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(prodNameCtrl);
            WebElement prodDescrCtrl = textControls.stream()
                    .filter(x -> x.getAttribute("id").equals("productDescription"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(prodDescrCtrl);
            Assert.assertTrue(prodNameCtrl.getText().toLowerCase().contains(searchText.toLowerCase())
                    || prodDescrCtrl.getText().toLowerCase().contains(searchText.toLowerCase()));
        }
    }

    @When("User clicks on a product")
    public void userClicksOnAProduct() {
        //User clicks on any product
        driver.findElementById("android.view.ViewGroup").click();
    }

    @When("User clicks on a product with index {string}")
    public void userClicksOnAProductWithIndex(String index) {
        List<WebElement> products = driver.findElementsByClassName("android.view.ViewGroup");
        products.get(Integer.parseInt(index)).click();
    }

    @And("User clicks on Add Review button")
    public void userClicksOnAddReviewButton() {
        driver.findElementById("com.example.challenge:id/addReview").click();
    }

    @Then("User is able to add review with review text {string} and rating number {string}")
    public void userIsAbleToAddReviewWithReviewTextAndRatingNumber(String review, String rating) {
        driver.findElementById("com.example.challenge:id/reviewDetails").sendKeys(review);
        //driver.findElement(By.id("xpath_of_spinner")).click();
        driver.findElementById("com.example.challenge:id/reviewNumber").click();
        driver.findElementByXPath("//android.widget.CheckedTextView[3]").click();
        driver.findElementById("com.example.challenge:id/saveReview").click();
    }

    @Then("User scrolls to the bottom of the review list")
    public void userScrollsToTheBottomOfTheReviewList() {
        Assert.assertEquals(driver.findElementById("com.example.challenge:id/reviewsRecycler").getAttribute("scrollable"), "true");
        //Need to verify actual scrollability
        //TouchActions action = new TouchActions(driver);
        //action.scroll(element, 10, 100);
        //action.perform();
    }

    @Then("Added review {string} is displayed at the end of the list")
    public void addedReviewIsDisplayedAtTheEndOfTheList(String review) {
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text("+review+"))");
    }

    @When("User clicks on Settings")
    public void userClicksOnSettings() {
    }

    @Then("User sees options {string}")
    public void userSeesOptions(String arg0) {
    }

    @Then("Relevant product description is displayed")
    public void relevantProductDescriptionIsDisplayed() {
    }

    @Given("User is working on the app")
    public void userIsWorkingOnTheApp() {
    }

    @When("Internet is down")
    public void internetIsDown() {
    }

    @Then("App throws appropriate error message {string}")
    public void appThrowsAppropriateErrorMessage(String arg0) {
    }
}
