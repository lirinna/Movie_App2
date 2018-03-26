package app.example.db.movie.movieapp.model;

/**
 * Created by Katy on 26.03.2018.
 */

public class Review {

    private String author;
    private String content;
    private String url;


    public Review() {

    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
