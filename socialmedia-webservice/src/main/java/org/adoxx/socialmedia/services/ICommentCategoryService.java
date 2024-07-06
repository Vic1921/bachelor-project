package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.CategoryResult;

import java.util.List;

public interface ICommentCategoryService {
    List<CategoryResult> getCategories(String text);
}
