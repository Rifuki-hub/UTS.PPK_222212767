/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jastiphimada.service;

import java.util.List;
import com.jastiphimada.dto.UserDto;

/**
 *
 * @author rifky
 */
public interface UserService {

    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long id);

    public UserDto createUser(UserDto user);

    public UserDto getUserByEmail(String email);

    public UserDto updateUser(UserDto userDto);

    public UserDto updateUserById(Long userId, UserDto userDto);

    public void deleteUserById(Long userId);
            
    void deleteUserByEmail(String email);

    public boolean verifyOldPassword(String email, String oldPassword);
    
    public void changePassword(String email, String newPassword);
}
