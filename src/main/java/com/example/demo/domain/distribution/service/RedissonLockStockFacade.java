package com.example.demo.domain.distribution.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final DistributionService distributionService;

    //분산락의 범위가 트랜잭션 보다 항상 커야함
    //why? db가 아닌 redis가 락을 갖고 있기 떄문에 unlock -> 커밋 순서면
    // 반영전의 값이 조회 가능
    public void decrease(Long itemId, int quantity ) {
        RLock lock = redissonClient.getLock(itemId.toString());

        try{
            //최대 10초, 1초간 유지
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if(!available) {
                throw new RuntimeException("락 점유 타임아웃");
            }
            //트랜잭션의 종료 시점
            distributionService.distributionUpdateProcess(itemId);

        }catch (InterruptedException e){
            throw new RuntimeException("대기 중 인터럽트");
        }finally{
            //락 해제는 필수
            if(lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
