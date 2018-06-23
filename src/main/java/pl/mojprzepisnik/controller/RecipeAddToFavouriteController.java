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

@WebServlet(name = "RecipeAddToFavouriteController", urlPatterns = {"/like"})
public class RecipeAddToFavouriteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User loggedUser = (User)request.getSession().getAttribute("user");
        if (loggedUser!= null) {
            long recipe_id = Long.parseLong(request.getParameter("recipe_id"));
            long user_id = Long.parseLong(request.getParameter("recipe_user_id"));
            if (user_id != loggedUser.getId()){
                if (!checkIfAllreadyFavourite(recipe_id, loggedUser.getUsername())){
                    addToFavourite(recipe_id, loggedUser.getUsername());
                }
            }
        }
        
        response.sendRedirect(request.getContextPath()+"/");
    }
    
    private void addToFavourite(Long recipe_id, String username){
        RecipeService recipeService = new RecipeService();
        recipeService.addToFavourite(recipe_id, username);
    }
    
    private boolean checkIfAllreadyFavourite(Long recipe_id, String username){
        RecipeService recipeService = new RecipeService();
        Boolean result = recipeService.checkIfAllreadyFavourite(recipe_id, username);
        
        return result;
    }

}
