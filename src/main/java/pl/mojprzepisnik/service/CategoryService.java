package pl.mojprzepisnik.service;

import java.util.Comparator;
import java.util.List;
import pl.mojprzepisnik.dao.CategoryDao;
import pl.mojprzepisnik.dao.DaoFactory;
import pl.mojprzepisnik.model.Category;

public class CategoryService {
    
    public List<Category> getAllCategories(){
        DaoFactory factory = DaoFactory.getDaoFactory();
        CategoryDao categoryDao = factory.getCategoryDao();
        List<Category> categoryList = categoryDao.getAll();
        return categoryList;
    }
    public List<Category> getAllCategories(Comparator<Category> comparator){
        DaoFactory factory = DaoFactory.getDaoFactory();
        CategoryDao categoryDao = factory.getCategoryDao();
        List<Category> categoryList = categoryDao.getAll();
        if (comparator!=null && categoryList != null){
            categoryList.sort(comparator);
        }
        return categoryList;
    }
}
