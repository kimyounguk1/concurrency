# concurrency
동시 주문 환경에서 재고 감소 동시성 제어 전략 비교
1. 프로젝트 개요

동시 요청 환경에서 재고 감소 로직은 Race Condition으로 인해 데이터 정합성 문제가 발생할 수 있다.

예를 들어 동시에 여러 요청이 들어올 경우 다음과 같은 문제가 발생한다.

초기 재고 = 10

Thread A: 재고 조회 → 10
Thread B: 재고 조회 → 10

Thread A: 재고 감소 → 9
Thread B: 재고 감소 → 9

최종 재고 = 9 (정상 결과는 8)

이를 Lost Update 문제라고 하며, 재고 시스템에서는 반드시 해결해야 하는 문제이다.

본 프로젝트에서는 재고 감소 로직에 대해 4가지 동시성 제어 전략을 구현하고 부하 테스트를 통해 성능 및 특성을 비교하였다.

2. 사용 기술

Spring Boot

JPA / Hibernate

MySQL

Redis

Redisson

k6 (부하 테스트)

3. 재고 감소 전략
3.1 Pessimistic Lock (비관적 락)

DB 레벨에서 Row Lock을 걸어 동시에 접근하는 트랜잭션을 차단한다.

@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select i from Item i where i.id = :id")
Item findByIdForUpdate(Long id);

특징

데이터 정합성 보장

동시성 환경에서 DB Lock 경합 발생

TPS 감소 가능성

3.2 Optimistic Lock (낙관적 락)

버전 필드를 이용하여 업데이트 충돌을 감지한다.

@Version
private Long version;

특징

DB 락 없음

충돌 발생 시 재시도 필요

재시도 로직이 없을 경우 성공 보장 어려움

3.3 Redis Distributed Lock (Redisson)

Redis 기반 분산 락을 통해 재고 감소 로직을 보호한다.

RLock lock = redissonClient.getLock("stock_lock");

lock.lock();
try {
    decreaseStock();
} finally {
    lock.unlock();
}

특징

DB Lock 제거

분산 환경에서도 사용 가능

Redis 네트워크 비용 발생

3.4 Atomic SQL Update

DB의 원자적 연산을 이용하여 재고를 감소시킨다.

UPDATE item
SET stock = stock - 1
WHERE id = ?
AND stock > 0

특징

단일 쿼리로 재고 감소 처리

별도의 Lock 필요 없음

높은 성능

4. 부하 테스트 환경

부하 테스트는 k6를 사용하였다.

테스트 시나리오

초기 재고: 100

k6 요청 수: 200

재고 감소 API를 동시에 호출하여
동시성 환경을 생성

즉,

재고 100개
요청 200개

환경에서 재고 감소 로직의 동작을 테스트하였다.

5. 테스트 결과

실험 결과 성능은 다음 순서로 나타났다.

전략	성능 순위
Atomic SQL Update	1
Pessimistic Lock	2
Optimistic Lock	3
Redis Distributed Lock	4
결과 분석

1️⃣ Atomic SQL Update

가장 높은 성능을 보였다.

이유

단일 SQL로 처리

별도의 Lock 없음

DB에서 원자적으로 연산 수행

2️⃣ Pessimistic Lock

두 번째로 빠른 성능을 보였다.

이유

DB Row Lock으로 충돌 방지

Lock 경합은 발생하지만 재시도 비용 없음

3️⃣ Optimistic Lock

세 번째 성능을 보였다.

이유

버전 충돌 발생

재시도 로직이 없을 경우 성공 보장 어려움

충돌로 인해 일부 요청 실패

4️⃣ Redis Distributed Lock

가장 낮은 성능을 보였다.

이유

Redis 네트워크 호출 발생

Lock 획득 및 해제 비용

DB + Redis 두 시스템 사용

6. 결론

동시성 제어 전략에 따라 다음과 같은 특성이 나타났다.

전략	특징
Atomic SQL	가장 높은 성능
Pessimistic Lock	안정적 정합성
Optimistic Lock	충돌 발생 가능
Redis Lock	분산 환경에서 유리

서비스 특성에 따라 성능, 정합성, 분산 환경 여부를 고려하여 동시성 제어 전략을 선택해야 한다.
