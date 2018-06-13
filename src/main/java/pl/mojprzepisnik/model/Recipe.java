package pl.mojprzepisnik.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Recipe {

    private long id;
    private String name;
    private String description;
    private String ulr;
    private Timestamp timestamp;
    private int upVote;
    private int downVote;
    private boolean isShared;
    private User user;
    private Category[] category;

    public Recipe() {
    }

    public Recipe(long id, String name, String description, String ulr, Timestamp timestamp, int upVote, int downVote, boolean isShared, User user, Category[] category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ulr = ulr;
        this.timestamp = timestamp;
        this.upVote = upVote;
        this.downVote = downVote;
        this.isShared = isShared;
        this.user = user;
        this.category = category;
    }
    
    public Recipe(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.ulr = recipe.getUlr();
        this.timestamp = new Timestamp(recipe.getTimestamp().getTime());
        this.upVote = recipe.getUpVote();
        this.downVote = recipe.getDownVote();
        this.isShared = recipe.isIsShared();
        this.user = new User(recipe.getUser());
        this.category = recipe.getCategory();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUlr() {
        return ulr;
    }

    public void setUlr(String ulr) {
        this.ulr = ulr;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }

    public boolean isIsShared() {
        return isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category[] getCategory() {
        return category;
    }

    public void setCategory(Category[] category) {
        this.category = category;
    }
    
    public void appendCategory (Category[] category){

        List<Category> categoryList = new ArrayList<Category>();
        Category[] categoryArray = new Category[this.category.length+category.length];
        
        for (int i = 0; i < this.category.length;i++){
            categoryList.add(this.category[i]);
        }
        for (int i = 0; i < category.length;i++){
            categoryList.add(category[i]);
        }
        categoryList.toArray(categoryArray);
        setCategory(categoryArray);
    }

    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", description=" + description + ", ulr=" + ulr + ", timestamp=" + timestamp + ", upVote=" + upVote + ", downVote=" + downVote + ", isShared=" + isShared + ", user=" + user + ", category=" + category + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.ulr);
        hash = 97 * hash + Objects.hashCode(this.timestamp);
        hash = 97 * hash + this.upVote;
        hash = 97 * hash + this.downVote;
        hash = 97 * hash + (this.isShared ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.user);
        hash = 97 * hash + Arrays.deepHashCode(this.category);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.upVote != other.upVote) {
            return false;
        }
        if (this.downVote != other.downVote) {
            return false;
        }
        if (this.isShared != other.isShared) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.ulr, other.ulr)) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Arrays.deepEquals(this.category, other.category)) {
            return false;
        }
        return true;
    }

    
    
}
