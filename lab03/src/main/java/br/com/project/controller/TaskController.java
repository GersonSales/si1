package br.com.project.controller;

import br.com.project.model.task.RealTask;
import br.com.project.model.task.TaskListShown;
import br.com.project.service.TaskBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by gersonsales on 04/02/17.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskBankService taskBankService;

//    private List<Task> taskList;
    private TaskListShown taskListShown;

    public TaskController() {
//        this.taskList = new ArrayList<>();
        taskListShown= new TaskListShown();
    }

    @ModelAttribute("taskList")
    TaskListShown getTaskList() {
        return this.taskListShown;
    }

//    void setTaskList(List<Task> taskList) {
//        this.taskList.clear();
//        this.taskList.addAll(taskList);
//    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String taskIndex() {
        return "redirect:/task/allTasks";
    }

    @RequestMapping(value = "/taskList", method = RequestMethod.GET)
    public String taskList(ModelMap model) {
        model.addAttribute("categoryList", taskBankService.getCategories());
        model.addAttribute("priorityList", taskBankService.getPriorities());
        model.addAttribute("taskBankList", taskBankService.getBankNames());
        model.addAttribute("priorityList", taskBankService.getPriorities());
        model.addAttribute("uncheckedTask", taskBankService.getQtdUncheckedTasks());

        return "tasklist";
    }

    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public String allTasks(ModelMap model) {
        taskListShown.updateList(taskBankService.getAllTasks());
        return "redirect:/task/taskList";
    }

    @RequestMapping(value = "/uncheckedTasks", method = RequestMethod.GET)
    public String uncheckedTasks(ModelMap model) {
        taskListShown.updateList(taskBankService.getUncheckedTasks());
        return "redirect:/task/taskList";
    }

    @RequestMapping(value = "/taskListByBank", method = RequestMethod.GET)
    public String taskListByBank(String bankName, Model model) {
        taskListShown.updateList(taskBankService.getTasksByBank(bankName));
        model.addAttribute("listName", bankName.toUpperCase());

        return "redirect:/task/taskList";
    }

    @RequestMapping(value = "/taskListByCategory", method = RequestMethod.GET)
    public String taskListByCategory(String category, Model model) {
        taskListShown.updateList(taskBankService.getTasksByCategory(category));
        model.addAttribute("listName", category.toUpperCase());
        return "redirect:/task/taskList";
    }


    @RequestMapping(value = "/taskListByPriority", method = RequestMethod.GET)
    public String taskListByPriority(String priority, Model model) {
        taskListShown.updateList(taskBankService.getTasksByPriority(priority));
        model.addAttribute("listName", priority.toUpperCase());

        return "redirect:/task/taskList";
    }

    @RequestMapping(value = "/{subTaskNumber}", method = RequestMethod.GET)
    public String newTask(@PathVariable Integer subTaskNumber, Model model) {
        model.addAttribute("subTaskNumber", subTaskNumber);
        List<String> bankNames = taskBankService.getBankNames();
        List<String> categoryList = taskBankService.getCategories();

        model.addAttribute("bankNames", bankNames);
        model.addAttribute("categoryList", categoryList);

        return "realtaskform";
    }

    @RequestMapping(value = "/newRealTask", method = RequestMethod.GET)
    public String newTask(Model model) {
        List<String> bankNames = taskBankService.getBankNames();
        List<String> categoryList = taskBankService.getCategories();

        model.addAttribute("bankNames", bankNames);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("subTaskNumber", 1);


        return "realtaskform";
    }

        @RequestMapping(value = "/newCategory", method = RequestMethod.GET)
    public String newCategory(Model model) {
        return "categoryform";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(String category) {
        taskBankService.addCategory(category);
        return "redirect:/task/allTasks";

    }

    @RequestMapping(value = "/addRealTask", method = RequestMethod.POST)
    public String addTask(@ModelAttribute RealTask task, String bankName, String subtask, Model  model) {
        taskBankService.addTaskWithSubTask(bankName, task, subtask);
        return "redirect:/task/allTasks";
    }

    @RequestMapping(value = "/addSubTask", method = RequestMethod.GET)
    public String addSubTask(String subTaskNumber, Model model) {
        Integer newValue = Integer.valueOf(subTaskNumber) + 1;

        return "redirect:/task/" + newValue ;
    }

    @RequestMapping(value = "/removeSubTask", method = RequestMethod.GET)
    public String removeSubTask(String subTaskNumber, Model model) {
        Integer newValue = Integer.valueOf(subTaskNumber) - 1;
        newValue = newValue > 0 ? newValue : 1;

        return "redirect:/task/" + newValue ;
    }

    @RequestMapping(value = "/removeTaskBank", method = RequestMethod.GET)
    public String removeTaskBank(String taskBank) {
        taskBankService.removeTaskBank(taskBank);
        return "redirect:/task/allTasks";

    }

    @RequestMapping(value = "/removeAllTasks", method = RequestMethod.GET)
    public String removeAllTasks() {
        taskBankService.removeAllTasks();

        return "redirect:/task/allTasks";
    }

    @RequestMapping(value = "/checkAllTasks", method = RequestMethod.GET)
    public String checkAllTasks() {
        taskBankService.checkAllTasks();

        return "redirect:/task/allTasks";
    }


    @RequestMapping(value = "/showRealTask", method = RequestMethod.GET)
    public String showRealTask(Long id, Model model) {
        model.addAttribute("task", taskBankService.getTaskById(id));
        return "showrealtask";
    }

    @RequestMapping(value = "/removeRealTask", method = RequestMethod.GET)
    public String removeRealTask(Long id) {
        taskBankService.removeTask(id);
        return "redirect:/task/allTasks";
    }

    @RequestMapping(value = "/checkTask", method = RequestMethod.GET)
    public String checkTask(Long id, Model model) {
        taskBankService.checkTaskById(id);
        taskListShown.checkTaskById(id);


        return "forward:/task/taskList";
    }

    @RequestMapping(value = "/checkSubTask", method = RequestMethod.GET)
    public String checkSubTask(Long id, Model model) {
        taskBankService.checkSubTaskById(id);
        taskListShown.checkSubTaskById(id);
        id = taskBankService.getTaskId(id);


        return "forward:/task/showRealTask?id=" + id;
    }



    @RequestMapping(value = "/newTaskBank", method = RequestMethod.GET)
    public String newTaskBank() {
        return "taskbankform";
    }

    @RequestMapping(value = "/addTaskBank", method = RequestMethod.POST)
    public String addTaskBank(String bankName) {
        taskBankService.addBank(bankName);
        return "redirect:/task/allTasks";

    }

    @RequestMapping(value = "/contactUs", method = RequestMethod.GET)
    public String addTaskBank() {
        return "contactus";
    }


    @RequestMapping(value = "/sortByName", method = RequestMethod.GET)
    public String sortListByName() {
        taskListShown.sortByName();
        return "redirect:/task/taskList";
    }

    @RequestMapping(value = "/sortByPriority", method = RequestMethod.GET)
    public String sortListByPriority() {
        taskListShown.sortByPriority();
        return "redirect:/task/taskList";
    }




}
