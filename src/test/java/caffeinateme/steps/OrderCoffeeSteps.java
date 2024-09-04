package caffeinateme.steps;

import caffeinateme.model.CoffeeShop;
import caffeinateme.model.Customer;
import caffeinateme.model.Order;
import caffeinateme.model.OrderStatus;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderCoffeeSteps {
    Customer customer;
    CoffeeShop coffeeShop = new CoffeeShop();
    Order order;

    @Given("{} is a CaffeinateMe customer")
    public void is_a_Caffeinate_me_customer(String customerName) {
        customer = coffeeShop.registerNewCustomer(customerName);
    }

    @Given("Cathy is {int} metres from the coffee shop")
    public void cathy_is_metres_from_the_coffee_shop(Integer distance) {

        customer.setDistanceFromShop(distance);

    }
    @When("Cathy orders a {string}")
    public void cathy_orders_a_product(String orderedProduct) {

        order = Order.of(1, orderedProduct).forCustomer(customer);
        customer.placesAnOrderFor(order).at(coffeeShop);
    }
    @Then("Barry should receive the order")
    public void barry_should_receive_the_order() {

        assertThat(coffeeShop.getPendingOrders()).contains(order);
    }
    @Then("^Barry should know that the order is (.*)")
    public void barry_should_know_that_the_order_is(OrderStatus expectedOrderStatus)
    {
        Order cathysOrder = coffeeShop.getOrderFor(customer).orElseThrow(() -> new AssertionError("No order found "));
        assertThat(cathysOrder.getStatus()).isEqualTo(expectedOrderStatus);
    }

    @When("Cathy orders a {string} with a comment {string}")
    public void cathy_orders_a_product_with_a_comment(String orderedProduct, String orderComment) {
        order = Order.of(1, orderedProduct).forCustomer(customer).withComment(orderComment);
        customer.placesAnOrderFor(order).at(coffeeShop);
    }

    @And("the order should have the comment {string}")
    public void theOrderShouldHaveTheComment(String orderComment) {
        Order cathysOrder = coffeeShop.getOrderFor(customer).orElseThrow(() -> new AssertionError("No order found "));
        assertThat(cathysOrder.getComment()).isEqualTo(orderComment);
    }
}
