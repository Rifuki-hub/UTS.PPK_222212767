package com.jastiphimada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jastiphimada.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
