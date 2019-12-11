package com.rks.userservice.model;

import com.rks.userservice.entities.User;
import com.rks.userservice.entities.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User360DegreeView {
    private String userName;
    private String name;
    private String email;
    List<UserAddress> addressList = new ArrayList<>();

    public void updateView(User user) {
        this.setName(user.getName());
        this.setUserName(user.getUsername());
        this.setEmail(user.getEmail());
    }

}
