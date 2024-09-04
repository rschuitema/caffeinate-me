package caffeinateme.model;

import java.util.*;

public class CoffeeShop {

    private final Queue<Order> orders = new LinkedList<>();
    private final Queue<Customer> customers = new LinkedList<>();

    public void placeOrder(Order order, int distanceInMetres) {
        if (distanceInMetres <= 200)
        {
            order = order.withStatus(OrderStatus.Urgent);
        }
        orders.add(order);
    }

    public List<Order> getPendingOrders() {
        return new ArrayList<>(orders);
    }

    public Optional<Order> getOrderFor(Customer customer) {
        return orders.stream()
                .filter( order -> order.getCustomer().equals(customer))
                .findFirst();
    }

    public Customer registerNewCustomer(String customerName) {
        Customer customer = new Customer(customerName);
        this.customers.add(customer);
        return customer;
    }
}
