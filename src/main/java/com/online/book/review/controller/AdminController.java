package com.online.book.review.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.book.review.model.BookRegistration;
import com.online.book.review.model.UserRegistration;
import com.online.book.review.repository.BookRepo;
import com.online.book.review.repository.UserRepo;

// Spring контролер (AdminController), който управлява
// заявки (requests) и визуализации (views) свързани с административната част на онлайн системата за оценяване на книги

// @Controller: Анотация, която указва, че този клас е Spring контролер, отговарящ за обработката на HTTP заявки
@Controller
public class AdminController {
	// @Autowired: Анотация, която внедрява зависимости в случая обекти от класовете UserRepo и BookRepo
	@Autowired
	UserRepo repo;

	@Autowired
	BookRepo brepo;

	// Методът ще се извика, когато се направи HTTP заявка към път "/Admin_Home"
	// След това методът създава обект от класа "ModelAndView" с име(изглед) "Admin_View" и го връща.
	// Този обект на "ModelAndView" съдържа информация за изгледа и модела.
	@RequestMapping("/Admin_Home")
	public ModelAndView Admin_Home() {
		ModelAndView mv = new ModelAndView("Admin_View");
		return mv;
	}

	// Методът ще се извика, когато се направи HTTP заявка към път "/Book_Management"
	// След това методът създава обект от класа "ModelAndView" с име(изглед) "Book_Management" и го връща.
	// Този обект на "ModelAndView" съдържа информация за изгледа и модела.
	@RequestMapping("/Book_Management")
	public ModelAndView Book_Management() {
		ModelAndView mv = new ModelAndView("Book_Management");
		return mv;
	}

	// Методът ще се извика, когато се направи HTTP заявка към път "/selectOperation"
	// Представя логика за обработка на операции, свързани с управлението на книги
	@RequestMapping("/selectOperation")
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

	// Този метод приема обект от тип BookRegistration с име updatedBook като параметър.
	// Този обект представя книга, чиито данни трябва да бъдат обновени
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

	// Този метод приема обект от тип BookRegistration с име breg като параметър
	// приема и параметър от тип String - Book_title Book_title
	@RequestMapping("/addBook")
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
	@RequestMapping("/deleteBook")
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
			mv.addObject("UserObject", ureg1);
		}

		return mv;
	}
}
