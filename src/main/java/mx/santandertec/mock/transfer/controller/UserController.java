package mx.santandertec.mock.transfer.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.santandertec.mock.transfer.entities.User;
import mx.santandertec.mock.transfer.service.UserService;

@Slf4j
// define controller
@Controller
@AllArgsConstructor
public class UserController {

	// inyecta UserService por constructor
	private UserService userService;

	@GetMapping("/") // localhost:8080/?page=2&size=3
	public String index(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		log.info("retrieving page of Users for page: {} of size: {}", currentPage - 1, pageSize);

		// Implementa
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		Page<User> usersPage = userService.findPaginated(pageable);

		model.addAttribute("usersPage", usersPage);

		int totalPages = usersPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

			model.addAttribute("pageNumbers", pageNumbers);
		}

		log.info("going to index view");

		return "index";
	}

	@ResponseBody
	@GetMapping("/all")
	public Page<User> all(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		log.info("retrieving page of Users for page: {} of size: {}", currentPage - 1, pageSize);

		// Implementa
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

		Page<User> usersPage = userService.findPaginated(pageable);

		return usersPage;
	}

	@GetMapping("/add-user-form")
	public String showSignUpForm(Model model) {

		log.info("going to add-user-form view");
		model.addAttribute("user", new User());

		return "add-user-form";
	}

	@PostMapping("/add-user")
	public String addUser(@Valid User user, BindingResult result, Model model) {

		// Implementa
		if (result.hasErrors()) {
			log.info("going to add-user-form view, User input is invalid");

			return "add-user-form";
		}

		log.info("saving User into DB");
		userService.saveOrUpdate(user);

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {

		log.info("retrieving User with id: {}", id);

		// Implementa
		User user = userService.searchById(id);

		model.addAttribute("user", user);

		log.info("going to update-user-form view");

		return "update-user-form";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {

		// Implementa
		if (result.hasErrors()) {
			log.info("going to update-user-form view, User input is invalid");
			user.setId(id);

			return "update-user-form";
		}

		log.info("updating User into DB");
		userService.saveOrUpdate(user);

		log.info("redirecting to '/' path");

		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {

		// Implementa
		log.info("retrieving User with id: {}", id);
		User user = userService.searchById(id);

		log.info("deleting User with id: {}", id);
		userService.delete(user);

		log.info("redirecting to '/' path");

		return "redirect:/";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String illegalArgumentExceptionHandler() {
		return "redirect:/";
	}

	@ModelAttribute("headers")
	public Map<String, String> headers(HttpServletRequest request) {
		Map<String, String> headers = new HashMap<>();

		Enumeration<String> hearderNames = request.getHeaderNames();
		while (hearderNames.hasMoreElements()) {
			String headerName = hearderNames.nextElement();
			headers.put(headerName, request.getHeader(headerName));
		}
		
		System.out.println("Model Headers: " + headers);
		return headers;
	}
}
