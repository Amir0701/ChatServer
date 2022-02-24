package com.lambakean.representation.controller;

import com.lambakean.data.model.Chat;
import com.lambakean.domain.service.UserService;
import com.lambakean.representation.dto.UserDto;
import com.lambakean.representation.dto.UserSecurityTokensDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserSecurityTokensDto> register(@RequestBody @Valid UserDto example,
                                                          BindingResult bindingResult)
            throws ExecutionException, InterruptedException {

        UserSecurityTokensDto result = userService.register(example, bindingResult).get();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/session")
    public ResponseEntity<UserSecurityTokensDto> login(@RequestBody UserDto credentials) {
        return ResponseEntity.ok(userService.login(credentials));
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }


    @PutMapping
    public ResponseEntity<UserDto> change(@RequestBody @Valid UserDto user, BindingResult bindingResult){
        return ResponseEntity.ok(userService.change(user, bindingResult));
    }
}
