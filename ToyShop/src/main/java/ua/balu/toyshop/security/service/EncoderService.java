package ua.balu.toyshop.security.service;

import ua.balu.toyshop.dto.user.UserEntity;
import ua.balu.toyshop.dto.user.UserLogIn;

public interface EncoderService {
    boolean isValidPassword(UserLogIn userLogin, UserEntity userEntity);

    boolean isValidStatus(UserEntity userEntity);

    String encodePassword(String rawPassword);
}
