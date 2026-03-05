package com.example.demo.domain.pessimistic.controller;

import com.example.demo.domain.pessimistic.service.PessimisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessimisitc")
@RequiredArgsConstructor
public class PessimisticController {

    private final PessimisticService pessimisticService;

    @PatchMapping("/update/{id}")
    public void updateItem (@PathVariable("id") Long id) {
        pessimisticService.updateItemProcess(id);
    }

}
