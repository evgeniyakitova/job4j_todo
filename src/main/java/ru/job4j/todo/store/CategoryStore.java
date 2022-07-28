package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

@Repository
public class CategoryStore implements Store {
    private final SessionFactory sf;

    public CategoryStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Category> findAll() {
        return tx(session -> session.createQuery("from Category", Category.class).list(), sf);
    }

}
