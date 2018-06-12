package pl.mojprzepisnik.dao;

import pl.mojprzepisnik.model.Vote;

public interface VoteDao extends GenericDao<Vote, Long>{
    
    public Vote getVoteByUserIdRecipeId(long userId, long recipeId);

}
