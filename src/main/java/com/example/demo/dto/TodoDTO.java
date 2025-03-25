package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO { // 클라이언트한테 보낼 때 사용한다.
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity) { //todoentity 를 받아서 생성된다.
        // entity -> dto로 바꿔서 보내야 하기 때문에 생성자가 필요하다.
        // userId가 없다. ?
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    // todo dto를 todo entity로 변환한다.
    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
