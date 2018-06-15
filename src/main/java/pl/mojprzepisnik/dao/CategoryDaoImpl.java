package pl.mojprzepisnik.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pl.mojprzepisnik.model.Category;
import pl.mojprzepisnik.model.CategoryType;
import pl.mojprzepisnik.util.ConnectionProvider;

public class CategoryDaoImpl implements CategoryDao{
    
    private static final String READ_ALL_CATEGORIES = "SELECT * FROM category";  

    NamedParameterJdbcTemplate template;

    public CategoryDaoImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
    
    @Override
    public Category create(Category newObject) {
        return null;
    }

    @Override
    public Category read(String primaryKey) {
        return null;
    }

    @Override
    public boolean update(Category updateObject) {
        return false;
    }

    @Override
    public boolean delete(String primaryKey) {
        return false;
    }

    @Override
    public List<Category> getAll() {
        
        List<Category> categoryList = template.query(READ_ALL_CATEGORIES, new CategoryRowMapper());
        
        
        return categoryList;
    }
    
    private class CategoryRowMapper implements RowMapper<Category>{

        @Override
        public Category mapRow(ResultSet resultSet, int row) throws SQLException {
            Category category = new Category();
                category.setName(resultSet.getString("category_name"));
                category.setcType(CategoryType.valueOf(resultSet.getString("type")));
            return category;
        }
        
    }
    
    
}
