package pratice.jpa.model.entity.pratice.nonIdentifyingRelationship;

import javax.persistence.*;

@Entity
public class ChildNonId {
    @Id @Column(name = "CHILD_ID")
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1")
            ,@JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")})
    private ParentNonId parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParentNonId getParent() {
        return parent;
    }

    public void setParent(ParentNonId parent) {
        this.parent = parent;
    }
}
