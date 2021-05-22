@ChallengeAppRegressionTest
Feature: Challenge home page

Scenario: Challenge home page
  Given User opens challenge app
  When Home page loads
  Then Validate tiles in home page
  Then User closes challenge app

Scenario: Check Search functionality
  Given User opens challenge app
  When User types in a search string "Trainers"
  Then Filtered list of products are displayed with type "Trainers"

Scenario: Check Add Review functionality
  Given User opens challenge app
  When User clicks on a product with index "3"
  And User clicks on Add Review button
  Then User is able to add review with review text "Sample review" and rating number "5"
  Then User scrolls to the bottom of the review list
  Then Added review "Sample review" is displayed at the end of the list

Scenario: Check Settings button functionality
  Given User opens challenge app
  When User clicks on Settings
  Then User sees options "Profile Country Language Currency"

Scenario: Check product has the correct product description
  Given User opens challenge app
  When User clicks on a product
  Then Relevant product description is displayed

Scenario: Check app response when internet is down
  Given User is working on the app
  When Internet is down
  Then App throws appropriate error message "Check your internet connection"
