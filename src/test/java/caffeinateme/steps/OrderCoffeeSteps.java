package caffeinateme.steps;

import caffeinateme.model.*;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

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

    @ParameterType("\"[^\"]*\"")
    public Order order(String orderedProduct)
    {
        return Order.of(1, orderedProduct).forCustomer(customer);
    }

    @When("Cathy orders a {order}")
    public void cathy_orders_a_product(Order order) {

        this.order = order;
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

    @When("Cathy orders a {order} with a comment {string}")
    public void cathy_orders_a_product_with_a_comment(Order order, String orderComment) {
        this.order = order.withComment(orderComment);
        customer.placesAnOrderFor(this.order).at(coffeeShop);
    }

    @And("the order should have the comment {string}")
    public void the_order_should_have_the_comment(String orderComment) {
        Order cathysOrder = coffeeShop.getOrderFor(customer).orElseThrow(() -> new AssertionError("No order found "));
        assertThat(cathysOrder.getComment()).isEqualTo(orderComment);
    }

    @When("Cathy places an order for the following items:")
    public void cathy_places_an_order_for_the_following_items(List<Map<String, String>> items) {
        var orderedItems = items.stream().map(row-> new OrderItem(row.get("Product"), Integer.parseInt(row.get("Quantity")))).toList();
        this.order = new Order(orderedItems, this.customer);
        customer.placesAnOrderFor(this.order).at(coffeeShop);
    }

    @And("the order should contain {int} line items")
    public void the_order_should_contain_line_items(int expectedNumberOfLineItems) {
        var order = coffeeShop.getOrderFor(customer).orElseThrow(() -> new AssertionError("No order found "));
        assertThat(order.getItems().size()).isEqualTo(expectedNumberOfLineItems);
    }
}
