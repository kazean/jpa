package pratice.jpa.test.entity.nonIdentifyingRelationship;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ParentNonEm {
    @EmbeddedId
    private ParentIdNonEm parentId;
    private String name;

    public ParentIdNonEm getParentId() {
        return parentId;
    }

    public void setParentId(ParentIdNonEm parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
