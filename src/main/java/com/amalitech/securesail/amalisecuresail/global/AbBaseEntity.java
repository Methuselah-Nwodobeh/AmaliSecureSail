package com.amalitech.securesail.amalisecuresail.global;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ab_base_entity")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AbBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modified", nullable = false)
    @UpdateTimestamp
    private LocalDateTime dateModified;

    @LastModifiedBy
    private String modifiedBy;

    @Override
    public boolean equals(Object o) {
        AbBaseEntity that = null;
        if (o.getClass() == AbBaseEntity.class) {
            that = (AbBaseEntity) o;
        }
        if (this == o) return true;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateproxy ? hibernateproxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateproxy ? hibernateproxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        if (that == null) return false;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy hibernateproxy ? hibernateproxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
