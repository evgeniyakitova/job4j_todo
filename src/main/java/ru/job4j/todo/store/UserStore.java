package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
public class UserStore implements Store {

    private static final String FIND_BY_EMAIL_AND_PWD = """
                                                from User user where user.email = :email
                                                and user.password = :password
                                        """;

    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> findByEmailAndPwd(String email, String password) {
        return tx(session -> session.createQuery(FIND_BY_EMAIL_AND_PWD, User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResultOptional(), sf);
    }

    public boolean add(User user) {
        try {
            tx(session -> session.save(user), sf);
            return true;
        } catch (ConstraintViolationException e) {
            return false;
        }
    }
}
