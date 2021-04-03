package com.rks.userservice.service.impl;

import com.rks.userservice.domain.User;
import com.rks.userservice.domain.User360DegreeView;
import com.rks.userservice.exception.NotFoundException;
import com.rks.userservice.repository.UserAddressRepository;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rks.userservice.constants.UserServiceConstants.FAILURE;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.USER_NOT_FOUND_ERR_MSG;
import static com.rks.userservice.constants.UserServiceErrorCodes.USER_NOT_FOUND_ERR_CODE;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    UserRepository userRepository;
    UserAddressRepository userAddressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserAddressRepository userAddressRepository) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            throw new NotFoundException(FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteUserByEmail(String email) {
        boolean userExist = userRepository.existsByEmail(email);
        if(!userExist) {
            throw new NotFoundException(FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        userRepository.deleteByEmail(email);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        User fetchedUser = optionalUser.get();
        return Optional.ofNullable(fetchedUser);
    }

    @Override
    public Optional<User> getSecureUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        User fetchedUser = optionalUser.get();
        return Optional.ofNullable(fetchedUser);
    }

    @Override
    public User360DegreeView get360DegreeView(String userName) {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if(!optionalUser.isPresent()) {
            throw new NotFoundException(FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        User fetchedUser = optionalUser.get();
        User360DegreeView view = new User360DegreeView();
        view.updateView(fetchedUser);

        User360DegreeView finalView = view;
        userAddressRepository.findAll().forEach(a -> finalView.getAddressList().add(a));
        return finalView;
    }
}
