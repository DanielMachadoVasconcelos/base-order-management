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
public class OrderRepository {
  
  private DynamoDbEnhancedClient dynamoDbenhancedClient ;

  // Store the order item in the database
  public void save(final Order order) {
    DynamoDbTable<Order> orderTable = getTable();
    orderTable.putItem(order);
  }

  // Retrieve a single order item from the database
  public Order getOrder(final String customerID, final String orderID) {
    DynamoDbTable<Order> orderTable = getTable();
    // Construct the key with partition and sort key
    Key key = Key.builder().partitionValue(customerID)
                       .sortValue(orderID)
                       .build();
    
    return orderTable.getItem(key);
  }


  // Create a tablescheme to scan our bean class order
  private DynamoDbTable<Order> getTable() {
    return dynamoDbenhancedClient.table("Order", TableSchema.fromBean(Order.class));
  }
}
