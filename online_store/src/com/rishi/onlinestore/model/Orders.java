package com.rishi.onlinestore.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Orders  extends AbstractTimestampEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long order_id;
    private Long user_id;

    @OneToMany
    @JoinTable(name = "ORDER_VALUES", joinColumns = {
        @JoinColumn(name = "order_values_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "order_id")})
    private List<OrderValues> orders;
    private Long total_amount;
    private Long payment_method;
    private Long delivery_method;

    public Orders() {
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public List<OrderValues> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderValues> orders) {
        this.orders = orders;
    }

    public Long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    public Long getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Long payment_method) {
        this.payment_method = payment_method;
    }

    public Long getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(Long delivery_method) {
        this.delivery_method = delivery_method;
    }

}
