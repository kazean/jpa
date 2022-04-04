package pratice.jpa.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    LocalDateTime createdDate;
    LocalDateTime lastModiFiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModiFiedDate() {
        return lastModiFiedDate;
    }

    public void setLastModiFiedDate(LocalDateTime lastModiFiedDate) {
        this.lastModiFiedDate = lastModiFiedDate;
    }
}
