package com.example.studentapartmentms.controller;


import com.example.studentapartmentms.pojo.User;
import com.example.studentapartmentms.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping
    public boolean addUser(String name, String id, String password,
                          String role, String phone,
                          String gender, LocalDate birth
    ) {
        return userService.addUser(name, id, password, role, phone, gender, birth);
    }

    @GetMapping
    public List<User> allUser() {
        return userService.allUser();
    }

}
