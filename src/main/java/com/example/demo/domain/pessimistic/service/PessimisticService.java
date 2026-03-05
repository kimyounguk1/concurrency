package com.example.demo.domain.pessimistic.service;

import com.example.demo.domain.distribution.entity.Item;
import com.example.demo.domain.distribution.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessimisticService {

    private final ItemJpaRepository itemJpaRepository;


    @Transactional
    public void updateItemProcess(Long id) {

        Item item = itemJpaRepository.findByIdWithPessimisticLock(id).orElseThrow(() -> new RuntimeException("존재하지 않는 아이템"));

        Long stock = item.getStock();

        if(stock>0){
            item.setStock(stock-1);
        }else{
            throw new RuntimeException("재고 부족");
        }

    }
}
