package pl.mojprzepisnik.dao;

import java.util.List;
import pl.mojprzepisnik.model.Recipe;

public interface RecipeDao extends GenericDao<Recipe, Long> {
    List<Recipe> getAllByUsername(String username);
    List<Recipe> getFavouriteRecipesByUsername(String username);
    List<Recipe> getAllIfShared();
    Boolean shareRecipe(long recipe_id, boolean is_shared);
    Boolean addToFavourite (long recipe_id, String username);
    Boolean checkIfAllreadyFavourite(long recipe_id, String username);
    Boolean removeRecipeFromFavourites(long recipe_id, String username);
}
