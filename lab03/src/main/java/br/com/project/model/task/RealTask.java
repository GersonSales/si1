package br.com.project.model.task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gersonsales on 04/02/17.
 */
@Entity
public class RealTask extends Task{
    private String category;
    private Priority priority;
    private String description;


    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<SubTask> subTaskList;

    public RealTask(String title, String description, String category, Priority priority) {
        super(title);
        this.description = description;
        this.category = category;
        this.priority = priority;
    }

    public RealTask() {
        this.category = "";
        this.priority = Priority.NONE;
        this.subTaskList = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return super.toString() + "RealTask{" +
                "category='" + category + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", subTaskList=" + subTaskList.toString() +
                '}';
    }

    public boolean isThatCategory(String category) {
        return getCategory().toString().equalsIgnoreCase(category);
    }

    public boolean isThatPriority(String priority) {
        return getPriority().toString().equalsIgnoreCase(priority);
    }

    public void addAllSubTask(List<SubTask> subTaskList) {
        this.subTaskList.addAll(subTaskList);
    }

    public List<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }


    public SubTask getSubTaskById(Long id) {
        for (SubTask subTask: subTaskList) {
            if (subTask.getId().equals(id)) {
                return subTask;
            }
        }
        return null;

    }

    public void checkSubTask(Long id) {
        SubTask subTask = getSubTaskById(id);
        if (subTask != null) {
            subTask.setChecked(true);
        }
    }

    public boolean containsSubtask(Long id) {
        return getSubTaskById(id) != null;
    }

}
