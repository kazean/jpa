package pratice.jpa.model.entity.pratice.identifyingRelationship;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChildIdIdenEm implements Serializable {
    @Column(name = "PARENT_ID")
    private String parent;

    @Column(name = "CHILD_ID")
    private String childId;

    public ChildIdIdenEm() {
    }

    public ChildIdIdenEm(String parent, String childId) {
        this.parent = parent;
        this.childId = childId;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildIdIdenEm that = (ChildIdIdenEm) o;
        return Objects.equals(parent, that.parent) && Objects.equals(childId, that.childId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, childId);
    }
}
