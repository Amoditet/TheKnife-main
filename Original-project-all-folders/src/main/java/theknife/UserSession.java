package theknife;

public class UserSession {
    private static UserSession instance;

    private String username;
    private String location;
    private String role;
    private java.util.List<Restaurant> favorites = new java.util.ArrayList<>();

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String username, String location, String role) {
        this.username = username;
        this.location = location;
        this.role = role;
    }

    public void clearUser() {
        this.username = null;
        this.location = null;
        this.role = null;
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    public boolean isNotLoggedIn() {
        return username == null;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public String getRole() {
        return role;
    }

    public void addFavorite(Restaurant restaurant) {
        if (restaurant != null && !favorites.contains(restaurant)) {
            favorites.add(restaurant);
        }
    }

    public java.util.List<Restaurant> getFavorites() {
        return favorites;
    }
}