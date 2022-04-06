package pratice.jpa.test.jpql.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PracTeam {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "pracTeam")
    private List<PracMember> pracMembers = new ArrayList<>();

    public PracTeam() {
    }

    public PracTeam(String name) {
        this.name = name;
    }

    public List<PracMember> getPracMembers() {
        return pracMembers;
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

    @Override
    public String toString() {
        return "PracTeam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /*
        연관관계 편의메소드 둘중 하나만 호출
         */
    public void addPracMember(PracMember pracMember) {
        if (pracMember.getPracTeam() != null) {
            pracMember.getPracTeam().getPracMembers().remove(pracMember);
        }
        pracMember.setPracTeam(this);
        pracMembers.add(pracMember);
    }
}
