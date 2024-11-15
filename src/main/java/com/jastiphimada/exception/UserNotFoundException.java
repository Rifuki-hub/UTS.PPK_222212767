/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jastiphimada.exception;

public class UserNotFoundException extends RuntimeException {

    // Constructor dengan pesan khusus
    public UserNotFoundException(String message) {
        super(message);
    }

    // Constructor dengan pesan dan penyebab exception lainnya
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor tanpa argumen
    public UserNotFoundException() {
        super("User not found");
    }
}