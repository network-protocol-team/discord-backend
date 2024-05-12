package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.discord.src.service.dto.ExampleRes;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExampleService {
    public ExampleRes getHello(String name){
        ExampleRes exampleRes = ExampleRes.builder()
                        .message(name+ " hello!")
                        .build();
        log.info("created hello!");
        return exampleRes;
    }

    public ExampleRes getBye(String name){
        if(name.equals("tokki")){
            throw new BaseException(ApiResponseStatus.NOT_BYE_TOKKI_ERROR);
        }

        ExampleRes exampleRes = ExampleRes.builder()
                        .message(name+ " hello!")
                        .build();
        log.info("created Bye!");
        return exampleRes;
    }
}
