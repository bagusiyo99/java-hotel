package com.hotel.services.authentication;

import com.hotel.dto.SignupRequestDTO;
import com.hotel.dto.UserDto;
import com.hotel.entity.User;

public interface AuthService {
    UserDto signupClient(SignupRequestDTO signupRequestDTO);
    Boolean presentByEmail (String email);

    UserDto signupCompany(SignupRequestDTO signupRequestDTO);

}
