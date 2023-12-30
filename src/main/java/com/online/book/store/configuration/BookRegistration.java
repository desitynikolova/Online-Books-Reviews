package com.online.book.store.configuration;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


// Този клас представлява регистрация на книга
@Entity
public class BookRegistration {

    @Id
    String Book_title; // декларира поле за съхранение на заглавието на книгата

    String Author; // декларира поле за съхранение на автора на книгата

    String rate; // декларира поле за съхранение на рейтинга на книгата

    public String getBook_title() {
        return Book_title;
    } // дефинира метод за връщане на стойността на полето Book_title

    public void setBook_title(String book_title) {
        Book_title = book_title;
    } // този метод приема като аргумент новата стойност за Book_title и я присвоява на полето

    public String getAuthor() {
        return Author;
    } // този метод връща текущата стойност на Author; използва се за получаване на стойности на Author

    public void setAuthor(String author) {
        Author = author;
    } // този метод приема като аргумент новата стойност за Author и я присвоява на полето

    public String getRate() {
        return rate;
    } // този метод връща текущата стойност на Rate; използва се за получаване на стойности на Rate

    public void setRate(String rate) {
        this.rate = rate;
    } // този метод приема като аргумент новата стойност за Rate и я присвоява на полето
}
