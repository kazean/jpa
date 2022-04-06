package pratice.jpa.test.jpql.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PracMember {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_ID")
    private PracTeam pracTeam;

    @OneToMany(mappedBy = "pracMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PracOrder> pracOrders = new ArrayList<>();

    public PracMember() {
    }

    public PracMember(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public PracTeam getPracTeam() {
        return pracTeam;
    }

    public void setPracTeam(PracTeam pracTeam) {
        this.pracTeam = pracTeam;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PracMember{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    /*
        연관관계편의 메소드 둘중하나만 호출
         */
    public void addPracTeam(PracTeam pracTeam) {
        if(this.pracTeam != null){
            this.pracTeam.getPracMembers().remove(this);
        }
        this.pracTeam = pracTeam;
        pracTeam.getPracMembers().add(this);
    }

    public void addPracOrder(PracOrder pracOrder) {
        if(pracOrder.getPracMember() != null){
            pracOrder.getPracMember().getPracOrders().remove(pracOrder);
        }
        pracOrder.setPracMember(this);
        this.pracOrders.add(pracOrder);
    }
}
