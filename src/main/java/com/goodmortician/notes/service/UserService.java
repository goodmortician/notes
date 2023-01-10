package com.goodmortician.notes.service;

import com.goodmortician.notes.domain.Role;
import com.goodmortician.notes.domain.User;
import com.goodmortician.notes.repository.RoleRepository;
import com.goodmortician.notes.repository.UserRepository;
import com.goodmortician.notes.web.dto.UserDto;
import com.goodmortician.notes.web.dto.UserDtoCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserDto createUser (UserDtoCreate dto) {
        User user = buildUserEntity (dto);
        User savedEntity = userRepository.save (user);
        return buildUserDto (savedEntity);
    }
    private User buildUserEntity (UserDtoCreate dto){
        User user = new User ();
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List <Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setActive(dto.getActive());
        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRoles(userRoles);
        return user;
    }
    private UserDto buildUserDto (User savedEntity){

        UserDto result = new UserDto();
        result.setLogin(savedEntity.getLogin());
        result.setActive(savedEntity.getActive());
        result.setFirstName(savedEntity.getFirstName());
        result.setLastName(savedEntity.getLastName());
        return result;
    }
    public UserDto getOneUser (Integer id) {
        User entity = userRepository.getReferenceById(id);
        return buildUserDto(entity);
    }
    public List<UserDto> getAllUsers(){
        List <User> userList = userRepository.findAll();
        return userList
                .stream()
                .map(this::buildUserDto)
                .collect(Collectors.toList());
    }
    public UserDto updateUser (Integer id, UserDto updateRequest)
    {
        User entity = userRepository.getReferenceById(id);
        entity.setLogin(updateRequest.getLogin());
        entity.setActive(updateRequest.getActive());
        entity.setFirstName(updateRequest.getFirstName());
        entity.setLastName(updateRequest.getLastName());
        userRepository.save(entity);
        return buildUserDto(entity);
    }
    public void deleteUser (Integer id) {userRepository.deleteById(id);}

}