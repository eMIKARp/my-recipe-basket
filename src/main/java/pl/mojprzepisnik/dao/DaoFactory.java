package pl.mojprzepisnik.dao;

import pl.mojprzepisnik.exception.NoSuchDbTypeException;


public abstract class DaoFactory {
    
    public static final int MYSQL_DAO_FACTORY = 1;
    
    public abstract UserDao getUserDao();
    public abstract RecipeDao getRecipeDao();
    public abstract VoteDao getVoteDao();
    public abstract CategoryDao getCategoryDao();
    
    public static DaoFactory getDaoFactory(){
        
        DaoFactory factory = null;
        try {
            factory = getDaoFactory(MYSQL_DAO_FACTORY);
        } catch (NoSuchDbTypeException ex){
            ex.printStackTrace();
        }
        
        return factory;
    }
    
    public static DaoFactory getDaoFactory(int type) throws NoSuchDbTypeException{
        
        switch(type){
            case MYSQL_DAO_FACTORY:
                return new MySqlDaoFactory();
            default: 
                throw new NoSuchDbTypeException();
        }
    }
}
