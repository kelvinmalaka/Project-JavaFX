public class User {
    private String username;
    private String email;
    private String password;
    private String role;  // Role: Admin or User
    private String userID;

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserID() {
        return userID;
    }

    public void generateUserID() {
        // Format: USXXX (X: Digit 0-9)
        userID = String.format("US%03d", Database.fetchUsers().size() + 1);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getRole() {
		  return role;
	}
}
