package theknife;

public class UserSession {
    private static UserSession instance;

    private String username;
    private String location;
    private String role;
    private java.util.List<Restaurant> favorites = new java.util.ArrayList<>();
    private java.util.List<Booking> bookings = new java.util.ArrayList<>();
    private java.util.Map<Integer, Review> reviews = new java.util.HashMap<>();
    private java.util.List<Restaurant> ownedRestaurants = new java.util.ArrayList<>();

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

    public void addBooking(Booking b) {
        if (b != null) {
            bookings.add(b);
        }
    }

    public void addOwnedRestaurant(Restaurant r) {
        if (r != null) {
            ownedRestaurants.add(r);
        }
    }

    public java.util.List<Restaurant> getOwnedRestaurants() {
        return ownedRestaurants;
    }

    public java.util.List<Booking> getBookings() {
        return bookings;
    }

    public Review getReview(int restaurantId) {
        return reviews.get(restaurantId);
    }

    public void addOrUpdateReview(int restaurantId, Review r) {
        if (r != null) {
            reviews.put(restaurantId, r);
        }
    }

    public void deleteReview(int restaurantId) {
        reviews.remove(restaurantId);
    }
}