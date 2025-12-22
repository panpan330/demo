package com.example.demo.controller;

import com.example.demo.entity.LocBase;
import com.example.demo.service.LocBaseService; // 👈 改用 Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "*")
public class LocBaseController {

    @Autowired
    private LocBaseService locBaseService; // 👈 注入 Service

    @GetMapping("/bases")
    public List<LocBase> getAllBases() {
        return locBaseService.findAllBases(); // 👈 调用 Service
    }
}