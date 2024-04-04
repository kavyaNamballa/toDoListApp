package com.app.toDo.controller;

import com.app.toDo.model.ToDoItem;
import com.app.toDo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @RequestMapping(value={"", "/", "view"})
    public String displayItems(Model model){
        model.addAttribute("list",toDoService.getAllItems());
        return "viewItems.html";
    }

    @GetMapping("/add")
    public String addToDoItem(Model model){
        model.addAttribute("todo",new ToDoItem());
        return "addToDoItem.html";
    }

    @PostMapping("/save")
    public String saveToDoItem(ToDoItem todo, RedirectAttributes redirectAttributes){
        if(toDoService.saveOrUpdate(todo)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Save Failure");
        }
        return "redirect:/view"; // Use correct URL for redirect
    }

    @GetMapping("/deleteToDoItem/{id}")
    public String deleteToDoItem(@PathVariable Long id){
        toDoService.deleteToDoItem(id);
        return "redirect:/view";
    }
    @GetMapping("/markCompleted/{id}")
    public String updateToDoItem(@PathVariable Long id){
        toDoService.updateStatus(id);
        return "redirect:/view";
    }

    @GetMapping("/editToDoItem/{id}")
    public String editToDoItem(@PathVariable Long id,Model model){
        model.addAttribute("todo",toDoService.getToDoItemById(id));
        return "editToDoItem";
    }

    @PostMapping("/edit")
    public String saveEditedItem(ToDoItem toDoItem){
        toDoService.saveOrUpdate(toDoItem);
        return "redirect:/view";
    }
}

