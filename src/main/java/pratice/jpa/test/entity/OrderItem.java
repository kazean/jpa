package pratice.jpa.test.entity;

import pratice.jpa.test.entity.item.Item;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /*
    연관관계편의 메서드
     */
    public void setOrder(Order order) {
        if(this.order != null){
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

}
