package theknife;

public class Review {
    private String username;
    private int rating;
    private String text;

    public Review(String username, int rating, String text) {
        this.username = username;
        this.rating = rating;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int r) {
        this.rating = r;
    }

    public String getText() {
        return text;
    }

    public void setText(String t) {
        this.text = t;
    }
}
