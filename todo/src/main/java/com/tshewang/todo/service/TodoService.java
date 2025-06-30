package com.tshewang.todo.service;

import com.tshewang.todo.request.TodoRequest;
import com.tshewang.todo.response.TodoResponse;

import java.util.List;

public interface TodoService {
    List<TodoResponse> getAllTodos();
    TodoResponse createTodo(TodoRequest todoRequest);
    TodoResponse toggleTodoCompletion(long id);
    void deleteTodo(long id);


}
