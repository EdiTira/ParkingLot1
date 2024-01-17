package com.parking.parkinglot.common;

public class UserGroupDto {
    private Long id;

    private String username;

    private String userGroup;

    public UserGroupDto(Long id, String username, String userGroup) {
        this.id = id;
        this.username = username;
        this.userGroup = userGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
