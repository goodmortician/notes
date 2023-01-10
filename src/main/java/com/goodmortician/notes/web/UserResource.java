package com.goodmortician.notes.web;

import com.goodmortician.notes.service.UserService;
import com.goodmortician.notes.web.dto.UserDto;
import com.goodmortician.notes.web.dto.UserDtoCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserResource {
    private final UserService userService;
    @PostMapping()
    public UserDto createUser (@RequestBody UserDtoCreate createUser){
        return userService.createUser(createUser);
    }
    @GetMapping(value = "/{id}")
    public UserDto getOneUser (@PathVariable (name = "id") Integer id) {return userService.getOneUser(id);}
    @GetMapping ()
    public List<UserDto> getAll (){ return userService.getAllUsers();}
    @PutMapping (value = "/{id}")
    public UserDto updateUser (@PathVariable (name = "id") Integer id, @RequestBody UserDto updateUser){
        return userService.updateUser(id, updateUser);}
    @DeleteMapping (value = "/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable (name = "id") Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}