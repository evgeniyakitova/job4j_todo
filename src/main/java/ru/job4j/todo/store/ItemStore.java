package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemStore implements Store {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Item> findAll(int userId) {
        return tx(session -> session.createQuery(
                "select distinct it from Item it join fetch it.categories where it.user.id = :userId",
                        Item.class
                )
                .setParameter("userId", userId)
                .list(), sf);
    }

    public List<Item> findByDone(int userId, boolean done) {
        return tx(session -> session.createQuery(
                "select distinct it from Item it join fetch it.categories where it.user.id = :userId and it.done=:done",
                        Item.class
                )
                .setParameter("userId", userId)
                .setParameter("done", done)
                .list(), sf);
    }

    public Optional<Item> findById(int id) {
        return tx(session -> session.createQuery(
                        "select distinct it from Item it join fetch it.categories where it.id = :id",
                        Item.class
                )
                .setParameter("id", id)
                .uniqueResultOptional(), sf);
    }

    public void add(Item item) {
        tx(session -> session.save(item), sf);
    }

    public void update(Item item) {
        tx(session -> {
            session.update(item);
            return true;
        }, sf);
    }

    public void updateCheckbox(boolean isDone, int id) {
        tx(session -> session.createQuery("update Item set done=:isDone where id=:id")
                .setParameter("isDone", isDone)
                .setParameter("id", id)
                .executeUpdate(), sf);
    }

    public void delete(int id) {
        Item item = new Item();
        item.setId(id);
        tx(session -> {
            session.delete(item);
            return true;
        }, sf);
    }
}
