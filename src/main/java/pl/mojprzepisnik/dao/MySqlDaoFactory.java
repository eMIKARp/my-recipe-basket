package pl.mojprzepisnik.dao;

public class MySqlDaoFactory extends DaoFactory{

    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public RecipeDao getRecipeDao() {
        return new RecipeDaoImpl();
    }

    @Override
    public VoteDao getVoteDao() {
        return new VoteDaoImpl();
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new CategoryDaoImpl();
    }
    
}
