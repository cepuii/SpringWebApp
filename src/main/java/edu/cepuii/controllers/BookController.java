package edu.cepuii.controllers;

import edu.cepuii.dao.BookDao;
import edu.cepuii.dao.PersonDao;
import edu.cepuii.models.Book;
import edu.cepuii.models.Person;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {


    private final BookDao bookDao;
    private final PersonDao personDao;

    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.getAll());
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

        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {

        Optional<Book> book = bookDao.getById(id);

        if (book.isEmpty()) {
            return "redirect:/books";
        }

        Optional<Person> owner = bookDao.getBookOwner(id);

        if (owner.isEmpty()) {
            model.addAttribute("people", personDao.getAll());
        } else {
            model.addAttribute("owner", owner.get());
        }

        model.addAttribute("book", book.get());
        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDao.getById(id);

        if (book.isEmpty()) {
            return "redirect:/books";
        }

        model.addAttribute("book", book.get());
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }

        bookDao.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/addPerson")
    public String addPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDao.updatePersonId(id, person.getPersonId());
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/cleanPerson")
    public String cleanPerson(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDao.cleanPersonId(id);
        return "redirect:/books/" + id;
    }
}
