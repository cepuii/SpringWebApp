package edu.cepuii.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String title;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    @Column(name = "author")
    private String author;
    @Min(value = 1800, message = "Year of publication should be greater than 1800")
    @Column(name = "year_of_public")
    private int yearOfPublic;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;
    @Column(name = "in_owe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inOwe;
    @Transient
    private boolean delay;

    public Book() {
    }

    public Book(int bookId, String title, String author, int yearOfPublic) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfPublic = yearOfPublic;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublic() {
        return yearOfPublic;
    }

    public void setYearOfPublic(int yearOfPublic) {
        this.yearOfPublic = yearOfPublic;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getInOwe() {
        return inOwe;
    }

    public void setInOwe(Date inOwe) {
        this.inOwe = inOwe;
    }

    public boolean isDelay() {
        return Duration.between(inOwe.toInstant(), new Date().toInstant()).toDays() <= 10;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }
}
