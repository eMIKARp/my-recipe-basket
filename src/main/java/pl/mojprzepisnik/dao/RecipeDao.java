package pl.mojprzepisnik.dao;

import java.util.List;
import pl.mojprzepisnik.model.Recipe;

public interface RecipeDao extends GenericDao<Recipe, Long> {
    List<Recipe> getAllByUsername(String username);
    List<Recipe> getAllIfShared();
    Boolean shareRecipe(long recipe_id, boolean is_shared);
}
