package com.example.demo.domain.atomic.controller;

import com.example.demo.domain.atomic.service.AtomicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atomic")
@RequiredArgsConstructor
public class AtomicController {

    private final AtomicService atomicService;

    @PatchMapping("/update/{id}")
    public void atomicUpdate(@PathVariable Long id) {
        atomicService.atomicUpdateProcess(id);
    }

}
