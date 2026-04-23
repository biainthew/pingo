package aib.pingo.domain.entity;

import aib.pingo.domain.enums.AlertType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AlertHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // 알림히스토리ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_price_id", nullable = false)
    private TargetPrice targetPrice;            // 목표가ID

    @Column(nullable = false)
    private long sentPrice;                     // 발송가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;                     // 알림방식

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;            // 등록일시
}
