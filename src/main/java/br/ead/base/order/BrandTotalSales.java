package br.ead.base.order;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
@NoArgsConstructor
public final class BrandTotalSales {
    private String brandName;
    private double totalAmount;

    public BrandTotalSales(
            String brandName,
            double totalAmount) {
        this.brandName = brandName;
        this.totalAmount = totalAmount;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("brandName")
    public String brandName() {
        return brandName;
    }

    @DynamoDbAttribute("totalAmount")
    public double totalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BrandTotalSales) obj;
        return Objects.equals(this.brandName, that.brandName) &&
               Double.doubleToLongBits(this.totalAmount) == Double.doubleToLongBits(that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandName, totalAmount);
    }

    @Override
    public String toString() {
        return "BrandTotalSales[" +
               "brandName=" + brandName + ", " +
               "totalAmount=" + totalAmount + ']';
    }


}
