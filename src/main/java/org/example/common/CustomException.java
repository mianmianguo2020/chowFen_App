package org.example.common;

/**
 * @author Christy Guo
 * @create 2023-03-28 1:11 AM
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
