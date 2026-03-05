package com.example.demo.domain.optimistic.service;

import com.example.demo.domain.distribution.entity.Item;
import com.example.demo.domain.distribution.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticService {

    private final ItemJpaRepository itemJpaRepository;

    //트랜잭션 내부에서 재시도 로직을 작성해는 안됨
    @Transactional
    public void updateOptimisitcProcess(Long id) {

        Item item = itemJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 아이템"));

        Long stock = item.getStock();

        if(stock>=1){
            item.setStock(stock-1);
        }else{
            throw new RuntimeException("재고 부족");
        }

    }
}
