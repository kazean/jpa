package pratice.jpa.test.entity.identifyingRelationship;

import javax.persistence.*;

@Entity
public class ChildIdenEm {

    @EmbeddedId
    private ChildIdIdenEm childId;

    @MapsId(value = "parent") @ManyToOne @JoinColumn(name = "PARENT_ID")
    private ParentIdenEm parent;

}
