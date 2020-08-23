package me.bohat.tacocloud.repositories.interfaces;

import me.bohat.tacocloud.models.Order;

public interface OrderRepository {
    Order save(Order order);
}
