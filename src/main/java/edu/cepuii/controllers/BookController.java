package edu.cepuii.controllers;

import edu.cepuii.models.Book;
import edu.cepuii.models.Person;
import edu.cepuii.sevices.BookService;
import edu.cepuii.sevices.PeopleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;


    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "books_per_page", defaultValue = "3") int booksPerPage,
                        @RequestParam(name = "sort_by", defaultValue = "title") String sortBy) {

        model.addAttribute("books",
                bookService.getAll(PageRequest.of(page, booksPerPage, Sort.by(sortBy))));

        return "/books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/create";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/books/create";
        }

        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {

        Book book = bookService.getById(id);

        if (book == null) {
            return "redirect:/books";
        }

        Person owner = book.getOwner();

        if (owner == null) {
            model.addAttribute("people", peopleService.getAll());
        } else {
            model.addAttribute("owner", owner);
        }

        model.addAttribute("book", book);
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookService.getById(id);

        if (book == null) {
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/addPerson")
    public String addPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.updatePersonId(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/cleanPerson")
    public String cleanPerson(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.cleanPersonId(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "searchingTitle", required = false) String searchingTitle) {
        if (searchingTitle != null) {
            model.addAttribute("books", bookService.findByTitle(searchingTitle));
        }
        return "/books/search";
    }
}
