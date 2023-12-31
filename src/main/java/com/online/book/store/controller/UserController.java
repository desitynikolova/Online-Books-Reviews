package com.online.book.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.book.store.configuration.BookRegistration;
import com.online.book.store.repository.BookRepo;
import com.online.book.store.repository.UserRepo;

// @Controller: Означава, че този клас е Spring контролер, отговорен за обработването на HTTP заявките.
@Controller
public class UserController {

    // @Autowired: Вкарване на зависимости (в този случай, UserRepo и BookRepo)
    @Autowired
    UserRepo repo;

    @Autowired
    BookRepo brepo;

    static public String user_session1 = "User";

    // Обработва заявка за потребителския начален екран.
    // Подава потребителската сесия и съобщение за показване на User_View.
    @RequestMapping("/User_Home")
    public ModelAndView User_Home(String User_Session, String print) {
        ModelAndView mv = new ModelAndView("User_View");

        if (User_Session != null) {
            user_session1 = User_Session;
        }

        mv.addObject("User", user_session1);

        mv.addObject("PrintSwal", print);

        return mv;
    }

    // Обработва заявка за показване на книги. Подава потребителската сесия и насочва към Search_Book.
    @RequestMapping("/User_Books")
    public ModelAndView User_Books(String User_Session) {
        ModelAndView mv = new ModelAndView("Search_Book");

        mv.addObject("User", user_session1);

        return mv;
    }

    // Обработва заявка за избор на операция (търсене, показване, оценяване) върху книгите.
    // Подава потребителската сесия и избраната операция.
    @RequestMapping("/user_select_operation")
    public ModelAndView user_select_operation(String book_operation) {
        ModelAndView mv = new ModelAndView("Search_Book");

        mv.addObject("User", user_session1);

        if (book_operation.equals("None")) {
            return mv;
        } else if (book_operation.equals("Search")) {
            mv.addObject("selectSearch", "Search");
        } else if (book_operation.equals("Display")) {
            mv.addObject("selectDisplay", "Display");
            return User_Book_Details(mv);
        } else if (book_operation.equals("Rate")) {
            mv.addObject("selectRate", "Rate");
        }

        return mv;
    }

    // Обработва заявка за търсене на книга. Подава потребителската сесия,
    // търсеното заглавие на книгата и извежда съобщение за намиране или не на книгата.
    @RequestMapping("/user_search_Book")
    public ModelAndView user_search_Book(String Book_title) {
        ModelAndView mv = new ModelAndView("Search_Book");

        mv.addObject("User", user_session1);

        Optional<BookRegistration> breg1 = brepo.findById(Book_title);

        if (breg1.isPresent()) {
            mv.addObject("PrintSwal", "Book_Found");
        } else {
            mv.addObject("PrintSwal", "Book_Not_Found");
        }
        return mv;

    }

    // Обработва заявка за показване на детайлите на всички книги.
    // Подава потребителската сесия и връща модел с информация за книгите.
    @RequestMapping("/User_Book_Details")
    public ModelAndView User_Book_Details(ModelAndView mv) {

        List<BookRegistration> breg1 = brepo.findAll();

        if (breg1.isEmpty()) {
            mv.addObject("PrintSwal", "Book_Details_Empty");

            mv.setViewName("Search_Book");
        } else {
            BookRegistration book = null;
            mv.addObject("BookArray", book);
            mv.addObject("BookObject", breg1);

        }
        return mv;
    }

    // Обработва заявка за оценяване на книга. Подава потребителската сесия, заглавието на книгата и оценката.
    @RequestMapping("/user_Rate_Book")
    public ModelAndView user_Rate_Book(String Book_title, String rate) {
        ModelAndView mv = new ModelAndView("Search_Book");

        mv.addObject("User", user_session1);

        Optional<BookRegistration> breg1 = brepo.findById(Book_title);

        if (breg1.isPresent()) {
            mv.addObject("PrintSwal", "RBook_Found");
            BookRegistration breg = breg1.get();
            breg.setRate(rate);
            brepo.save(breg);
        } else {
            mv.addObject("PrintSwal", "RBook_Not_Found");
        }
        return mv;
    }
}