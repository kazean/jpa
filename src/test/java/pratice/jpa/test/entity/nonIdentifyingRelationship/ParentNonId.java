package pratice.jpa.test.entity.nonIdentifyingRelationship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ParentIdNonId.class)
public class ParentNonId {

    @Id
    @Column(name = "PARENT_ID1")
    private String parentId1;

    @Id
    @Column(name = "PARENT_ID2")
    private String parentId2;

    private String name;

    public String getParentId1() {
        return parentId1;
    }

    public void setParentId1(String parentId1) {
        this.parentId1 = parentId1;
    }

    public String getParentId2() {
        return parentId2;
    }

    public void setParentId2(String parentId2) {
        this.parentId2 = parentId2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
