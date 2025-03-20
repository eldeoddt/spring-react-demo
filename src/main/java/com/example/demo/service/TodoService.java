package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public String testService() {
        //Todo entity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        // Todo entity 저장
        repository.save(entity);

        // findByid로 리포지토리에서 검색한다.
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle(); // My first todo item이 반환된다.
    }
}
