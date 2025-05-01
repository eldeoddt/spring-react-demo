package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    // service 객체 자동 주입.
    @Autowired
    private TodoService service;

    // todo dto 입력 받기
    @PostMapping
    // @AuthenticationPrincipal : 첫번째 인자값 = userId값이 자동으로 전달된다.
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId,
                                        @RequestBody TodoDTO dto) {
        try {

            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setId(null);

            // Authentication Bearer token - @AuthenticationPrincipal로 받은 userId를 지정한다.
            entity.setUserId(userId);

            // enitity 생성
            List<TodoEntity> entities = service.create(entity);

            // entity list -> todo dto 로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // dto list로 responseDTO 초기화. 추가된후 todo 리스트를 반환한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 예외 처리 dto 대신 error메시지 반환
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // get 요청 시 todo dto list가 반환된다.
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        // service의 retrieve 로 todo list 가져온다.
        List<TodoEntity> entities = service.retrieve(userId);

        // 자바 스트림을 이용하여 엔티티 리스트롤 todo dto list로 변환.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // responseDTO에 todo dto list를 담는다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // responseDTO를 반환한다.
        return ResponseEntity.ok().body(response);
    }

    @PutMapping // 수정 시 사용
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {
        String temporaryUserId = "temp-user";

        // dto -> entity 로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);

        // id를 설정
        entity.setUserId(userId);

        // service.update로 엔티티 업데이트.
        List<TodoEntity> entities = service.update(entity);

        //자바 스트림을 이용하여 리턴된 엔티티 리스트를 todo dto 리스트로 변환.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // responsedto에 담음.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {
        try {

            // dto -> entity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // token에서 추출한 id를 설정
            entity.setUserId(userId);

            // service.delete로 엔티티 삭제하기
            List<TodoEntity> entities = service.delete(entity);

            //자바 스트림을 이용하여 리턴된 엔티티 리스트를 todo dto 리스트로 변환.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // responsedto에 담음.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService(); // test service 사용.
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response); // "Test Service"라고 응답이 나옴.
    }
}
