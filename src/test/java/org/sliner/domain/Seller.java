package org.sliner.domain;

import org.sliner.annotation.Criteria;
import org.sliner.annotation.Managed;
import org.sliner.annotation.Sorting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Component:
 * Description:
 * Date: 14-6-18
 *
 * @author Andy Ai
 */
@Managed(key = "seller")
@Entity(name = "tb_seller")
public class Seller {
    @Id
    @Column(name = "seller_id")
    private String sellerId;

    private String sellerName;
    @Criteria
    @Column(name = "partition_id")
    private Long partitionId;
    @Criteria
    @Column(name = "seller_type")
    private Long sellerType;
    @Sorting
    @Column(name = "age")
    private int age;

    private int level;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Criteria(multiple = true)
    @Column(name = "seller_name")
    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getSellerType() {
        return sellerType;
    }

    public void setSellerType(Long sellerType) {
        this.sellerType = sellerType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Sorting
    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Long partitionId) {
        this.partitionId = partitionId;
    }
}
