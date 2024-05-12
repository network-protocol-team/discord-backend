package com.example.discord.src.controller;

import com.example.discord.common.response.ApiResponse;
import com.example.discord.src.service.ExampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.discord.src.service.dto.ExampleRes;

@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/hello")
    public ApiResponse<ExampleRes> getHello(){
        ExampleRes exampleRes = exampleService.getHello("gosumdochi");
        ApiResponse<ExampleRes> res = new ApiResponse<>(exampleRes);
        return res;
//        return new ApiResponse<>(exampleRes);
    }

    @GetMapping("/bye")
    public ApiResponse<ExampleRes> getBye(){
        ExampleRes exampleRes = exampleService.getBye("tokki");
        return new ApiResponse<>(exampleRes);
    }
}
