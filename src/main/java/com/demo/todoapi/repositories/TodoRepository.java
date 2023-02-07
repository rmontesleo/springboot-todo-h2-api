package com.demo.todoapi.repositories;

import com.demo.todoapi.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * 
 * http://localhost:8080/api/todoapp/h2-console/login.jsp
 */
public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByCreatedBy( long userId );

    List<Todo> findByCreatedByAndCompleted( long userId, boolean isCompletd);

    Optional<Todo> findByCreatedByAndTodoId(long userId, long todoId );

    long deleteByCreatedByAndTodoId( long createdBy,  long todoId);

}
