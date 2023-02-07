package com.demo.todoapi.services;

import com.demo.todoapi.dto.PostTodo;
import com.demo.todoapi.dto.PutTodo;
import com.demo.todoapi.entities.Todo;
import com.demo.todoapi.repositories.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }

    public List<Todo> getUserTodos( final long userId ){
        return todoRepository.findByCreatedBy(userId);
    }

    public List<Todo> getUserTodosByStatus( long userId, boolean status ){
        return todoRepository.findByCreatedByAndCompleted( userId, status );
    }

    public Optional<Todo> getUserTodoById( long userId, long todoId ){
        return todoRepository.findByCreatedByAndTodoId(  userId, todoId);
    }

    public Todo saveTodo(long userId, PostTodo postTodo ){
        Todo todo = Todo.builder()
                        .createdBy( userId )
                        .createdAt(  LocalDateTime.now() )
                        .completed(  false)
                        .title(postTodo.title()   )
                        .description(postTodo.description() )
                        .build();

        return todoRepository.save( todo );
    }

    @Transactional
    public boolean deleteTodo( long userId, long todoId){
        try{
            long numberOfDeletedRecords = todoRepository.deleteByCreatedByAndTodoId(userId, todoId);
            log.debug("The number of deleted records are {}",  numberOfDeletedRecords );
            return numberOfDeletedRecords == 1;
        }catch ( EmptyResultDataAccessException ignored){
            return false;
        }
    }



    public Optional<Todo> updateTodo(long userId, long todoId, PutTodo todo ){
        return getUserTodoById( userId, todoId )
                .map( wantedTodo ->{
                    wantedTodo.setTitle( todo.title() );
                    wantedTodo.setDescription( todo.description() );
                    wantedTodo.setCompleted( todo.completed() );
                    wantedTodo.setUpdatedAt( LocalDateTime.now() );
                    return todoRepository.save(  wantedTodo );
                });
    }

}
