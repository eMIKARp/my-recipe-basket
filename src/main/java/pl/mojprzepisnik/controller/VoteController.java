package pl.mojprzepisnik.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.mojprzepisnik.model.Recipe;
import pl.mojprzepisnik.model.User;
import pl.mojprzepisnik.model.Vote;
import pl.mojprzepisnik.model.VoteType;
import pl.mojprzepisnik.service.RecipeService;
import pl.mojprzepisnik.service.VoteService;

@WebServlet(name = "VoteController", urlPatterns = {"/vote"})
public class VoteController extends HttpServlet {
    
    private final static long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        User loggedUser = (User)request.getSession().getAttribute("user");
        if (loggedUser != null) {
           VoteType voteType = VoteType.valueOf(request.getParameter("vote"));
           long user_id = loggedUser.getId();
           long recipe_id = Long.parseLong(request.getParameter("recipe_id"));
           updateVote(user_id, recipe_id, voteType);
        } 
        response.sendRedirect(request.getContextPath()+"/index");
    }
    
    private void updateVote(long user_id, long recipe_id, VoteType voteType) {
        VoteService voteService = new VoteService();
        Vote existingVote = voteService.getVoteByRecipeUserId(recipe_id, user_id);
        Vote updatedVote = voteService.addOrUpdateVote(recipe_id, user_id, voteType);
        if(existingVote != updatedVote || !updatedVote.equals(existingVote)) {
                updateRecipe(recipe_id, existingVote, updatedVote);
        }
    }
    
    private void updateRecipe(long recipe_id, Vote oldVote, Vote newVote) {
        RecipeService recipeService = new RecipeService();
        Recipe recipeById = recipeService.getRecipeById(recipe_id);
        Recipe updatedRecipe = null;
        if(oldVote == null && newVote != null) {
                updatedRecipe = addRecipeVote(recipeById, newVote.getVoteType());
        } else if(oldVote != null && newVote != null) {
                updatedRecipe = removeRecipeVote(recipeById, oldVote.getVoteType());
                updatedRecipe = addRecipeVote(updatedRecipe, newVote.getVoteType());
        }
        recipeService.updateRecipe(updatedRecipe);
    }

    private Recipe addRecipeVote(Recipe recipe, VoteType voteType) {
        Recipe recipeCopy = new Recipe(recipe);
        if(voteType == VoteType.VOTE_UP) {
                recipeCopy.setUpVote(recipeCopy.getUpVote() + 1);
        } else if(voteType == VoteType.VOTE_DOWN) {
                recipeCopy.setDownVote(recipeCopy.getDownVote() + 1);
        }
        return recipeCopy;
    }

    private Recipe removeRecipeVote(Recipe recipe, VoteType voteType) {
            Recipe recipeCopy = new Recipe(recipe);
            if(voteType == VoteType.VOTE_UP) {
                    recipeCopy.setUpVote(recipeCopy.getUpVote() - 1);
            } else if(voteType == VoteType.VOTE_DOWN) {
                    recipeCopy.setDownVote(recipeCopy.getDownVote() - 1);
            }
            return recipeCopy;
    }
}
