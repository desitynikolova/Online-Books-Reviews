package com.online.book.review.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Този клас представлява регистрация на книга
@Entity
public class BookRegistration {
    @Id
    String Book_title; // декларира поле за съхранение на заглавието на книгата

    String Author; // декларира поле за съхранение на автора на книгата

    String rate; // декларира поле за съхранение на рейтинга на книгата

    String Price; // декларира поле за съхранение на цената на книгата

    public String getPrice() {
        return Price;
    } // дефинира метод за връщане на стойността на полето price

    public void setPrice(String price) {
        Price = price;
    }  // този метод приема като аргумент новата стойност за price и я присвоява на полето

    public String getBook_title() {
        return Book_title;
    } // дефинира метод за връщане на стойността на полето Book_title

    public void setBook_title(String book_title) {
        Book_title = book_title;
    }  // този метод приема като аргумент новата стойност за Book_title и я присвоява на полето

    public String getAuthor() {
        return Author;
    } // дефинира метод за връщане на стойността на полето Author

    public void setAuthor(String author) {
        Author = author;
    }  // този метод приема като аргумент новата стойност за Author и я присвоява на полето

    public String getRate() {
        return rate;
    } // дефинира метод за връщане на стойността на полето Rate

    public void setRate(String rate) {
        this.rate = rate;
    }  // този метод приема като аргумент новата стойност за Rate и я присвоява на полето
}
