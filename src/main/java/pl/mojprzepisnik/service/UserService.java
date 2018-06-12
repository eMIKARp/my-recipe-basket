package pl.mojprzepisnik.service;

import pl.mojprzepisnik.dao.DaoFactory;
import pl.mojprzepisnik.dao.UserDao;
import pl.mojprzepisnik.model.User;

public class UserService {

    public void addUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setIsActive(true);
        DaoFactory factory = DaoFactory.getDaoFactory();
        UserDao userDao = factory.getUserDao();
        userDao.create(user);
    }
    
    public User getUserById(long user_id){
        DaoFactory factory = DaoFactory.getDaoFactory();
        UserDao userDao = factory.getUserDao();
        User user = userDao.read(user_id);
        return user;
    }
    
    public User getUserByUsername(String username){
        DaoFactory factory = DaoFactory.getDaoFactory();
        UserDao userDao = factory.getUserDao();
        User user = userDao.getUserByUsername(username);
        return user;
    }
}
