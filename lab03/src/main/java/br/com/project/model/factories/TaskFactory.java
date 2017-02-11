package br.com.project.model.factories;

import br.com.project.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gersonsales on 04/02/17.
 */
public class TaskFactory {

    public static Task createRealTask(String title, String description, String categoryStr, String priorityStr) {
//        Category category = null;//CategoryBank.valueOf(categoryStr);
        Priority priority = Priority.valueOf(priorityStr);

        Task realTask = new RealTask(title, description, "", priority);

        return realTask;
    }

    public static Task createSubTask(String title) {
        Task subTask = new SubTask(title);
        return subTask;
    }

    public static List<SubTask> getSubTaskList(String subtask) {
        subtask.replace(", ", ",");
        String[] subtaskString = subtask.split(",");
        List<SubTask> subTaskList = new ArrayList<>();
        for (String subtaskStr : subtaskString) {
            subTaskList.add(new SubTask(subtaskStr));
        }

        return subTaskList;
    }
}
