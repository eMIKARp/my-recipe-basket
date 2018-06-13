package pl.mojprzepisnik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.mojprzepisnik.model.User;
import pl.mojprzepisnik.service.RecipeService;

@WebServlet(name = "RecipeRemoveController", urlPatterns = {"/remove"})
public class RecipeRemoveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User loggedUser = (User)request.getSession().getAttribute("user");
        if (loggedUser!= null) {
            long recipe_id = Long.parseLong(request.getParameter("recipe_id"));
            long recipe_user_id = Long.parseLong(request.getParameter("recipe_user_id"));
            if (loggedUser.getId() == recipe_user_id){
                removeRecipe(recipe_id);
            }            
        }
        
        response.sendRedirect(request.getContextPath()+"/my_recipes");
    }
    
    private void removeRecipe(long recipe_id){
        RecipeService recipeService = new RecipeService();
        recipeService.deleteRecipe(recipe_id);
    }

}
