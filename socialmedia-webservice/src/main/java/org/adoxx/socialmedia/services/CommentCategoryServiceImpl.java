package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.CategoryResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentCategoryServiceImpl implements ICommentCategoryService {
    @Override
    public List<CategoryResult> getCategories(String text) {
        return null;
    }
}
