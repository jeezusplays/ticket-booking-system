package user;

public abstract class User {
    private int id;
    // private String username;
    private String password;
    private String email;
    private String type;

    public User(int id, String password, String email, String type) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    // public String getPassword() {
    //     return password;
    // }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }
}
