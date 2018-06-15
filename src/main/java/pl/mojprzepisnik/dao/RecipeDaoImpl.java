package pl.mojprzepisnik.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.mojprzepisnik.model.Category;
import pl.mojprzepisnik.model.CategoryType;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.model.User;
import pl.mojprzepisnik.util.ConnectionProvider;

public class RecipeDaoImpl implements RecipeDao{

    private static final String CREATE_RECIPE = "INSERT INTO recipe (name, description,url,user_id, date,"
            +"up_vote, down_vote) VALUES (:name,:description, :url,:user_id, :date, :up_vote, :down_vote)";
    private static final String READ_ALL_RECIPES = "SELECT user.user_id, username, email, is_active,"
            + " password, recipe.recipe_id, name, description, url, date, up_vote, down_vote, is_shared, category_name"
            + " FROM recipe"
            + " LEFT JOIN user ON recipe.user_id = user.user_id"
            + " LEFT JOIN recipe_category ON recipe.recipe_id = recipe_category.recipe_id;";
    
    private static final String READ_ALL_RECIPES_IF_SHARED = "SELECT user.user_id, username, email, is_active,"
            + " password, recipe.recipe_id, name, description, url, date, up_vote, down_vote, is_shared, category_name"
            + " FROM recipe"
            + " LEFT JOIN user ON recipe.user_id = user.user_id"
            + " LEFT JOIN recipe_category ON recipe.recipe_id = recipe_category.recipe_id"
            + " WHERE is_shared = 1;";
    
    private static final String READ_ALL_RECIPES_BY_USERNAME = "SELECT user.user_id, username, email, is_active,"
            + " password, recipe.recipe_id, name, description, url, date, up_vote, down_vote, is_shared, category_name"
            + " FROM recipe"
            + " LEFT JOIN user ON recipe.user_id = user.user_id"
            + " LEFT JOIN recipe_category ON recipe.recipe_id = recipe_category.recipe_id"
            + " WHERE username = :username;";
    
    private static final String READ_FAVOURITE_RECIPES_BY_USERNAME = "SELECT user.user_id, username, email, is_active,"
            + " password, recipe.recipe_id, name, description, url, date, up_vote, down_vote, is_shared, category_name"
            + " FROM recipe"
            + " LEFT JOIN user ON recipe.user_id = user.user_id"
            + " LEFT JOIN recipe_category ON recipe.recipe_id = recipe_category.recipe_id"
            + " WHERE recipe.recipe_id IN (SELECT recipe_id FROM user_foreign_recipe WHERE user_foreign_recipe.username=:username);";
    
    
    private static final String READ_RECIPE = "SELECT user.user_id, username, email, is_active,"
            + " password, recipe.recipe_id, name, description, url, date, up_vote, down_vote, is_shared, category_name"
            + " FROM recipe"
            + " LEFT JOIN user ON recipe.user_id = user.user_id"
            + " LEFT JOIN recipe_category ON recipe.recipe_id = recipe_category.recipe_id"
            + " WHERE recipe.recipe_id = :recipe_id;";

    private static final String UPDATE_RECIPE = "UPDATE recipe SET name=:name, description=:description, url=:url, user_id=:user_id, date=:date, up_vote=:up_vote, down_vote=:down_vote, is_shared=:is_shared"
		+ " WHERE recipe_id=:recipe_id;";   
    
    private static final String SHARE_RECIPE = "UPDATE recipe SET is_shared=:is_shared WHERE recipe_id=:recipe_id;";        
    
    private static final String DELETE_RECIPE = "DELETE FROM recipe WHERE recipe_id=:recipe_id";
    
    private static final String DELETE_RECIPE_FROM_FAVOURITES = "DELETE FROM user_foreign_recipe WHERE recipe_id=:recipe_id AND username=:username";
    
    private static final String ADD_TO_FAVOURITE = "INSERT INTO user_foreign_recipe (username, recipe_id) VALUES (:username,:recipe_id);";
    
    private static final String CHECK_IF_FAVOURITE = "UPDATE user_foreign_recipe SET username=:username, recipe_id=:recipe_id WHERE username=:username AND recipe_id=:recipe_id;";
      
    NamedParameterJdbcTemplate template;
    
    public RecipeDaoImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
    
    
    @Override
    public Recipe create(Recipe newRecipe) {
        
        Recipe resultRecipe = new Recipe(newRecipe);
        KeyHolder holder = new GeneratedKeyHolder();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", newRecipe.getName());
        paramMap.put("description", newRecipe.getDescription());
        paramMap.put("url", newRecipe.getUlr());
        paramMap.put("user_id", newRecipe.getUser().getId());
        paramMap.put("date", newRecipe.getTimestamp());
        paramMap.put("up_vote", newRecipe.getUpVote());
        paramMap.put("down_vote", newRecipe.getDownVote()); 
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(CREATE_RECIPE, paramSource, holder);
        if (update > 0){
            resultRecipe.setId(holder.getKey().longValue());
            setCategory(resultRecipe);
        }
        return resultRecipe;
    }
    
    public void setCategory(Recipe newRecipe){
        
        final String recipeCategoryQuery = "INSERT INTO recipe_category (recipe_id, category_name) VALUES (:recipe_id, :category_name);";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        for (int i =0; i < newRecipe.getCategory().size();i++){
            paramMap.put("recipe_id", newRecipe.getId());
            paramMap.put("category_name", newRecipe.getCategory().get(i).getName());
            SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
            template.update(recipeCategoryQuery, paramSource);
            paramMap.clear();
        }    
        
    }

    @Override
    public Boolean addToFavourite(long recipe_id, String username){
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("recipe_id", recipe_id);
        paramMap.put("username", username);
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(ADD_TO_FAVOURITE, paramSource);
        if (update > 0){
            result = true;
        }
        return result;
    }
    
    @Override
    public Boolean checkIfAllreadyFavourite(long recipe_id, String username){
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("recipe_id", recipe_id);
        paramMap.put("username", username);
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(CHECK_IF_FAVOURITE, paramSource);
        if (update > 0){
            result = true;
        }
        return result;
    }
    
    public Boolean removeRecipeFromFavourites(long recipe_id, String username){
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("recipe_id", recipe_id);
        paramMap.put("username", username);
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(DELETE_RECIPE_FROM_FAVOURITES, paramSource);
        if (update > 0){
            result = true;
        }
        return result;
    }
    
    @Override
    public Recipe read(Long primaryKey) {
        SqlParameterSource paramSource = new MapSqlParameterSource("recipe_id", primaryKey);
        List<Recipe> recipe = template.query(READ_RECIPE, paramSource, new RecipeRowMapper());
        Recipe resultRecipe = deduplicateRecipes(recipe).get(0);
        
        return resultRecipe;
    }

    @Override
    public boolean update(Recipe recipe) {
        
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("recipe_id", recipe.getId());
        paramMap.put("name", recipe.getName());
        paramMap.put("description", recipe.getDescription());
        paramMap.put("url", recipe.getUlr());
        paramMap.put("user_id", recipe.getUser().getId());
        paramMap.put("date", recipe.getTimestamp());
        paramMap.put("up_vote", recipe.getUpVote());
        paramMap.put("down_vote", recipe.getDownVote());
        paramMap.put("is_shared", recipe.isIsShared());
        paramMap.put("category", recipe.getCategory());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_RECIPE, paramSource);
        if(update > 0) {
                result = true;
        }
        return result;
    }
    
    @Override
    public Boolean shareRecipe(long recipe_id, boolean is_shared){
        boolean result = false;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("recipe_id", recipe_id);
        paramMap.put("is_shared", is_shared);
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(SHARE_RECIPE, paramSource);
        if(update > 0) {
                
                result = true;
        }
        
        return result;
    }
    
    @Override
    public boolean delete(Long primaryKey) {
        
        boolean result = false;
        
        SqlParameterSource paramSource = new MapSqlParameterSource("recipe_id", primaryKey);
        int update = template.update(DELETE_RECIPE, paramSource);
        
        return false;
    }
    
    
    @Override
    public List<Recipe> getAll() {
        
        List<Recipe> recipes = template.query(READ_ALL_RECIPES, new RecipeRowMapper());
        recipes = deduplicateRecipes(recipes);
                
        return recipes;
    }
    
    @Override
    public List<Recipe> getAllIfShared() {
        
        List<Recipe> recipes = template.query(READ_ALL_RECIPES_IF_SHARED, new RecipeRowMapper());
        recipes = deduplicateRecipes(recipes);
                
        return recipes;
    }

    
    @Override
    public List<Recipe> getAllByUsername(String username){
        List<Recipe> recipes = template.query(READ_ALL_RECIPES_BY_USERNAME, new MapSqlParameterSource("username",username), new RecipeRowMapper());
        recipes = deduplicateRecipes(recipes);
        
        return recipes;
    }

    @Override
    public List<Recipe> getFavouriteRecipesByUsername(String username){
        List<Recipe> recipes = template.query(READ_FAVOURITE_RECIPES_BY_USERNAME, new MapSqlParameterSource("username",username), new RecipeRowMapper());
        recipes = deduplicateRecipes(recipes);
        
        return recipes;
    }
    
    private List<Recipe> deduplicateRecipes(List<Recipe> recipes){
        Map<Long,Recipe> deduplicatedRecipes = new HashMap<Long,Recipe>();
        for (int i=0; i<recipes.size();i++){
            if (deduplicatedRecipes.containsKey(recipes.get(i).getId())){
                deduplicatedRecipes.get(recipes.get(i).getId()).appendCategory(recipes.get(i).getCategory());
            } else {
                deduplicatedRecipes.put(recipes.get(i).getId(), recipes.get(i));
            }
        }
        
        Recipe[] recipeArray = new Recipe[deduplicatedRecipes.values().size()];
        deduplicatedRecipes.values().toArray(recipeArray);
        List<Recipe> recipeList = new ArrayList<Recipe>();
        
        for (Recipe recipe: recipeArray){
            recipeList.add(recipe);
        }
        
        return recipeList;
    }
    
    private class RecipeRowMapper implements RowMapper<Recipe>{

    @Override
    public Recipe mapRow(ResultSet resultSet, int row) throws SQLException {
        Recipe recipe = new Recipe();
            recipe.setId(resultSet.getLong("recipe_id"));
            recipe.setName(resultSet.getString("name"));
            recipe.setDescription(resultSet.getString("description"));
            recipe.setUlr(resultSet.getString("url"));
            recipe.setTimestamp(resultSet.getTimestamp("date"));
            recipe.setUpVote(resultSet.getInt("up_vote"));
            recipe.setDownVote(resultSet.getInt("down_vote"));
            recipe.setIsShared(resultSet.getBoolean("is_shared"));
        User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setIsActive(resultSet.getBoolean("is_active"));

            recipe.setUser(user);

        List<Category> categoryList = new ArrayList<Category>();
            categoryList.add(new Category(resultSet.getString("category_name"),CategoryType.PRE_DEFINED));
            recipe.setCategory(categoryList);

        return recipe;
    } 
        
    }
}
