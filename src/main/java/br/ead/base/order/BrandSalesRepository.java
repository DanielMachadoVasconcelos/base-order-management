package br.ead.base.order;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@AllArgsConstructor
public class BrandSalesRepository {

    private DynamoDbEnhancedClient dynamoDbenhancedClient ;

    // Store the sales item in the database
    public void save(final BrandTotalSales sales) {
        DynamoDbTable<BrandTotalSales> orderTable = getTable();
        orderTable.putItem(sales);
    }

    // Retrieve a single order item from the database
    public BrandTotalSales getTotalBrandSales(final String brandName) {
        DynamoDbTable<BrandTotalSales> orderTable = getTable();
        // Construct the key with partition and sort key
        Key key = Key.builder().partitionValue(brandName)
//                .sortValue(month)
                .build();

        return orderTable.getItem(key);
    }

    // Create a tablescheme to scan our bean class order
    private DynamoDbTable<BrandTotalSales> getTable() {
        return dynamoDbenhancedClient.table("BrandSales", TableSchema.fromBean(BrandTotalSales.class));
    }
}
