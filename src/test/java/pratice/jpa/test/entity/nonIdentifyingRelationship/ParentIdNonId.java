package pratice.jpa.test.entity.nonIdentifyingRelationship;

import java.io.Serializable;
import java.util.Objects;

public class ParentIdNonId implements Serializable {
    private String parentId1;
    private String parentId2;

    public ParentIdNonId() {
    }

    public ParentIdNonId(String parentId1, String parentId2) {
        this.parentId2 = parentId1;
        this.parentId2 = parentId2;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentIdNonId that = (ParentIdNonId) o;
        return Objects.equals(parentId1, that.parentId1) && Objects.equals(parentId2, that.parentId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId1, parentId2);
    }
}
