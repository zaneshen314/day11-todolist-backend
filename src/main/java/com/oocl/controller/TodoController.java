package com.oocl.controller;

import com.oocl.model.Todo;
import com.oocl.server.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getTodos() {
        List<Todo> all = todoService.findAll();
        return todoService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Integer id) {
        todoService.delete(id);
    }

    @PutMapping("/{id}")
    public Todo update(@RequestBody Todo todo) {
        return todoService.update(todo);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Todo save(@RequestBody Todo todo) {
        return todoService.save(todo);
    }

}
