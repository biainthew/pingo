# Pingo

> 실시간 가격 변동 알림 서비스

네이버 쇼핑 API 기반으로 관심 상품의 가격을 주기적으로 수집하고 목표 가격 이하로 떨어지면 SSE와 이메일로 알림을 발송합니다.

---

## 개발 목적

실무에서 경험하지 못했던 Spring Batch, Resilience4j Circuit Breaker, 비동기 처리(@Async)를 직접 설계하고 적용하기 위해 진행한 개인 프로젝트입니다.

---

## 핵심 기능

- 회원가입 / 로그인 (JWT 인증)
- 네이버 쇼핑 API 기반 상품 등록
- 목표 가격 설정 (이하 조건)
- Spring Batch 기반 30분 주기 가격 수집
- 목표 가격 달성 시 SSE + 이메일 알림 발송
- 가격 이력 그래프 조회

---

## 기술 스택

### Backend
- Java 17, Spring Boot 3.3
- Spring Data JPA, QueryDSL
- Spring Security, JWT
- Spring Batch
- Resilience4j Circuit Breaker
- Redis
- MySQL

### Frontend
- React 18, TypeScript
- Vite, Tailwind CSS
- Recharts (가격 이력 그래프)

### Infra
- AWS EC2, RDS, S3, CloudFront, Route 53
- Nginx, Let's Encrypt
- GitHub Actions CI/CD
- Docker (로컬 Redis)

---

## 아키텍처

```
사용자
  ↓ HTTPS
CloudFront → S3 (React)

사용자
  ↓ HTTPS (api.도메인)
Nginx → EC2 (Spring Boot)
  ├── Redis          — 캐싱 / 중복 알림 방지
  ├── RDS MySQL      — 상품 / 알림 조건 / 가격 이력
  ├── Spring Batch   — 30분 주기 가격 수집 및 조건 판단
  └── 네이버 쇼핑 API — 상품 가격 데이터 소스
```

---

## 구현 포인트

| 주제 | 내용 |
|------|------|
| Spring Batch | 청크 기반 대량 상품 가격 수집, Skip/Retry 전략 |
| Resilience4j | 네이버 API 장애 시 Circuit Breaker로 자동 차단 및 복구 |
| @Async | Batch 처리와 알림 발송을 별도 스레드 풀로 분리 |
| Redis | 최근 수집 가격 캐싱, 중복 알림 방지 |

---

## ERD

| 테이블 | 설명 |
|--------|------|
| Member | 회원 정보 |
| Product | 수집 대상 상품 (네이버 상품 기준 공유 엔티티) |
| PriceHistory | 수집 시각별 가격 이력 |
| TargetPrice | 사용자별 목표 가격 조건 |
| AlertHistory | 알림 발송 이력 |