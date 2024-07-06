package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCreationServiceImpl implements ITaskCreationService {
    @Override
    public List<Task> creationActionableTasks() {
        return null;
    }
}
