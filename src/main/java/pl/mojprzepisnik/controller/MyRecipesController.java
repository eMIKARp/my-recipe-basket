package pl.mojprzepisnik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.service.RecipeService;

@WebServlet(name = "MyRecipesController", urlPatterns = {"/my_recipes"})
public class MyRecipesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        saveRecipesInRequest(request);
        request.getRequestDispatcher("WEB-INF/my_recipes.jsp").forward(request, response);
    }
    
    private void saveRecipesInRequest(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        RecipeService recipeService = new RecipeService();
        List<Recipe> allFavouriteRecipes = recipeService.getFavouriteRecipesByUsername(username);
        List<Recipe> allRecipes = recipeService.getAllRecipesByUsername(username); 
        allRecipes.addAll(allFavouriteRecipes);
        allRecipes.sort(new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                Timestamp t1 = r1.getTimestamp();
                Timestamp t2 = r2.getTimestamp();
                if (t1.before(t2)){
                    return 1;
                } else if (t1.after(t2)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        
        request.setAttribute("recipes", allRecipes);
    } 
}
