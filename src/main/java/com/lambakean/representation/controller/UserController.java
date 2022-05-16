package com.lambakean.representation.controller;

import com.lambakean.data.model.Chat;
import com.lambakean.domain.service.UserService;
import com.lambakean.representation.dto.PasswordDto;
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
    public ResponseEntity<UserSecurityTokensDto> login(@RequestBody UserDto credentials)
            throws ExecutionException, InterruptedException {

        UserSecurityTokensDto result = userService.login(credentials).get();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> change(@PathVariable Long id, @RequestBody @Valid UserDto user, BindingResult bindingResult){
        return ResponseEntity.ok(userService.change(user, bindingResult));
    }

    @PutMapping(value = "/{id}", params = "change")
    public ResponseEntity<UserDto> updatePassword(@PathVariable Long id, @RequestParam boolean change ,@RequestBody @Valid PasswordDto passwordDto,
                                                  BindingResult bindingResult){
        return ResponseEntity.ok(userService.changePassword(passwordDto, bindingResult));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping(params = "nickname")
    public ResponseEntity<UserDto> getUser(@RequestParam String nickname){
        return ResponseEntity.ok(userService.getUser(nickname));
    }

    @GetMapping(params = "chatId")
    public ResponseEntity<UserDto[]> getUsersByChatId(@RequestParam Long chatId){
        return ResponseEntity.ok(userService.getUsersByChatId(chatId));
    }
}
