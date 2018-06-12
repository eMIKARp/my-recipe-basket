package pl.mojprzepisnik.model;

import java.util.Objects;

public class Category {

    private String name;
    private CategoryType cType;

    public Category() {
    }

    public Category(String name, CategoryType cType) {
        this.name = name;
        this.cType = cType;
    }
    
    public Category(Category category) {
        this.name = category.name;
        this.cType = category.cType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getcType() {
        return cType;
    }

    public void setcType(CategoryType cType) {
        this.cType = cType;
    }

    @Override
    public String toString() {
        return "Category{" + "name=" + name + ", cType=" + cType + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.cType);
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
        final Category other = (Category) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.cType != other.cType) {
            return false;
        }
        return true;
    }
    
    
}
