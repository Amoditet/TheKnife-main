public class Booking {
    private String restaurantName;
    private String date;
    private String time;

    public Booking(String restaurantName, String date, String time) {
        this.restaurantName = restaurantName;
        this.date = date;
        this.time = time;
    }

    public String getRestaurantName() { return restaurantName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}
