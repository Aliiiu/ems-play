package models;

public enum Role {
    ADMIN("admin"),
    EMPLOYEE("employee");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role fromString(String role){
        switch(role.toLowerCase()){
            case "employee":
                return EMPLOYEE;
            case "admin":
                return ADMIN;
            default:
                return null;
        }
    }
}




