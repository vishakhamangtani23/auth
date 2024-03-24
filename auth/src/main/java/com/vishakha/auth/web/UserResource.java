package com.vishakha.auth.web;

import ch.qos.logback.classic.spi.ConfiguratorRank;
import com.vishakha.auth.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api")
public class UserResource {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, Object> body) {
        return userService.registerUser(body);
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody Map<String, Object> body) {
        System.out.println(userService.loginUser(body));
        return userService.loginUser(body);
    }

    @GetMapping("/students")
    public ResponseEntity<List<String>> getStudentNames(HttpServletRequest httpServletRequest) {
        return userService.getStudentNames(httpServletRequest);
    }

    @PostMapping("/sendMail")
    public void validateAndSendMail(@RequestBody Map<String, Object> body) {
        userService.validateAndSendMail(body);
    }
    @PostMapping("/validateFPToken")
    public ResponseEntity<Map<String,Object>> validateFPToken(@RequestBody Map<String,Object> body)
    {
        return userService.validateFPToken(body);
    }

}

