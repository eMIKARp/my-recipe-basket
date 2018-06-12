package pl.mojprzepisnik.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.mojprzepisnik.model.User;
import pl.mojprzepisnik.util.ConnectionProvider;

public class UserDaoImpl implements UserDao {

    private static final String CREATE_USER = "INSERT INTO user (username, password, email, is_active) VALUES (:username, :password, :email, :isActive);";
    private static final String READ_USER = "SELECT user_id, username, email, password, is_active FROM user WHERE user_id=:id;";
    private static final String READ_USER_BY_USERNAME = "SELECT user_id, username, email, password, is_active FROM user WHERE username=:username;";
    private NamedParameterJdbcTemplate template;

        public UserDaoImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
    
    @Override
    public User create(User newUser) {
    
        User resultUser = new User(newUser);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(newUser);
        
        int update = template.update(CREATE_USER, paramSource, holder);
        if (update > 0){
            resultUser.setId(holder.getKey().longValue());
            setPriviliges(resultUser);
        }
        
        return null;
    }
    
    public void setPriviliges(User newUser){
        final String userRoleQuery = "INSERT INTO user_role (username) VALUES (:username)";
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(newUser);
        template.update(userRoleQuery, paramSource);
    }

    @Override
    public User read(Long primaryKey) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("id", primaryKey);
        resultUser = template.queryForObject(READ_USER, paramSource, new UserRowMapper());
        return resultUser;
    }

    @Override
    public boolean update(User updateObject) {
        return false;
    }

    @Override
    public boolean delete(Long primaryKey) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
    
    @Override
    public User getUserByUsername(String username) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("username", username);
        resultUser = template.queryForObject(READ_USER_BY_USERNAME, paramSource, new UserRowMapper());
        return resultUser;
    }
    
    private class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
        
    }
    
}
