package pl.mojprzepisnik.service;

import java.sql.Timestamp;
import java.util.Date;
import pl.mojprzepisnik.dao.DaoFactory;
import pl.mojprzepisnik.dao.VoteDao;
import pl.mojprzepisnik.model.Vote;
import pl.mojprzepisnik.model.VoteType;

public class VoteService {
    
    public Vote addVote(long recipe_id, long user_id, VoteType voteType) {
        
        Vote vote = new Vote();
        vote.setRecipe_id(recipe_id);
        vote.setUser_id(user_id);
        vote.setTimestamp(new Timestamp(new Date().getTime()));
        vote.setVoteType(voteType);
        DaoFactory factory = DaoFactory.getDaoFactory();
        VoteDao voteDao = factory.getVoteDao();
        vote = voteDao.create(vote);
        return vote;
	
    }
    
    public Vote updateVote(long recipe_id, long user_id, VoteType voteType) {
        DaoFactory factory = DaoFactory.getDaoFactory();
        VoteDao voteDao = factory.getVoteDao();
        Vote voteToUpdate = voteDao.getVoteByUserIdRecipeId(user_id, recipe_id);
        if(voteToUpdate != null) {
                voteToUpdate.setVoteType(voteType);
                voteDao.update(voteToUpdate);
        }
        return voteToUpdate;
    }
    
    public Vote addOrUpdateVote(long recipe_id, long user_id, VoteType voteType) {
        DaoFactory factory = DaoFactory.getDaoFactory();
        VoteDao voteDao = factory.getVoteDao();
        Vote vote = voteDao.getVoteByUserIdRecipeId(user_id, recipe_id);
        Vote resultVote = null;
        if(vote == null) {
                resultVote = addVote(recipe_id, user_id, voteType);
        } else {
                resultVote = updateVote(recipe_id, user_id, voteType);
        }
        return resultVote;
    }
 
    public Vote getVoteByRecipeUserId(long recipe_id, long user_id) {
        DaoFactory factory = DaoFactory.getDaoFactory();
        VoteDao voteDao = factory.getVoteDao();
        Vote vote = voteDao.getVoteByUserIdRecipeId(user_id, recipe_id);
        return vote;
    }
}
