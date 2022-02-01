package ua.balu.toyshop.security.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.balu.toyshop.dto.user.UserEntity;
import ua.balu.toyshop.dto.user.UserLogIn;
import ua.balu.toyshop.security.JwtTokenProvider;
import ua.balu.toyshop.security.service.EncoderService;

@Service
public class EncoderServiceImpl implements EncoderService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EncoderServiceImpl(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isValidPassword(UserLogIn userLogin, UserEntity userEntity) {
        return passwordEncoder.matches(userLogin.getPassword(),userEntity.getPassword());
    }

    @Override
    public boolean isValidStatus(UserEntity userEntity) {
        return userEntity.isStatus();
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
