package com.sasa.user_service.domain.model;

public enum UserRoles {
    admin,
    user;

    public static UserRoles fromString(String role) {
        if (role == null) {
            return null;
        }
        for (UserRoles r : values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }
}
