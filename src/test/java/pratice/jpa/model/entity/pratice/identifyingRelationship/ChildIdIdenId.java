package pratice.jpa.model.entity.pratice.identifyingRelationship;

import java.io.Serializable;
import java.util.Objects;

public class ChildIdIdenId implements Serializable {
    private String parent;
    private String childId;

    public ChildIdIdenId() {
    }

    public ChildIdIdenId(String parentId, String childId) {
        this.parent = parentId;
        this.childId = childId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildIdIdenId childId1 = (ChildIdIdenId) o;
        return Objects.equals(parent, childId1.parent) && Objects.equals(childId, childId1.childId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, childId);
    }
}
