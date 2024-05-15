package com.amalitech.securesail.amalisecuresail.logging;

import com.amalitech.securesail.amalisecuresail.logging.constants.EventType;
import com.amalitech.securesail.amalisecuresail.global.constants.OperationResultConstants;
import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user_logs")
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User performedBy;

    @Enumerated(EnumType.STRING)
    private OperationResultConstants status;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateOccurred;

    private String referrer;

    private String ipAddress;

    private String userAgent;

    private String location;

    private String extraInfo;

}
