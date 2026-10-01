package com.tele.telefood;

public class LoginRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
