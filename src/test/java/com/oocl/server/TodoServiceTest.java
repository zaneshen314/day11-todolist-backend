package com.oocl.server;

import com.oocl.model.Todo;
import com.oocl.repository.TodoRepository;
import com.oocl.server.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    TodoService todoService;
    @Mock
    TodoRepository todoRepository;

    @Test
    void should_return_the_given_todos_when_findAll() {
        //given
        when(todoRepository.findAll()).thenReturn(List.of(new Todo(1,"学习")));
        //when
        List<Todo> todos = todoService.findAll();

        //then
        assertEquals(1, todos.size());
        assertEquals("学习", todos.get(0).getText());
    }

    @Test
    void should_created_todo_when_create_todo() {
        //given
        Todo todo = new Todo(1,"学习");
        when(todoRepository.save(todo)).thenReturn(todo);
        //when
        Todo savedTodo = todoService.save(todo);
        /* then */
        assertEquals(1, savedTodo.getId());
        assertEquals("学习", savedTodo.getText());
    }

}
