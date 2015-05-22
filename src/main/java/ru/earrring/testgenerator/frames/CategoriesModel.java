package ru.earrring.testgenerator.frames;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nenagleyko on 22.05.2015.
 */
public class CategoriesModel extends AbstractListModel<String> {

    private List<String> categories;
    public CategoriesModel()
    {
        super();
        categories = new ArrayList<>();
    }
    public void setCategories(List<String> categories)
    {
        this.categories = categories;
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public String getElementAt(int index) {
        return categories.get(index);
    }
}
