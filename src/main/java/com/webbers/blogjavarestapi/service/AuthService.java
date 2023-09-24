package com.webbers.blogjavarestapi.service;

import com.webbers.blogjavarestapi.payload.LoginDto;
import com.webbers.blogjavarestapi.payload.RegistrationDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegistrationDto registrationDto);
}
