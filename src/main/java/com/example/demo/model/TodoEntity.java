package com.example.demo.model;

import com.google.j2objc.annotations.GenerateObjectiveCGenerics;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@NoArgsConstructor // 인자없는 생성자 생성
@AllArgsConstructor
@Data // getter/setter 추가
@Entity // entity 클래스 지정
// 엔티티 클래스와 연결된 테이블 이름을 지정한다.
@Table(name="todo") // 기본으로는 엔티티 클래스의 이름이 테이블 명으로 사용된다.
public class TodoEntity { //db랑 가장 가까운 클래스이다.
    //id 등 테이블 속성 내용들을 갖는다.

    @Id //jakarta라이브러리. pk임을 명시한다.
    @GeneratedValue(generator = "system-uuid") // id.pk를 자동으로 생성하겠다. uuid 16진수 32자리로 사용하겠다.
    @GenericGenerator(name="system-uuid", strategy = "uuid") // system uuid 라는 이름의 generator를 정의한다.
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
