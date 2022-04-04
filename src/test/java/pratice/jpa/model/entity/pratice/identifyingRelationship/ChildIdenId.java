package pratice.jpa.model.entity.pratice.identifyingRelationship;

import javax.persistence.*;

@Entity
@IdClass(ChildIdIdenId.class)
public class ChildIdenId {
    @Id @ManyToOne @JoinColumn(name = "PARENT_ID")
    private ParentIdenId parent;

    @Id
    @Column(name = "CHILD_ID")
    private String childId;


}
