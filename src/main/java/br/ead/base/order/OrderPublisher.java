package br.ead.base.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderPublisher {

    private PulsarTemplate<String> stringTemplate;
    private ObjectMapper objectMapper;

    @SneakyThrows
    public void publish(Order order)  {
        stringTemplate.send("order-management-topic", objectMapper.writeValueAsString(order));
    }
}
