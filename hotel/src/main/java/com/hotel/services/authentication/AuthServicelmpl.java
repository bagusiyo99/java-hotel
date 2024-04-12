package com.hotel.services.authentication;

import com.hotel.dto.SignupRequestDTO;

import com.hotel.dto.UserDto;
import com.hotel.entity.User;
import com.hotel.enums.UserRole;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServicelmpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode( signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    public Boolean presentByEmail (String email) {
        return userRepository.findFirstByEmail (email) != null;
    }

    public UserDto signupCompany (SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setEmail(signupRequestDTO.getPhone());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode( signupRequestDTO.getPassword()));
        user.setRole(UserRole.COMPANY);

        return userRepository.save(user).getDto();
    }
}
