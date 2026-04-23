package aib.pingo.domain.entity;

import aib.pingo.domain.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // 사용자ID

    @Column(nullable = false, unique = true)
    private String email;                               // 이메일

    @Column(nullable = false)
    private String password;                            // 비밀번호

    @Column(nullable = false)
    private String name;                                // 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;                            // 역할

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;                    // 등록일시

    @LastModifiedDate
    private LocalDateTime updatedAt;                    // 수정일시
}
