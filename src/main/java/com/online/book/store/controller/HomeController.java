package com.online.book.store.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.book.store.configuration.UserRegistration;
import com.online.book.store.repository.UserRepo;

// Spring контролер (HomeController), отговарящ за обработката на
// заявки (requests) свързани с началната страница, логин, регистрация на потребител и проверка на данните за влизане

// @Controller: Анотация, която указва, че този клас е Spring контролер, отговарящ за обработката на HTTP заявки.
@Controller
public class HomeController {

    // @Autowired: Анотация, която внедрява зависимости, в този случай обект от класа UserRepo.
    // Този репозиторий се използва за взаимодействие с базата данни, където се съхраняват данни за потребителите.
    @Autowired
    UserRepo repo;

    String print = null; // променлива, използваща се за съхранение на съобщения (например за успешно влизане, неуспешно влизане и др.)

    static public String user_session; // използва се за съхранение на информация за потребителска сесия

    // Този метод връща обект от тип ModelAndView, който посочва, че ще се използва изглед (view) с име "Home"
    @RequestMapping("/")
    public ModelAndView Home() {
        ModelAndView mv = new ModelAndView("Home");
        return mv;
    }

    // Този метод приема параметри print, User и Pass.
    // Логиката в метода проверява дали User и Pass са null.
    // Ако са null, се добавят стойности по подразбиране ("admin" и "admin"). Накрая се насочва към изглед "Login_Form".
    @RequestMapping("/Login")
    public ModelAndView Login(String print, String User, String Pass) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("PrintSwal", print);

        if (User != null && Pass != null) {
            mv.addObject("User_Name", User);
            mv.addObject("Pass", Pass);
        } else {
            mv.addObject("User_Name", "admin");
            mv.addObject("Pass", "admin");
        }

        mv.setViewName("Login_Form");

        return mv;
    }

    // Този метод не приема параметри; просто насочва към изглед "Registration_Form"
    @RequestMapping("/User_RegistrationForm")
    public ModelAndView User() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Registration_Form");
        return mv;
    }

    // Този метод приема параметри email, password и u1 (обект от класа UserController)
    @RequestMapping("/VerifyLogin")
    public ModelAndView VerifyLogin(String email, String password, UserController u1) {
        ModelAndView mv = new ModelAndView("Login_Form");

        UserRegistration user = repo.findByEmailAndPassword(email, password);

        // Използва се UserRepo, за да се провери дали има потребител със зададения email и password
        // Ако email и password съвпадат със стойностите "admin", се смята, че става влизане на администратор; насочва към изглед "Admin_View"
        // Ако съществува потребител със зададения email и password, се смята, че става влизане на потребител. Извиква се метод User_Home от UserController и се предават параметри за потребителско име и съобщение.
        // Ако не съществува такъв потребител, се добавя съобщение за неуспешно влизане.
        if (email.equals("admin") && password.equals("admin")) {
            print = "Admin";
            mv.addObject("PrintSwal", print);
            user_session = "Admin";
            mv.setViewName("Admin_View");
        } else if (user != null) {
            print = "UserLogin";
            //mv.addObject("PrintSwal",print);
            mv.addObject("User", user.getFullname());
            user_session = user.getFullname();
            //mv.setViewName("User_View");
            return u1.User_Home(user_session, print);
        } else {
            print = "Failed";
            mv.addObject("PrintSwal", print);
        }

        return mv;
    }

    // Този метод приема параметри ureg, email и password.
    @RequestMapping("/User_Login")
    public ModelAndView User_Login(UserRegistration ureg,String email, String password)
    {
        ModelAndView mv = new ModelAndView("Registration_Form");

        Optional<UserRegistration> ureg1 = repo.findById(email);

        // Проверява се дали съществува потребител със зададения email. Ако съществува, се смята, че потребителят вече е регистриран.
        // В противен случай, се добавя нов потребител в базата данни чрез repo.save(ureg).
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
            //mv.setViewName("Login_Form");
            return Login(print,email,password);
        }
        mv.addObject("PrintSwal",print);
        return mv;
    }
}
