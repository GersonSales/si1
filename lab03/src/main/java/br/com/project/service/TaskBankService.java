package br.com.project.service;

import br.com.project.model.factories.TaskFactory;
import br.com.project.model.task.*;
import br.com.project.repository.TaskBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gersonsales on 06/02/17.
 */
@Service
public class TaskBankService {

    @Autowired
    private TaskBankRepository taskBankRepository;

    private Set<String> categoryList;

    public TaskBankService() {
        this.categoryList = new HashSet<>();
    }

    public void addBank(String bankName) {
        TaskBank taskBank = new TaskBank(bankName);
        if (!taskBankRepository.findAll().contains(taskBank)) {
            taskBankRepository.save(taskBank);
        }

    }

    public TaskBank findByName(String bankName) {
        return taskBankRepository.findByName(bankName);
    }


    public List<RealTask> getAllTasks() {
        List<RealTask> allTasks = new ArrayList<>();
        for (TaskBank taskBank : taskBankRepository.findAll()){
            allTasks.addAll(taskBank.getAllTasks());
        }
        return allTasks;
    }

    public void addTask(String bankName, RealTask task) {
        TaskBank taskBank = findByName(bankName);
        taskBank.addTask(task);
        taskBankRepository.save(taskBank);



    }

    public List<String> getBankNames() {
        List<TaskBank> taskBankList = taskBankRepository.findAll();
        List<String> bankNames = taskBankList.stream().map(TaskBank::getName).collect(Collectors.toList());
        Collections.sort(bankNames);


        return bankNames;
    }

    public Task getTaskById(Long id) {
        Task foundTask = null;
        for (TaskBank taskBank : taskBankRepository.findAll()) {
            foundTask = taskBank.getTaskById(id);
            if (foundTask != null) {
                return foundTask;
            }
        }
        return foundTask;


    }

    public void removeTask(Long id) {
        for (TaskBank taskBank : taskBankRepository.findAll()) {
            taskBank.removeTask(id);
            taskBankRepository.save(taskBank);
        }

    }


    public List<RealTask> getTasksByBank(String bankName) {
        TaskBank taskBank = taskBankRepository.findByName(bankName);
        return taskBank.getAllTasks();
    }

    public List<RealTask> getTasksByCategory(String category) {
        List<RealTask> result = new ArrayList<>();
        for (Task task : getAllTasks()) {
            if (task instanceof RealTask) {
                RealTask realTask = (RealTask)task;
                if (realTask.isThatCategory(category)) {
                    result.add(realTask);
                }
            }
        }

        return result;
    }

    public void addCategory(String category) {
        categoryList.add(category);
    }

    public List<String> getCategories() {
//        taskBankList.stream().map(TaskBank::getName).collect(Collectors.toList());
        categoryList.addAll(getAllTasks().stream().map(RealTask::getCategory).collect(Collectors.toSet()));
        List<String> categories = categoryList.stream().collect(Collectors.toList());
        Collections.sort(categories);
        return categories;
    }

    public List<Priority> getPriorities() {
        return Arrays.asList(Priority.values());
    }

    public List<RealTask> getTasksByPriority(String priority) {
        List<RealTask> result = new ArrayList<>();
        for (Task task : getAllTasks()) {
            if (task instanceof RealTask) {
                RealTask realTask = (RealTask)task;
                if (realTask.isThatPriority(priority)) {
                    result.add(realTask);
                }
            }
        }
        return result;
    }


    public void addTaskWithSubTask(String bankName, RealTask task, String subtask) {
        TaskBank taskBank = findByName(bankName);
        List<SubTask> subTaskList = TaskFactory.getSubTaskList(subtask);
        task.addAllSubTask(subTaskList);
        taskBank.addTask(task);
        taskBankRepository.save(taskBank);
    }

    private void updateBank() {
        getAllTasks().forEach(taskBankRepository::save);
    }

    public void checkTaskById(Long id) {
        getTaskById(id).setChecked(true);
        updateBank();
    }


    private SubTask getSubTaskById(Long id) {
        for (RealTask realTask : getAllTasks()) {
            SubTask subTask = realTask.getSubTaskById(id);
            if (subTask != null) {
                return subTask;
            }
        }
        return null;
    }

    public void checkSubTaskById(Long id) {
        getSubTaskById(id).setChecked(true);
        updateBank();
    }



    public void removeTaskBank(String bankName) {
        TaskBank taskBank = findByName(bankName);
        taskBankRepository.delete(taskBank);
    }

    public void removeAllTasks() {
        taskBankRepository.findAll().forEach(TaskBank::removeAllTasks);
        taskBankRepository.findAll().forEach(taskBankRepository::save);

    }


    public Long getTaskId(Long id) {
        for (RealTask realTask : getAllTasks()) {
            if (realTask.containsSubtask(id)) {
                return realTask.getId();
            }
        }
        return null;

    }

    public List<RealTask> getUncheckedTasks() {
        List<RealTask> uncheckedTasks = new ArrayList<>();
        for (RealTask task : getAllTasks()) {
            if (!task.isChecked()) {
                uncheckedTasks.add(task);
            }
        }
        return uncheckedTasks;

    }

    public Integer getQtdUncheckedTasks() {
        return getUncheckedTasks().size();
    }

    public void checkAllTasks() {
        for (Task task : getAllTasks()) {
            task.setChecked(true);
        }
        updateBank();
    }
}
