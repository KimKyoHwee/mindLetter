package com.likelion.letterBox.controller;

import com.likelion.letterBox.domain.User;
import com.likelion.letterBox.dto.PostBoxRequestDto;
import com.likelion.letterBox.dto.PostBoxReturnDto;
import com.likelion.letterBox.service.PostBoxService;
import com.likelion.letterBox.service.UserService;
import com.likelion.letterBox.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/postbox")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostBoxController {

    private final PostBoxService postBoxService;
    private final UserService userService;

    //닉네임 , 기본 디자인으로 우체통 생성

    @ApiOperation(value = "우체통 만들기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "실패")
    })
    @PostMapping
    public ResponseEntity<?> createPostBox(Authentication authentication,
                                           @RequestBody PostBoxRequestDto postBoxRequestDto){
        String email= authentication.getName();
        User user=userService.returnUser(email);
        postBoxService.createPostBox(user, postBoxRequestDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "내 우체통 보기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "실패")
    })
    @GetMapping
    public ResponseEntity<PostBoxReturnDto> getPostBox(Authentication authentication){
        String email= authentication.getName();
        User user=userService.returnUser(email);
        return ResponseEntity.ok().body(postBoxService.returnPostBoxDto(user));
    }
}
