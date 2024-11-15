/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jastiphimada.exception;

/**
 *
 * @author rifky
 */
public class EmailAlreadyExistsException extends RuntimeException {

    // Konstruktor dengan pesan kustom
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    // Konstruktor dengan pesan kustom dan throwable
    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

