package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> { // 자바 Generic을 이용한다. 제너릭 t타입.
    // 타입 t를 일반적으로 표현한 것. 어떤 원소 타입의 리스트도 반환가능하다.
    private String error;
    private List<T> data;
    //todoresponse도 반환 가능.
}
