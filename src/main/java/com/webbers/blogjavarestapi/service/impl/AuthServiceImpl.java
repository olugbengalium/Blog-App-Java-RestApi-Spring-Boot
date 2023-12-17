package com.webbers.blogjavarestapi.service.impl;

import com.webbers.blogjavarestapi.entity.Role;
import com.webbers.blogjavarestapi.entity.User;
import com.webbers.blogjavarestapi.exceptions.BlogAPIException;
import com.webbers.blogjavarestapi.payload.LoginDto;
import com.webbers.blogjavarestapi.payload.RegistrationDto;
import com.webbers.blogjavarestapi.repository.RoleRepository;
import com.webbers.blogjavarestapi.repository.UserRepository;
import com.webbers.blogjavarestapi.security.JwtTokenProvider;
import com.webbers.blogjavarestapi.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

//    @Override
//    public String login(LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.getUsernameOrEmail(),loginDto.getPassword()
//        ));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtTokenProvider.generateToken(authentication);
//        return token;
//    }
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegistrationDto registrationDto) {
        //check if username already exists in database!!!
        if(userRepository.existsByUsername(registrationDto.getUsername()) || userRepository.existsByEmail(registrationDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username/Email already exists");
        }
        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Set<Role> roles = new HashSet<>();
//////        Ro    le userRole = roleRepository.findByName("ROLE_USER").get();
//////        roles.add(userRole);
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
//        roler.ifPresent(roles::add);

        if (userRole.isPresent()){
            roles.add(userRole.get());
            userRepository.save(user);
            return "new user registered successfully!!!";
        }
        else {
            return "role not found";
        }
    }
}
