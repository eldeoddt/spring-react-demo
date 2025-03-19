package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity { //db랑 가장 가까운 클래스이다.
    //id 등 테이블 속성 내용들을 갖는다.
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
