package br.ead.base.order;

import java.util.UUID;
import net.datafaker.Faker;
import org.apache.commons.lang3.math.NumberUtils;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public record Order(@DynamoDbPartitionKey @DynamoDbAttribute("orderId") UUID orderId,
                    @DynamoDbAttribute("customerName") String customerName,
                    @DynamoDbAttribute("productBrand") String productBrand,
                    @DynamoDbAttribute("productName") String productName,
                    @DynamoDbAttribute("price") double price,
                    @DynamoDbAttribute("quantity") int quantity) {

    private static Faker faker = new Faker();

    public Order() {
        this(
                UUID.randomUUID(),
                faker.funnyName().name(),
                faker.commerce().brand(),
                faker.commerce().productName(),
                NumberUtils.toDouble(faker.commerce().price(0, 120)),
                faker.number().numberBetween(1, 10)
        );
    }
}
