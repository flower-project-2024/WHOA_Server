package com.whoa.whoaserver.flower.controller;

import com.whoa.whoaserver.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flower")
public class FlowerController {

    @Autowired
    private final FlowerService flowerService;

    @GetMapping("detail/{flowerId}")
    public ResponseEntity<?> getFlower(@PathVariable("flowerId") final Long flowerId){
        return ResponseEntity.ok().body(flowerService.getFlower(flowerId));
    }
}