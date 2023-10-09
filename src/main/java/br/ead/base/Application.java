package br.ead.base;

import br.ead.base.order.Order;
import br.ead.base.order.OrderPublisher;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.pulsar.annotation.EnablePulsar;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnablePulsar
@EnableScheduling
@SpringBootApplication
public class Application {

    @Autowired
    private OrderPublisher orderPublisher;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @SneakyThrows
    @Scheduled(fixedRateString = "PT1S")
    public void publishOrder() {
        IntStream.range(1, RandomUtils.nextInt(2, 100))
                .mapToObj(ignore -> new Order())
                .forEach(orderPublisher::publish);
    }
}
