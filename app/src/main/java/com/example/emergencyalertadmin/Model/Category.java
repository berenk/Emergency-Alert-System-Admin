package com.example.emergencyalertadmin.Model;

public class Category implements ICategory {
    String category;
    String description;

    public Category(String category, String description) {
        this.category = category;
        this.description = description;
    }

    @Override
    public String getCategoryName() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
