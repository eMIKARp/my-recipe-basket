package pl.mojprzepisnik.dao;

import pl.mojprzepisnik.model.User;

public interface UserDao extends GenericDao<User, Long>{
    
    User getUserByUsername(String username);

}
