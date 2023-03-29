package edu.cepuii.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {

  private int bookId;
  @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
  private String title;
  @NotEmpty(message = "Author should not be empty")
  @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
  private String author;
  @Min(value = 1800, message = "Year of publication should be greater than 1800")
  private int yearOfPublic;

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

}
