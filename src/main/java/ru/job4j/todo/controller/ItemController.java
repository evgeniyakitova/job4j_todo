package ru.job4j.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final CategoryService categoryService;

    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, new CategoryPropertyEditor());
    }

    private static class CategoryPropertyEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String id) {
            final Category category = new Category(Integer.parseInt(id));
            setValue(category);
        }

        @Override
        public String getAsText() {
            Category category = (Category) getValue();
            return String.valueOf(category.getId());
        }
    }

    @ModelAttribute
    public User sessionUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("/all")
    public String getAllItems(@SessionAttribute User user, Model model) {
        model.addAttribute("items", itemService.findAll(user.getId()));
        return "items/list";
    }

    @GetMapping("/done")
    public String getDoneItems(@SessionAttribute User user, Model model) {
        model.addAttribute("items", itemService.findDone(user.getId()));
        return "items/list";
    }

    @GetMapping("/new")
    public String getNewItems(@SessionAttribute User user, Model model) {
        model.addAttribute("items", itemService.findNew(user.getId()));
        return "items/list";
    }

    @GetMapping("/add")
    public String getAddForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", categoryService.findAll());
        return "items/add";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, @SessionAttribute User user) {
        item.setUser(user);
        itemService.add(item);
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable String id, Model model) {
        Optional<Item> item = itemService.findById(Integer.parseInt(id));
        if (item.isEmpty()) {
            return "redirect:/items";
        }
        model.addAttribute("item", item.get());
        model.addAttribute("categories", categoryService.findAll());
        return "items/edit";
    }

    @PostMapping("/edit")
    public String editItem(@ModelAttribute Item item, @SessionAttribute User user) {
        item.setUser(user);
        itemService.update(item);
        return "redirect:/items";
    }

    @PostMapping("/update_checkbox")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCheckbox(@RequestParam("id") int id, @RequestParam("done") boolean done) {
        itemService.updateCheckbox(done, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int id) {
        itemService.delete(id);
    }
}
