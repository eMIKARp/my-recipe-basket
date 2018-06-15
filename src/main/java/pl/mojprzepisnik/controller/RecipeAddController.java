package pl.mojprzepisnik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.mojprzepisnik.model.Category;
import pl.mojprzepisnik.model.CategoryType;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.model.User;
import pl.mojprzepisnik.service.RecipeService;

@WebServlet(name = "RecipeAddController", urlPatterns = {"/add"})
public class RecipeAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getUserPrincipal()!=null){
            request.getRequestDispatcher("WEB-INF/add.jsp").forward(request, response);
        } else {
            response.sendError(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("inputRecipeName");
        String description = request.getParameter("inputDescription");
        String url = request.getParameter("inputUrl");
        List<String> categoryNameList = Arrays.asList(request.getParameterValues("inputCategory"));
        List<Category> categoryList = new ArrayList<Category>();
        for (int i=0; i < categoryNameList.size();i++){
            categoryList.add(new Category(categoryNameList.get(i),CategoryType.PRE_DEFINED));
        }
        User authenticatedUser = (User)request.getSession().getAttribute("user");
        if (request.getUserPrincipal()!= null){
            RecipeService recipeService = new RecipeService();
            recipeService.addRecipe(name, description, url, authenticatedUser, categoryList);
            response.sendRedirect(request.getContextPath()+"/my_recipes");
        } else {
            response.sendError(403);
        }
        
    }

  
}
