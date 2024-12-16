package net.hamdi.securityservice.dto;

public class TokenDetails {
    
    private String userId;
    private String roles;
    private boolean active;

    // Getters, setters, et constructeur
    public TokenDetails(String userId, String roles, boolean active) {
        this.userId = userId;
        this.roles = roles;
        this.active = active;
    }

    // Getters et setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
