package com.amalitech.securesail.amalisecuresail.authentication.models;

import com.amalitech.securesail.amalisecuresail.authentication.AuthenticationMethod;
import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String sessionToken;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private Duration duration;
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;
    @Enumerated(EnumType.STRING)
    private AuthenticationMethod authenticationMethod;
    @Enumerated(EnumType.STRING)
    private SessionStatus status;
    @ManyToOne
    @JoinColumn(name = "device_info_id")
    private DeviceInfo deviceInfo;
    @ManyToOne
    @JoinColumn(name = "browser_info_id")
    private BrowserInfo browserInfo;
    private String referrer;
    private String ipAddress;
    private String userAgent;
    private String location;
    private LocalDateTime dateExpiry;
}
