package com.rks.userservice.service.impl;

import com.rks.mcommon.exception.NotFoundException;
import com.rks.userservice.domain.User;
import com.rks.userservice.domain.User360DegreeView;
import com.rks.userservice.repository.UserAddressRepository;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rks.mcommon.constants.CommonErrorMessageConstants.API_RESPONSE_STATUS_FAILURE;
import static com.rks.userservice.constants.ErrorCodeConstants.USER_NOT_FOUND_ERR_CODE;
import static com.rks.userservice.constants.ErrorMessageConstants.USER_NOT_FOUND_ERR_MSG;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAddressRepository userAddressRepository;

    @Override
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            throw new NotFoundException(API_RESPONSE_STATUS_FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User360DegreeView get360DegreeView(String userName) {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if(!optionalUser.isPresent()) {
            throw new NotFoundException(API_RESPONSE_STATUS_FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        User fetchedUser = optionalUser.get();
        User360DegreeView view = new User360DegreeView();
        view.updateView(fetchedUser);

        User360DegreeView finalView = view;
        userAddressRepository.findAll().forEach(a -> finalView.getAddressList().add(a));
        return finalView;
    }
}
