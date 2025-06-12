package theknife;

public class Restaurant {
    private int id;
    private String name;
    private String cuisine;
    private double rating;
    private int reviews;
    private String price;
    private String distance;
    private String imageUrl;
    private String description;

    public Restaurant(int id, String name, String cuisine, double rating, int reviews, String price,
            String distance, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine != null ? cuisine.toLowerCase() : "unknown";
        this.rating = rating;
        this.reviews = reviews;
        this.price = price;
        this.distance = distance;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public double getRating() {
        return rating;
    }

    public int getReviews() {
        return reviews;
    }

    public String getPrice() {
        return price;
    }

    public String getDistance() {
        return distance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
