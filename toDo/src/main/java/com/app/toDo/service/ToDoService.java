package com.app.toDo.service;

import com.app.toDo.model.ToDoItem;
import com.app.toDo.repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepo toDoRepo;
    public List<ToDoItem> getAllItems(){
        ArrayList<ToDoItem> todoList = new ArrayList<>();
        toDoRepo.findAll().forEach(todo -> todoList.add(todo));

        return todoList;
    }
    public ToDoItem getToDoItemById(Long id){
        return toDoRepo.findById(id).get();
    }
    public boolean updateStatus(Long id){
        ToDoItem todo = getToDoItemById(id);
        todo.setStatus("Completed");
        return saveOrUpdate(todo);
    }
    public boolean saveOrUpdate(ToDoItem todo){
        ToDoItem obj = toDoRepo.save(todo);
        if(getToDoItemById(obj.getId())!=null){
            return true;
        }
        return false;
    }
    public boolean deleteToDoItem(Long id){
        try{
            toDoRepo.deleteById(id);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }
}
