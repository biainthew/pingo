package aib.pingo.domain.entity;

import aib.pingo.domain.enums.AlertSendType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "member_id"}))
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TargetPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // 목표가ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;                    // 물건ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;                      // 사용자ID

    @Column(nullable = false)
    private long targetPrice;                   // 목표가

    @Enumerated(EnumType.STRING)
    private AlertSendType state;                // 알림발송상태

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;            // 등록일시

    @LastModifiedDate
    private LocalDateTime updatedAt;            // 수정일시
}
