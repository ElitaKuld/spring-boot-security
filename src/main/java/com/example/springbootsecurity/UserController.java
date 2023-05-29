package com.example.springbootsecurity;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class UserController {
    @GetMapping("whoami")
    public String whoami(Principal principal) {
        return principal.getName();
    }
}