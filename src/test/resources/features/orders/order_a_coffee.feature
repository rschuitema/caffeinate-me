Feature: Order a coffee

  In order to save time when I pick up my morning coffee
  As a coffee love
  I want to be able to order my coffee in advance

  Scenario: Buyer orders a coffee when they are close to the coffee shop
    Given Cathy is a CaffeinateMe customer
    And Cathy is 100 metres from the coffee shop
    When Cathy orders a "large cappuccino"
    Then Barry should receive the order
    And Barry should know that the order is Urgent

  Scenario Outline: Buyer orders a coffee at a certain distance to the coffee shop
    Given Cathy is a CaffeinateMe customer
    And Cathy is <distance> metres from the coffee shop
    When Cathy orders a "large cappuccino"
    Then Barry should receive the order
    And Barry should know that the order is <status>

    Examples:
    | distance | status |
    | 50 | Urgent |
    | 200 | Urgent |
    | 201 | Normal |
    | 300 | Normal |

  Scenario: Buyers can add a comment with their order
    Given Cathy is a CaffeinateMe customer
    When Cathy orders a "large cappuccino" with a comment "Double sugar"
    Then Barry should receive the order
    And the order should have the comment "Double sugar"