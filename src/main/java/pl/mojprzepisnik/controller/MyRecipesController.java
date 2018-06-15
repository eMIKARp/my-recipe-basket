package pl.mojprzepisnik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.mojprzepisnik.model.Category;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.service.CategoryService;
import pl.mojprzepisnik.service.RecipeService;

@WebServlet(name = "MyRecipesController", urlPatterns = {"/my_recipes"})
public class MyRecipesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String category_name = request.getParameter("category_name");
        saveRecipesInRequest(category_name, request);
        saveCategoriesInRequest(request);
        request.getRequestDispatcher("WEB-INF/my_recipes.jsp").forward(request, response);
    }
    
    private void saveCategoriesInRequest(HttpServletRequest request){
     CategoryService categoryService = new CategoryService();
     List<Category> allCategories = categoryService.getAllCategories(new Comparator<Category>() {
         @Override
         public int compare(Category c1, Category c2) {
             String c1Name = c1.getName();
             String c2Name = c2.getName();

             if (c1Name.compareToIgnoreCase(c2Name) > 0){
                 return 1;
             } else if (c1Name.compareToIgnoreCase(c2Name) < 0){
                 return -1;
             } else return 0;
         }
     });
        
        request.setAttribute("categories", allCategories);
    }
    
    
    private void saveRecipesInRequest(String category_name, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        RecipeService recipeService = new RecipeService();
        List<Recipe> allFavouriteRecipes = recipeService.getFavouriteRecipesByUsername(username);
        List<Recipe> allRecipes = recipeService.getAllRecipesByUsername(username); 
        allRecipes.addAll(allFavouriteRecipes);
        if (category_name!=null){
            allRecipes = filterRecipesByCategory(category_name, allRecipes);
        }
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
    
    private List<Recipe> filterRecipesByCategory (String category_name, List<Recipe> recipesToFilter){
        List<Recipe> filteredRecipes = new ArrayList<Recipe>();
        
        for (int i=0; i<recipesToFilter.size();i++){
            for (int j=0; j<recipesToFilter.get(i).getCategory().size();j++){
                 if (recipesToFilter.get(i).getCategory().get(j).getName().equals(category_name)){
                     filteredRecipes.add(recipesToFilter.get(i));
                }
            }
        }
        return filteredRecipes;
    }
}


