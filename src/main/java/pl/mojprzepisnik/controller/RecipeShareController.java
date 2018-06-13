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

@WebServlet(name = "RecipeShareController", urlPatterns = {"/share"})
public class RecipeShareController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User loggedUser = (User)request.getSession().getAttribute("user");
            if (loggedUser != null) {
                Boolean is_shared = Boolean.parseBoolean(request.getParameter("is_shared"));
                if (is_shared != true){
                long recipe_id = Long.parseLong(request.getParameter("recipe_id"));
                shareRecipe(recipe_id, true);
                }
        }
        response.sendRedirect(request.getContextPath()+"/my_recipes");
    }
    
    private void shareRecipe(long recipe_id, boolean is_shared){
        RecipeService recipeService = new RecipeService();
        Boolean result = recipeService.shareRecipe(recipe_id, is_shared);
    }

}
