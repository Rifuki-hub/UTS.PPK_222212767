/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jastiphimada.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jastiphimada.dto.UserDto;
import com.jastiphimada.entity.User;
import com.jastiphimada.exception.EmailAlreadyExistsException;
import com.jastiphimada.exception.UserNotFoundException;
import com.jastiphimada.mapper.UserMapper;
import com.jastiphimada.repository.UserRepository;

/**
 *
 * @author rifky
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        try {
            // Enkripsi password
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            if (userDto.getRole() == null) {
                userDto.setRole("USER");
            }

            // Mencoba menyimpan user baru
            User user = userRepository.save(UserMapper.mapToUser(userDto));
            return UserMapper.mapToUserDto(user);
        } catch (DataIntegrityViolationException e) {
            // Menangani pelanggaran constraint unik pada email
            throw new EmailAlreadyExistsException("Email sudah terdaftar.");
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userDto.getId()));

        // Update data sesuai dengan yang diterima di userDto
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        // Pastikan untuk mengenkripsi password jika diperbarui
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Simpan perubahan ke database
        User updatedUser = userRepository.save(user);

        // Konversi User ke UserDto dan kembalikan
        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public UserDto updateUserById(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Set data yang akan di-update
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());  // Pastikan password sudah dienkripsi di sini
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());          // Admin bisa mengubah peran pengguna

        // Simpan perubahan ke database
        User updatedUser = userRepository.save(user);

        // Konversi dan kembalikan UserDto
        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }
    
    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        // Ambil semua pengguna dari repository
        List<User> allUsers = userRepository.findAll();

        // Pemetaan setiap User ke UserDto
        return allUsers.stream()
                .map(UserMapper::mapToUserDto) // Pemetaan User ke UserDto
                .collect(Collectors.toList()); // Mengumpulkan hasilnya dalam List<UserDto>
    }

    @Override
    public UserDto getUserById(Long userId) {
        // Cari user berdasarkan ID
        Optional<User> userOptional = userRepository.findById(userId);

        // Jika user ditemukan, map ke UserDto dan return
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserMapper.mapToUserDto(user);
        }

        // Jika user tidak ditemukan, bisa throw exception atau return null
        throw new UserNotFoundException("User not found with ID: " + userId);
    }

    @Override
    public boolean verifyOldPassword(String email, String oldPassword) {
        User user = userRepository.findByEmail(email);
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void changePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
