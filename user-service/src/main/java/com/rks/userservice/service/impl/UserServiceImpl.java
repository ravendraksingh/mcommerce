package com.rks.userservice.service.impl;

import com.rks.mcommon.exception.NotFoundException;
import com.rks.userservice.entities.User;
import com.rks.userservice.entities.UserAddress;
import com.rks.userservice.model.User360DegreeView;
import com.rks.userservice.repository.UserAddressRepository;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.service.UserAddressService;
import com.rks.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rks.mcommon.constants.CommonConstants.FAILED;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.NOT_FOUND_ERROR_CODE;
import static com.rks.userservice.constants.Constants.USER_NOT_FOUND_ERROR_MSG;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAddressRepository userAddressRepository;

    @Override
    public User360DegreeView get360DegreeView(String userName) {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if(!optionalUser.isPresent()) {
            throw new NotFoundException(FAILED, NOT_FOUND_ERROR_CODE, USER_NOT_FOUND_ERROR_MSG);
        }
        User fetchedUser = optionalUser.get();
        User360DegreeView view = new User360DegreeView();
        view.updateView(fetchedUser);

        User360DegreeView finalView = view;
        userAddressRepository.findAll().forEach(a -> finalView.getAddressList().add(a));
        return finalView;
    }
}
