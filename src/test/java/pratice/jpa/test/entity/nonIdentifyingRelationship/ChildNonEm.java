package pratice.jpa.test.entity.nonIdentifyingRelationship;

import javax.persistence.*;

@Entity
public class ChildNonEm {
    @Id @Column(name = "CHILD_ID")
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1"),
            @JoinColumn(name = "PARENT_ID2")
    })
    private ParentNonEm parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParentNonEm getParent() {
        return parent;
    }

    public void setParent(ParentNonEm parent) {
        this.parent = parent;
    }
}
