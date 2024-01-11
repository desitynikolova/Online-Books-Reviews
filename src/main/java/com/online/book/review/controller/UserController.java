package com.online.book.review.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.book.review.model.BookRegistration;
import com.online.book.review.repository.BookRepo;
import com.online.book.review.repository.UserRepo;

// Spring контролер (UserController), който управлява
// заявки (requests) и визуализации (views) свързани с потребителската част на онлайн системата за оценяване на книги

// @Controller: Анотация, която указва, че този клас е Spring контролер, отговарящ за обработката на HTTP заявки
@Controller
public class UserController {

    // @Autowired: Анотация, която внедрява зависимости в случая обекти от класовете UserRepo и BookRepo
    @Autowired
    UserRepo repo;

    @Autowired
    BookRepo brepo;

    static public String user_session1 = "User";

    // Методът ще се извика, когато се направи HTTP заявка към път "/User_Home"
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

    // Обработва заявка за показване на книги. Подава потребителската сесия и насочва към "/User_Book_Management"
    @RequestMapping("/User_Books")
    public ModelAndView User_Books() {
        ModelAndView mv = new ModelAndView("User_Book_Management");

        mv.addObject("User", user_session1);

        return mv;
    }

    // Обработва заявка за избор на операция (показване, оценяване) върху книгите.
    // Подава потребителската сесия и избраната операция.
    @RequestMapping("/user_select_operation")
    public ModelAndView user_select_operation(String book_operation) {
        ModelAndView mv = new ModelAndView("User_Book_Management");

        mv.addObject("User", user_session1);

        if (book_operation.equals("None")) {
            return mv;
        } else if (book_operation.equals("Display")) {
            mv.addObject("selectDisplay", "Display");
            return User_Book_Details(mv);
        } else if (book_operation.equals("Rate")) {
            mv.addObject("selectRate", "Rate");
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
            mv.setViewName("User_Book_Management");
        } else {
            mv.addObject("BookObject", breg1);
        }
        return mv;
    }

    // Обработва заявка за оценяване на книга. Подава потребителската сесия, заглавието на книгата и оценката.
    @RequestMapping("/user_Rate_Book")
    public ModelAndView user_Rate_Book(String Book_title, String rate) {
        ModelAndView mv = new ModelAndView("User_Book_Management");

        mv.addObject("User", user_session1);

        Optional<BookRegistration> breg1 = brepo.findById(Book_title);

        if (breg1.isPresent()) {
            mv.addObject("PrintSwal", "RBook_Update");
            BookRegistration breg = breg1.get();
            breg.setRate(rate);
            brepo.save(breg);
        } else {
            mv.addObject("PrintSwal", "RBook_Not_Found");
        }
        return mv;
    }
}