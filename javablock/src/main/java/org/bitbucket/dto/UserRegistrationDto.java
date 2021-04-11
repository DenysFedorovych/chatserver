package org.bitbucket.dto;

public class UserRegistrationDto {

    private String login;

    private String password;

    private String confirmPassword;

    private String email;

    private String phone;

    public UserRegistrationDto(String login, String password, String confirmPassword, String email, String phone) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

}
