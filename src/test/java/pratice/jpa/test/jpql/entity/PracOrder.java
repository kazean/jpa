package pratice.jpa.test.jpql.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRAC_ORDERS")
public class PracOrder {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    private int orderAmount;
    @Embedded
    private PracAddress pracAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private PracMember pracMember;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private PracProduct pracProduct;


    public PracOrder() {
    }

    public PracOrder(int orderAmount, PracAddress pracAddress) {
        this.orderAmount = orderAmount;
        this.pracAddress = pracAddress;
    }

    public PracProduct getPracProduct() {
        return pracProduct;
    }

    public void setPracProduct(PracProduct pracProduct) {
        this.pracProduct = pracProduct;
    }

    public PracMember getPracMember() {
        return pracMember;
    }

    public void setPracMember(PracMember pracMember) {
        this.pracMember = pracMember;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public PracAddress getPracAddress() {
        return pracAddress;
    }

    public void setPracAddress(PracAddress pracAddress) {
        this.pracAddress = pracAddress;
    }

    @Override
    public String toString() {
        return "PracOrder{" +
                "id=" + id +
                ", orderAmount=" + orderAmount +
                ", pracAddress=" + pracAddress +
                '}';
    }

    /*
         연관관계 편의메서드
         */
    public void addPracMember(PracMember pracMember) {
        if(this.pracMember != null){
            this.pracMember.getPracOrders().remove(this);
        }
        pracMember.getPracOrders().add(this);
        this.pracMember = pracMember;
    }

    public void addPracProduct(PracProduct pracProduct) {
        if (pracProduct.getStockAmount() >= this.getOrderAmount()) {
            if (this.pracProduct != null) {
                this.pracProduct.getPracOrders().remove(this);
            }
            pracProduct.getPracOrders().add(this);
            pracProduct.setStockAmount(pracProduct.getStockAmount() - this.getOrderAmount());
            this.pracProduct = pracProduct;
        }else{
            throw new IllegalStateException("주문양이 너무 많습니다.");
        }
    }
}
