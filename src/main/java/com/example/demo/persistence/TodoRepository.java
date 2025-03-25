package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // spring이 제공하는jpa 인터페이스 상속을 받는다.
    // jpa <테이블에 매핑될 엔티티 클래스, 이 엔티티의 기본 pk의 타입>
    // 자동으로 save()저장, delete(), findbyid() 메서드 등 자동으로 생성됨.
    // findbytitle, findbyuserid은 자동 제공이 아니다. todorepo가 자동으로 생성될 때 findbyid는 자동 생성해준다.

//    @Query(value = "select * from todo t where t.userId = ?1", nativeQuery = true)// 이 메서드가 실행되면 이 쿼리가 실행되도록 한다.
    // 물은표 1은 첫 번째 인자로 들어온게 userid와 일치하는 행을 모두 검색해라는 뜻이다.
    public List<TodoEntity> findByUserId(String userId); // 사용하려면 선언해줘야 한다.
    // findByUserId 라는 메소드 이름을 파싱하여 자동으로 쿼리를 작성한 후 실행한다. 선언만 하면 된다.

    // 선언하면 자동으로 쿼리 생성이 된다. 쉽게 db접근 가능하다.
    List<TodoEntity> findByIdAndTitle(String id, String title);
    List<TodoEntity> findByTitle(String title);

}
