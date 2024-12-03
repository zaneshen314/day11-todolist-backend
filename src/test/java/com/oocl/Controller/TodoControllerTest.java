package com.oocl.Controller;

import com.oocl.model.Todo;
import com.oocl.repository.TodoRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class TodoControllerTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private JacksonTester<List<Todo>> todoListJacksonTester;

    @BeforeEach
    void setUp() {
        givenDataInJpaRepository();
    }

    private void givenDataInJpaRepository() {
        todoRepository.deleteAll();
        todoRepository.save(new Todo(null, "study"));
        todoRepository.save(new Todo(null, "run"));
        todoRepository.save(new Todo(null, "money"));
    }

    @Test
    void should_return_todos_when_find_all_given_todo_exist() throws Exception {
        //given
        final List<Todo> givenTodos = todoRepository.findAll();

        //when
        //then
        final String jsonResponse = client.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        final List<Todo> todosResult = todoListJacksonTester.parseObject(jsonResponse);
        assertThat(todosResult)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_return_new_todo_when_save_given_todo() throws Exception {
        // Given
        todoRepository.deleteAll();
        String text = "text";
        String giveTodo = String.format(
                "{\"text\": \"%s\", \"done\": \"%s\"}",
                text, false
        );

        // When
        // Then
        client.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giveTodo)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(text));
    }

    @Test
    void should_return_update_todo_when_update_given_todo() throws Exception {
        // Given
        List<Todo> giveTodos = todoRepository.findAll();
        Integer givenId = giveTodos.get(0).getId();
        String updateText = "updateText";
        String givenTodo = String.format(
                "{\"id\": %s, \"text\": \"%s\", \"done\": \"%s\"}",
                givenId,
                updateText,
                true
        );

        // When
        // Then
        client.perform(MockMvcRequestBuilders.put("/todos/" + givenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenTodo)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(updateText))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(true));
    }

    @Test
    void should_remove_todo_success() throws Exception {
        // Given
        List<Todo> givenTodos = todoRepository.findAll();
        Todo givenTodo = givenTodos.get(0);
        int givenId = givenTodo.getId();

        // When
        // Then
        client.perform(MockMvcRequestBuilders.delete("/todos/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Todo> afterEmployees = todoRepository.findAll();
        assertThat(afterEmployees).hasSize(2);
        AssertionsForClassTypes.assertThat(afterEmployees.get(0).getId()).isEqualTo(givenTodos.get(1).getId());
        AssertionsForClassTypes.assertThat(afterEmployees.get(1).getId()).isEqualTo(givenTodos.get(2).getId());
    }

}
