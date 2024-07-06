package org.adoxx.socialmedia.services;

import org.adoxx.socialmedia.models.Task;

import java.util.List;

public interface ITaskCreationService {
    List<Task> creationActionableTasks();
}
