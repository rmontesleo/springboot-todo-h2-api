package com.demo.todoapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table( name ="todos")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @Column( name = "id" )
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column( name="created_by")
    private Long createdBy;

    @Column( name = "created_at")
    private LocalDateTime createdAt;

    @Column( name = "updated_at")
    private LocalDateTime updatedAt;

    private String title;

    private String description;

    @Column(name = "is_completed")
    private Boolean completed;


    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "created_by", insertable = false, updatable = false )
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Todo todo = (Todo) o;
        return todoId != null && Objects.equals(todoId, todo.todoId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
