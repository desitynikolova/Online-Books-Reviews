package com.online.book.store.controller;

import java.util.List;
import java.util.Optional;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.online.book.store.configuration.BookRegistration;
import com.online.book.store.configuration.UserRegistration;
import com.online.book.store.repository.BookRepo;
import com.online.book.store.repository.UserRepo;

// Spring контролер (AdminController), който управлява
// заявки (requests) и визуализации (views) свързани с административната част на онлайн системата за оценяване на книги

// @Controller: Анотация, която указва, че този клас е Spring контролер, отговарящ за обработката на HTTP заявки
@Controller
public class AdminController {

	// @Autowired: Анотация, която внедрява обекти от класовете UserRepo и BookRepo
	@Autowired
	UserRepo repo;

	@Autowired
	BookRepo brepo;

	// static public String user_session;

	// Този метод връща обект от тип ModelAndView, който посочва, че ще се използва изглед (view) с име "Admin_View"
	@RequestMapping("/Admin_Home")
	public ModelAndView Admin_Home() {
		ModelAndView mv = new ModelAndView("Admin_View");
		return mv;
	}

	// Този метод връща обект от тип ModelAndView с изглед "Book_Management"
	@RequestMapping("/Book_Management")
	public ModelAndView Book_Management() {
		ModelAndView mv = new ModelAndView("Book_Management");
		return mv;
	}

	// Този метод приема параметър book_operation от заявката.
	// Логиката в метода определя какво действие да се предприеме в зависимост от стойността на book_operation.
	@RequestMapping("/selectoperation")
	public ModelAndView selectoperation(String book_operation) {
		ModelAndView mv = new ModelAndView("Book_Management");

		if (book_operation.equals("None")) {
			return mv;
		} else if (book_operation.equals("Add")) {
			mv.addObject("selectAdd", "Add");
		} else if (book_operation.equals("Delete")) {
			mv.addObject("selectDelete", "Delete");
		} else if (book_operation.equals("Edit")) {
			List<BookRegistration> bookList = brepo.findAll();

			mv.addObject("selectEdit", "Edit");
			mv.addObject("bookList", bookList);
		}

		return mv;
	}

	// Този метод приема обект от тип BookRegistration като
	// параметър и съдържа логика за обновяване на информацията за книгата в базата данни
	@RequestMapping("/updateBook")
	public ModelAndView updateBook(@ModelAttribute BookRegistration updatedBook) {
		// Използва се BookRepo, за да се запазят промените към книгата
		brepo.save(updatedBook);

		List<BookRegistration> bookList = brepo.findAll();

		ModelAndView mv = new ModelAndView("Book_Management");
		mv.addObject("selectEdit", "Edit");
		mv.addObject("bookList", bookList);
		return mv;
	}

	// Този метод приема параметър от тип BookRegistration и стойност на параметъра Book_title
	@RequestMapping("/book_Add")
	public ModelAndView book_Add(BookRegistration breg, String Book_title) {
		ModelAndView mv = new ModelAndView("Book_Management");

		Optional<BookRegistration> breg1 = brepo.findById(Book_title);

		// проверява се дали книга със същото заглавие вече съществува в базата данни
		if (breg1.isPresent()) {
			mv.addObject("PrintSwal", "Book_Exist");
		} else {
			mv.addObject("PrintSwal", "Add_Success");
			brepo.save(breg);
		}

		return mv;
	}

	// Този метод приема параметър от тип String - Book_title
	@RequestMapping("/book_Delete")
	public ModelAndView book_Delete(String Book_title) {
		ModelAndView mv = new ModelAndView("Book_Management");

		Optional<BookRegistration> breg1 = brepo.findById(Book_title);

		// Ако книгата съществува (breg1.isPresent()), тя се изтрива от базата данни чрез brepo.deleteById(Book_title)
		// В противен случай се добавя съобщение за неуспешно изтриване ("Delete_Failed")
		if (breg1.isPresent()) {
			brepo.deleteById(Book_title);
			mv.addObject("PrintSwal", "Delete_Success");
		} else {
			mv.addObject("PrintSwal", "Delete_Failed");
		}

		return mv;
	}

	// Методът не приема параметри. Извлича списък с всички книги от базата данни, използвайки BookRepo.
	// Логиката проверява дали списъкът е празен (breg1.isEmpty()).
	// Ако е празен, се добавя съобщение, че няма налични книги ("Book_Details_Empty") и се насочва към изглед "Admin_View".
	// В противен случай се подготвят обекти за представяне на данните в изгледа.
	@RequestMapping("/Book_Details")
	public ModelAndView Book_Details() {
		ModelAndView mv = new ModelAndView("Book_Details");

		List<BookRegistration> breg1 = brepo.findAll();

		if (breg1.isEmpty()) {
			mv.addObject("PrintSwal", "Book_Details_Empty");
			mv.setViewName("Admin_View");
		} else {
			BookRegistration book = null;
			mv.addObject("BookArray", book);
			mv.addObject("BookObject", breg1);
		}

		return mv;
	}

	// Този метод не приема параметри. Извлича списък с всички потребители от базата данни, използвайки UserRepo.
	// Логиката проверява дали списъкът е празен (ureg1.isEmpty()).
	// Ако е празен, се добавя съобщение, че няма налични потребители ("User_Details_Empty") и се насочва към изглед "Admin_View".
	// В противен случай се подготвят обекти за представяне на данните за регистрираните потребители.
	@RequestMapping("/User_Details")
	public ModelAndView User_Details() {
		ModelAndView mv = new ModelAndView("User_Details");

		List<UserRegistration> ureg1 = repo.findAll();

		if (ureg1.isEmpty()) {
			mv.addObject("PrintSwal", "User_Details_Empty");
			mv.setViewName("Admin_View");
		} else {
			UserRegistration user = null;
			mv.addObject("UserArray", user);
			mv.addObject("UserObject", ureg1);
		}

		return mv;
	}
}
