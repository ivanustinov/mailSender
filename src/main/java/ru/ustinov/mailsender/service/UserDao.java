package ru.ustinov.mailsender.service;


import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import ru.ustinov.mailsender.request.User;

import java.util.List;

public class UserDao extends GenericDAOImpl<User, Long> {

    public List<User> findUserBy(String name) {
        Search search = new Search();
        search.addFilterEqual("name", name);
        return super.search(search);
    }
}
