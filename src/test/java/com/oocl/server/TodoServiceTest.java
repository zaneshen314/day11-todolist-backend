package com.oocl.server;

import com.oocl.model.Todo;
import com.oocl.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    TodoService todoService;
    @Mock
    TodoRepository todoRepository;

    @Test
    void should_return_todos_when_findAll_given_todos() {
        //given
        when(todoRepository.findAll()).thenReturn(List.of(new Todo(1,"学习")));
        //when
        List<Todo> todos = todoService.findAll();

        //then
        assertEquals(1, todos.size());
        assertEquals("学习", todos.get(0).getText());
    }

    @Test
    void should_return_saved_todo_when_save_given_todo() {
        //given
        Todo todo = new Todo(1,"学习");
        when(todoRepository.save(todo)).thenReturn(todo);
        //when
        Todo savedTodo = todoService.save(todo);
        /* then */
        assertEquals(1, savedTodo.getId());
        assertEquals("学习", savedTodo.getText());
    }

    @Test
    void should_return_todo_when_getById_given_exist_todo_id() {
        //given
        Todo givenTodo = new Todo(1,"学习");
        when(todoRepository.findById(givenTodo.getId())).thenReturn(Optional.of(givenTodo));
        //when
        Todo todo = todoRepository.findById(1).orElse(null);
        /* then */
        assertEquals(givenTodo, todo);
    }

}
