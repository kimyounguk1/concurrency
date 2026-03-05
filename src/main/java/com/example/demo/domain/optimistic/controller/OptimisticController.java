package com.example.demo.domain.optimistic.controller;

import com.example.demo.domain.optimistic.service.OptimisticLockStockFacade;
import com.example.demo.domain.optimistic.service.OptimisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/optimistic")
public class OptimisticController {

    private final OptimisticService optimisticService;
    private final OptimisticLockStockFacade optimisticLockStockFacade;

    @PatchMapping("/update/{id}")
    public void updateOptimistic(@PathVariable Long id) throws InterruptedException {

        optimisticLockStockFacade.decrease(id, 1);

    }

}
