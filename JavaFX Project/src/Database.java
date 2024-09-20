import java.util.ArrayList;

public class Database {
    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> fetchUsers() {
        return new ArrayList<>(users);
    }

    public static void addUser(User user) {
        // Pastikan user memiliki peran yang valid sebelum ditambahkan
        if (user.getRole() != null && (user.getRole().equals("admin") || user.getRole().equals("user"))) {
            users.add(user);
        } else {
            System.out.println("Error: User has an invalid role.");
        }
    }
    public static boolean isUsernameUnique(String newUsername) {
        ArrayList<User> userList = fetchUsers();
        return userList.stream().noneMatch(user -> user.getUsername().equals(newUsername));
    }

    public static boolean isEmailUnique(String newEmail) {
        ArrayList<User> userList = fetchUsers();
        return userList.stream().noneMatch(user -> user.getEmail().equals(newEmail));
    }

}
