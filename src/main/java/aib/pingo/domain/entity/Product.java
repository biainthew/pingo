package aib.pingo.domain.entity;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                        // 상품ID

    @Column(nullable = false, unique = true)
    private String naverProductId;                          // 네이버상품ID

    @Column(nullable = false)
    private long currentPrice;                              // 현재가격

    @Column(nullable = false)
    private String productUrl;                              // 상품URL

    private String productName;                             // 상품명
    private String productDescription;                      // 상품설명
    private String imageUrl;                                // 상품이미지URL
    private boolean active;                                 // 상품수집활성화여부
    private boolean deleted;                                // 삭제여부

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;                        // 등록일시

    @LastModifiedDate
    private LocalDateTime updatedAt;                        // 수정일시
}
