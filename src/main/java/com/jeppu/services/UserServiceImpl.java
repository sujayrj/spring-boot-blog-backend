package com.jeppu.services;

import com.jeppu.entities.User;
import com.jeppu.exceptions.ResourceNotFoundException;
import com.jeppu.payloads.UserDTO;
import com.jeppu.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = dtoToEntityUser(userDTO);
        User savedUser = userRepo.save(user);
        return entityToUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setAbout(userDTO.getAbout());

        User savedUser = userRepo.save(user);
        return entityToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return entityToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream().map(this::entityToUserDTO).toList();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepo.delete(user);
    }

    private User dtoToEntityUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
//        User user = new User();
//        user.setId(userDTO.getId());
//        user.setName(userDTO.getName());
//        user.setPassword(userDTO.getPassword());
//        user.setEmail(userDTO.getEmail());
//        user.setAbout(userDTO.getAbout());
        return user;
    }

    private UserDTO entityToUserDTO(User user) {
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setAbout(user.getAbout());
//        userDTO.setName(user.getName());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
