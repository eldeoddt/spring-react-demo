package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository  repository;

    public String testService() {
        //Todo entity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        // Todo entity 저장
        repository.save(entity);

        // findByid로 리포지토리에서 검색한다.
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle(); // My first todo item이 반환된다.
    }

    // list todo entity 생성 메서드 : create
    // 검증, 리팩토링 메서드를 분리하는 것이 좋다.
    public List<TodoEntity> create(final TodoEntity entity) {

        // 검증 메서드 호출
        validate(entity);

        // entity 저장
        repository.save(entity);

        log.info("entity id: {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
        // Incompatible types. Found: 'com.example.demo.model.TodoEntity', required: 'java.util.List<com.example.demo.model.TodoEntity>'
        // -> repository의 findbyuserid의 타입을 todoentity가 아니라 list<todoentity>로 수정하여 해결함.

    }

    private void validate (TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user");
        }
    }

    // 검색 지원 메서드. 컨트롤러에서 호출한다.
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    // update todo 구현하기
    public List<TodoEntity> update(TodoEntity entity) {
        // 저장할 엔티티가 유효한지 확인. create todo에서 구현함.
        validate(entity);

        // 넘겨받은 id로 todoentity를 가져온다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> { //ispresent 로 if문 사용도 가능.
            // original이 존재하면 todo entity를 덮어씌운다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            //저장
            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    // delete 구현
    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error ("error deleting entity", entity.getId(),e);

            throw new RuntimeException("error deleting entity" + entity.getId());
        }

        return retrieve(entity.getUserId());
    }
}
