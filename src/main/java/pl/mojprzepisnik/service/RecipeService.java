package pl.mojprzepisnik.service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import pl.mojprzepisnik.dao.DaoFactory;
import pl.mojprzepisnik.dao.RecipeDao;
import pl.mojprzepisnik.dao.RecipeDaoImpl;
import pl.mojprzepisnik.model.Category;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.model.User;

public class RecipeService {
    
    public void addRecipe(String name, String description, String url, User user, Category[] category){
        Recipe recipe = createRecipeObject(name, description, url, user, category);
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        recipeDao.create(recipe);
    }
    
    private Recipe createRecipeObject (String name, String description, String url, User user, Category[] category){
        
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setUlr(url);
        User userCopy = new User(user);
        recipe.setUser(userCopy);
        recipe.setCategory(category);
        recipe.setTimestamp(new Timestamp(new Date().getTime()));
        
        return recipe;
    }
    
    public List<Recipe> getAllRecipes(){
        return getAllRecipes(null);
    }
    
    public List<Recipe> getAllRecipesByUsername(String username){
        return getAllRecipesByUsername(null, username);
    }
    
    public List<Recipe> getAllRecipes(Comparator<Recipe> comparator){
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        List<Recipe> recipes = recipeDao.getAll();
        if (comparator!=null && recipes != null){
            recipes.sort(comparator);
        }
        return recipes;
    }
    public List<Recipe> getAllRecipesByUsername(Comparator<Recipe> comparator, String username){
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        List<Recipe> recipes = recipeDao.getAllByUsername(username);
        if (comparator!=null && recipes != null){
            recipes.sort(comparator);
        }
        return recipes;
    }
    
    public List<Recipe> getAllRecipesIfShared(Comparator<Recipe> comparator){
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        List<Recipe> recipes = recipeDao.getAllIfShared();
        if (comparator!=null && recipes != null){
            recipes.sort(comparator);
        }
        return recipes;
    }
    
    public boolean deleteRecipe (long recipe_id){
        
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        Boolean result = recipeDao.delete(recipe_id);
                
        return result;
    }
    
    public Recipe getRecipeById (long recipe_id){
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        Recipe recipe = recipeDao.read(recipe_id);
        
        return recipe;
    }
    
    public boolean updateRecipe(Recipe recipe){
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        Boolean result = recipeDao.update(recipe);
        return result;
    }
    
    public boolean shareRecipe(long recipe_id, boolean is_shared){
        DaoFactory factory = DaoFactory.getDaoFactory();
        RecipeDao recipeDao = factory.getRecipeDao();
        Boolean result = recipeDao.shareRecipe(recipe_id, is_shared);
        return result;
    }
}
