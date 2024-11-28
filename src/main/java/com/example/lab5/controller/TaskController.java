
package com.example.lab5.controller;

import com.example.lab5.model.Task;
import com.example.lab5.model.User;
import com.example.lab5.repository.TaskRepository;
import com.example.lab5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listTasks(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("tasks", taskRepository.findByUser(user));
        return "tasks/list";
    }

    @GetMapping("/add")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/add";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/tasks";
    }
}
