package pl.mojprzepisnik.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import pl.mojprzepisnik.dao.DaoFactory;
import pl.mojprzepisnik.dao.UserDao;
import pl.mojprzepisnik.model.User;

public class UserService {

    public void addUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        String md5Pass = encryptPassword(password);
        user.setPassword(md5Pass);
        user.setIsActive(true);
        DaoFactory factory = DaoFactory.getDaoFactory();
        UserDao userDao = factory.getUserDao();
        userDao.create(user);
    }
    
    private String encryptPassword(String password){
        MessageDigest digest = null;
        try{
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        digest.update(password.getBytes());
        String md5Password = new BigInteger(1, digest.digest()).toString(16);
        return md5Password;
        
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
