package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemStore;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public List<Item> findAll(int userId) {
        return itemStore.findAll(userId);
    }

    public List<Item> findDone(int userId) {
        return itemStore.findByDone(userId, true);
    }

    public List<Item> findNew(int userId) {
        return itemStore.findByDone(userId, false);
    }

    public Optional<Item> findById(int id) {
        return itemStore.findById(id);
    }

    public void add(Item item) {
        itemStore.add(item);
    }

    public void update(Item item) {
        itemStore.update(item);
    }

    public void updateCheckbox(boolean isDone, int id) {
        itemStore.updateCheckbox(isDone, id);
    }

    public void delete(int id) {
        itemStore.delete(id);
    }
}
