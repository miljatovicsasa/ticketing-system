package com.sasa.user_service.domain.model;

public class User {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserRoles role;

    public User(Long id, String username, String email, String firstName, String lastName, UserRoles role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public UserRoles getRole() { return role; }

    public void setRole(UserRoles role) { this.role = role; }


    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(String.valueOf(role));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}
