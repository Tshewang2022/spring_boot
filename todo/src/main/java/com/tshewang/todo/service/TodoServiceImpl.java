package com.tshewang.todo.service;

import com.tshewang.todo.entity.Todo;
import com.tshewang.todo.entity.User;
import com.tshewang.todo.repository.TodoRepository;
import com.tshewang.todo.request.TodoRequest;
import com.tshewang.todo.response.TodoResponse;
import com.tshewang.todo.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
       User curentUser = findAuthenticatedUser.getAuthenticatedUser();
       return todoRepository.findByOwner(curentUser).stream().map(this::convertTodoResponse).toList();
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = new Todo(
                todoRequest.getTitle(),
                todoRequest.getDescription(),
                todoRequest.getPriority(),
                false,
                currentUser
        );
        Todo savedTodo = todoRepository.save(todo);

        return convertTodoResponse(savedTodo);
    }


    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(long id) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, currentUser);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }

        todo.get().setComplete(!todo.get().isComplete());
        Todo updatedTodo = todoRepository.save(todo.get());

        return convertTodoResponse(updatedTodo);
    }

    @Override
    @Transactional
    public void deleteTodo(long id) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todo = todoRepository.findByIdAndOwner(id, currentUser);
        if(todo.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepository.delete(todo.get());
    }

    private TodoResponse convertTodoResponse(Todo todo){
        return new TodoResponse(
             todo.getId(),
             todo.getTitle(),
             todo.getDescription(),
             todo.getPriority(),
             todo.isComplete()
        );
    }
}
