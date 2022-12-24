package com.jeppu.controllers;

import com.jeppu.payloads.ApiResponse;
import com.jeppu.payloads.UserDTO;
import com.jeppu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //POST - create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createUserDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
    }
    //PUT - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Long id){
        UserDTO updatedUserDTO = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.CREATED);
    }
    //DELETE - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long id){
        this.userService.deleteUser(id);
        //return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", Boolean.TRUE), HttpStatus.OK);
    }

    //GET - get user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long id){
        UserDTO userFromDB = this.userService.getUserById(id);
        return new ResponseEntity<>(userFromDB, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
