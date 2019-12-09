package com.rks.userservice.bootstrap;

import com.rks.userservice.repository.RoleRepository;
import com.rks.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    //@Autowired
    //private AccountService accountService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        //doInit();
    }

    private void doInit() {
        log.info("Deleting all users and all roles");
        //deleteAllUsersAndRoles();
        createFewRolesIfNoneExist();
        createFewUsersIfNoneExist();
        tryLogin();
    }

    private void tryLogin() {
        String username = "ravendra";
        String password = "1234";

        //System.out.println("trying to login with username: "+ username);
        //JWTClientExample.postLogin(username, password);
    }

    private void deleteAllUsersAndRoles() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private void createFewRolesIfNoneExist() {
        long count = roleRepository.count();
        if (count == 0) {
            //roleRepository.save(new AppRole("ADMIN"));
            //roleRepository.save(new AppRole("USER"));
        }
    }

    private void createFewUsersIfNoneExist() {
        long count = userRepository.count();
        if (count == 0) {

        }
    }
}
