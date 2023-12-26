package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class AdminRestCtrl {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //--------------------------------------------------------------------

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) throws Exception {
        User user = userService.getUser(id);
        if (user == null){
            throw new Exception("There is no user with ID = " + id + " in Database");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<List<User>> addNewUser(@RequestBody User user) {
        userService.createOrUpdateUser(user);
        return ResponseEntity.ok(userService.getAllUsers());
    }

//    @PostMapping("saveUser")
//    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role") String roleName) {
//        Role role = roleService.getRoleByName(roleName);
//        Set<Role> roles = new HashSet<>(Collections.singletonList(role));
//        user.setRoles(roles);
//        userService.createOrUpdateUser(user);
//        return "redirect:/admin/users";
//    }

    @PutMapping()
    public ResponseEntity<List<User>> updateUser(@RequestBody User user) {
        if (user.getPassword() == null)
            user.setPassword(userService.getUser(user.getId()).getPassword());
        userService.updateUser(user);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
