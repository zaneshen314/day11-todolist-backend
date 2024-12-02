package com.oocl.server;

import com.oocl.Exception.TodoNotFoundException;
import com.oocl.model.Todo;
import com.oocl.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    public Todo save(Todo todo){
        return todoRepository.save(todo);
    }
    public Todo update(Todo todo){
        if(getById(todo.getId()) == null){
            throw new TodoNotFoundException();
        }
        return todoRepository.save(todo);
    }

    public void delete(Integer id){
        todoRepository.deleteById(id);
    }

    public Todo getById(Integer id){
        return todoRepository.findById(id).orElse(null);
    }

}
