package pl.mojprzepisnik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.service.RecipeService;

@WebServlet(name = "HomeController", urlPatterns = {"/index"})
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveRecipesInRequest(request);
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }
    
    private void saveRecipesInRequest(HttpServletRequest request){
        RecipeService recipeService = new RecipeService();
        List<Recipe> allRecipes = recipeService.getAllRecipesIfShared(new Comparator<Recipe>() {
            @Override
            public int compare(Recipe r1, Recipe r2) {
                int r1Vote = r1.getUpVote() - r1.getDownVote();
                int r2Vote = r2.getUpVote() - r2.getDownVote();
                if (r1Vote < r2Vote){
                    return 1;
                } else if (r1Vote > r2Vote) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }); 
        
        request.setAttribute("recipes", allRecipes);
    } 

}
