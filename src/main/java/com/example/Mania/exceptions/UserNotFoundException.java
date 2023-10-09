package com.example.Mania.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A task with this name already exists")
public class UserNotFoundException extends RuntimeException {
}
