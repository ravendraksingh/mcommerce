package com.rks.userservice.service.impl;

import com.rks.mcommon.exception.NotFoundException;
import com.rks.userservice.domain.User;
import com.rks.userservice.domain.UserAddress;
import com.rks.userservice.repository.UserAddressRepository;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.service.UserAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rks.mcommon.constants.CommonConstants.FAILED;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.NOT_FOUND_ERROR_CODE;
import static com.rks.userservice.constants.ErrorMessageConstants.USER_NOT_FOUND_ERR_MSG;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private static final Logger logger = LoggerFactory.getLogger(UserAddressServiceImpl.class);
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAddress create(UserAddress userAddress) {
        String userName = userAddress.getUserName();
        Optional<User> optionalUser = userRepository.findByUsername(userName);

        if(!optionalUser.isPresent()) {
            throw new NotFoundException(FAILED, NOT_FOUND_ERROR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        UserAddress response = userAddressRepository.save(userAddress);
        return response;
    }
}
