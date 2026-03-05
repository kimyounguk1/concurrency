package com.example.demo.domain.distribution.controller;

import com.example.demo.domain.distribution.service.RedissonLockStockFacade;
import com.example.demo.domain.distribution.service.DistributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/distribution")
public class DistributionController {

    private final DistributionService distributionService;
    private final RedissonLockStockFacade redissonLockStockFacade;

    @PatchMapping("/update/{id}")
    public void distributionUpdate(@PathVariable Long id) {
        redissonLockStockFacade.decrease(id, 1);
    }

}
