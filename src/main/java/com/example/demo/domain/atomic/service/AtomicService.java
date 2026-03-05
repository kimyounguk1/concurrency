package com.example.demo.domain.atomic.service;

import com.example.demo.domain.distribution.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AtomicService {

    private final ItemJpaRepository itemJpaRepository;

    @Transactional
    public void atomicUpdateProcess (Long id){

        int stock = ThreadLocalRandom.current().nextInt(1, 11);

        int i = itemJpaRepository.updateItemStockById(id, 1);

        if(i==0){
            throw new RuntimeException("재고 부족");
        }

    }

}
