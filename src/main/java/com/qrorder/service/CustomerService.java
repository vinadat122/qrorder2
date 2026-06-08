package com.qrorder.service;

import com.qrorder.dto.customer.CustomerOrderRequest;
import com.qrorder.dto.customer.CustomerTableResponse;
import com.qrorder.dto.order.response.OrderResponse;

import java.util.List;

public interface CustomerService {

    CustomerTableResponse getTableByQrToken(
            String qrToken
    );

    void createCustomerOrder(
            CustomerOrderRequest request
    );

    List<OrderResponse>
    getCustomerOrders(
            String sessionToken
    );

}

