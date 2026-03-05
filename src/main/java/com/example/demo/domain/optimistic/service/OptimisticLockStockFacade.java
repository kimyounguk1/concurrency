package com.example.demo.domain.optimistic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticService optimisticService;

    public void decrease(Long id, int quantity) throws InterruptedException {

        int count = 0;

        while(count<5){
            try{
                optimisticService.updateOptimisitcProcess(id);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                count ++;
                Thread.sleep(50);
            }
        }

        throw new RuntimeException("낙관적 락 재시도 로직 전부 실패");

    }

}
