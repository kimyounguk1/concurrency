package com.example.demo.domain.distribution.service;

import com.example.demo.domain.distribution.entity.DisItem;
import com.example.demo.domain.distribution.repository.DisItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DistributionService {

    private final DisItemJpaRepository disItemJpaRepository;

    @Transactional
    public void distributionUpdateProcess(Long id) {

        DisItem disItem = disItemJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 아이템"));

        Long quantity = disItem.getStock();

        if(quantity>=1){
            disItem.setStock(quantity-1);
        }else{
            throw new RuntimeException("재고 부족");
        }


    }

}
