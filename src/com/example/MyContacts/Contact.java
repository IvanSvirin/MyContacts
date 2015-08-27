package com.example.MyContacts;

import java.io.Serializable;

/**
 * Created by ivan on 30.07.2015.
 */
public class Contact implements Serializable{
    private String name;
    private String birth;
    private String homePhone;
    private String workPhone;
    private String email;
    private boolean avatarExists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAvatarExists() {
        return avatarExists;
    }

    public void setAvatarExists(boolean avatarExists) {
        this.avatarExists = avatarExists;
    }
}
