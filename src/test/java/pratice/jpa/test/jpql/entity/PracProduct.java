package pratice.jpa.test.jpql.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PracProduct {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;
    private int price;
    private int stockAmount;

    @OneToMany(mappedBy = "pracProduct")
    List<PracOrder> pracOrders = new ArrayList<>();

    public PracProduct() {
    }

    public PracProduct(String name, int price, int stockAmount) {
        this.name = name;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    public List<PracOrder> getPracOrders() {
        return pracOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    @Override
    public String toString() {
        return "PracProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockAmount=" + stockAmount +
                '}';
    }

    /*
    연관관계 편의메소드
     */
    public void addPracOrders(PracOrder pracOrder) {
        if (pracOrder.getPracProduct() != null) {
            pracOrder.getPracProduct().getPracOrders().remove(pracOrder);
        }
        pracOrder.setPracProduct(this);
        this.pracOrders.add(pracOrder);
    }
}
