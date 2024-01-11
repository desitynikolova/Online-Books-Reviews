package com.online.book.review.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.book.review.model.UserRegistration;
import com.online.book.review.repository.UserRepo;

// Spring контролер (HomeController), отговарящ за обработката на
// заявки (requests) свързани с началната страница, логин, регистрация на потребител и проверка на данните за влизане

// @Controller: Анотация, която указва, че този клас е Spring контролер, отговарящ за обработката на HTTP заявки
@Controller
public class HomeController {
    // @Autowired: Анотация, която внедрява зависимости в случая обект от класа UserRepo
    @Autowired
    UserRepo repo;

    String print = null; // променлива, използваща се за съхранение на съобщения (например за успешно влизане, неуспешно влизане и др.)

    static public String user_session; // използва се за съхранение на информация за потребителска сесия

    // Методът ще се извика, когато се направи HTTP заявка към път "/"
    // След това методът създава обект от класа "ModelAndView" с име(изглед) "Home" и го връща.
    // Този обект на "ModelAndView" съдържа информация за изгледа и модела.
    @RequestMapping("/")
    public ModelAndView Home() {
        ModelAndView mv = new ModelAndView("Home");
        return mv;
    }

    // Методът ще се извика, когато се направи HTTP заявка към път "/Login"
    // Този метод приема параметри print, User и Pass от тип String
    // насочва към изглед "Login_Form"
    @RequestMapping("/Login")
    public ModelAndView Login(String print, String user, String pass) {
        ModelAndView mv = new ModelAndView();

        mv.addObject("PrintSwal", print);

        mv.addObject("User_Name", user);
        mv.addObject("Pass", pass);

        mv.setViewName("Login_Form");

        return mv;
    }

    // Методът ще се извика, когато се направи HTTP заявка към път "/User_RegistrationForm"
    // Този метод не приема параметри; просто насочва към изглед "Registration_Form"
    @RequestMapping("/User_RegistrationForm")
    public ModelAndView User() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Registration_Form");
        return mv;
    }

    // Този метод приема параметри email, password от тип String, и u1 (обект от класа UserController)
    @RequestMapping("/VerifyLogin")
    public ModelAndView VerifyLogin(String email, String password, UserController u1) {
        ModelAndView mv = new ModelAndView("Login_Form");

        // Използва се UserRepo, за да се провери дали има потребител със зададения email и password
        UserRegistration user = repo.findByEmailAndPassword(email, password);

        // Ако email и password съвпадат със стойностите "admin", се смята, че става влизане на администратор; насочва към изглед "Admin_View"
        // Ако съществува потребител със зададения email и password, се смята, че става влизане на потребител. Извиква се метод User_Home от UserController и се предават параметри за потребителско име и съобщение.
        // Ако не съществува такъв потребител, се добавя съобщение за неуспешно влизане.
        if (email.equals("admin") && password.equals("admin")) {
            mv.addObject("PrintSwal", "Admin");
            user_session = "Admin";
            mv.setViewName("Admin_View");
        } else if (user != null) {
            print = "UserLogin";
            mv.addObject("User", user.getFullname());
            user_session = "User";
            user_session = user.getFullname();
            return u1.User_Home(user_session, print);
        } else {
            mv.addObject("PrintSwal", "Failed");
        }

        return mv;
    }

    // Този метод приема параметри ureg, email и password.
    @RequestMapping("/User_Registration")
    public ModelAndView User_Registration(UserRegistration ureg, String email, String password)
    {
        ModelAndView mv = new ModelAndView("Registration_Form");

        Optional<UserRegistration> ureg1 = repo.findById(email);

        // Проверява се дали съществува потребител със зададения email. Ако съществува, се смята, че потребителят вече е регистриран.
        // В противен случай, се запазва нов потребител в базата данни чрез repo.save(ureg).
        // Поставя се съобщение за успешна регистрация и се извиква методът Login() за визуализиране на login формата.
        if(ureg1.isPresent())
        {
            print="User_Exists";
        }
        else
        {
            repo.save(ureg);
            print="Reg_Success";
            mv.addObject("PrintSwal",print);

            return Login(print,email,password);
        }
        mv.addObject("PrintSwal",print);
        return mv;
    }
}
