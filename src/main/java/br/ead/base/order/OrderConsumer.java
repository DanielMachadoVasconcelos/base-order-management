package br.ead.base.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderConsumer {

//    OrderRepository orderRepository;
    BrandSalesRepository brandSalesRepository;
    ObjectMapper objectMapper;

//    @SneakyThrows
//    @PulsarListener(
//            subscriptionName = "order-management-topic-subscription",
//            topics = "order-management-topic",
//            subscriptionType = SubscriptionType.Shared
//    )
//    public void allOrders(String json) {
//        Order order = objectMapper.readValue(json, Order.class);
//        orderRepository.save(order);
//    }

    @PulsarListener(
            subscriptionName = "brand-sales-topic-subscription",
            topics = "order-management-topic",
            subscriptionType = SubscriptionType.Shared
    )
    public void totalBrandSales(String json) {
        log.info("Received message {}", json);

        Order order = null;

        try {
            order = objectMapper.readValue(json, Order.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing json {}", json, e);
            throw new RuntimeException("Error parsing json", e);
        }

        String productBrand = order.productBrand();
        double totalOrderAmount = order.price() * order.quantity();

        BrandTotalSales totalBrandSales = brandSalesRepository.getTotalBrandSales(productBrand);
        double totalAmount = totalBrandSales.totalAmount() + totalOrderAmount;

        log.info("Total amount for brand {} is {}", productBrand, totalAmount);
        brandSalesRepository.save(
            new BrandTotalSales(productBrand, totalAmount)
        );
    }
}