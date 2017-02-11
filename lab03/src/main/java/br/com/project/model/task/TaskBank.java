package br.com.project.model.task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gersonsales on 05/02/17.
 */
@Entity
public class TaskBank implements Comparable<TaskBank>{
    @Id
    @GeneratedValue(generator="STORE_SEQ")
    @SequenceGenerator(name="STORE_SEQ",sequenceName="STORE_SEQ", allocationSize=1)
    private Long id;
    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<RealTask> taskList;



    public TaskBank() {
        this.taskList = new ArrayList<>();
    }

    public TaskBank(String name) {
        this();
        this.name = name;
    }

    public void addTask(RealTask task) {
        taskList.add(task);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    public Task getTaskById(Long id) {
        for (Task task: taskList) {
            if (task.getId().equals(id)) {
                return task;
            }

        }
        return  null;
    }

    public void updateTask(RealTask task) {
        Task foundTask = getTaskById(task.getId());
        if (foundTask != null) {
            taskList.remove(foundTask);
            taskList.add(task);
        }
    }



    public List<RealTask> getAllTasks() {
        return taskList;
    }

    public void removeTask(Long id) {
        Task foundTask = getTaskById(id);
        System.out.println("<-------" + foundTask + "-------->");
        taskList.remove(foundTask);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "TaskBank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taskList=" + taskList +
                '}';
    }

    @Override
    public int compareTo(TaskBank otherTaskBank) {
        return getName().compareTo(otherTaskBank.getName());
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskBank)) return false;

        TaskBank taskBank = (TaskBank) o;

        return getName().equals(taskBank.getName());

    }



    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public void removeAllTasks() {
        this.taskList.clear();
    }
}
