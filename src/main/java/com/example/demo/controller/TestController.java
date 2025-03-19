package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // 이 클래스가 컨트롤러임을 명시.
@RequestMapping("test") // test로 요청 시 여기로
public class TestController {

    @GetMapping //get요처이 왔을때 얘가 동작함.
    public String testController() {
        return "Hello World"; // hello world를 return한다.
    }

    @GetMapping("/testGetMapping") // test/testGetMapping
    public String testControllerWithPath() {
        return "Hello World! testGetMapping"; // hello world를 return한다.
    }

    @GetMapping("/{id}") //test/123 하면 123이 브라우저에 나온다.문자열ㅇ을 넣으면 오류난다.
    public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
        return "Hello World! " + id;
    }

    @GetMapping("/testRequestParam") // /test/testRequestParam?id=123
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello World! id = " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World! " + testRequestBodyDTO.getId() + " Message: " + testRequestBodyDTO.getMessage();
    }

    // pathVariable, RequestParam, RequestBody 기억.

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("hello world! im a responseDTO");
        list.add("hello jisu");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return responseDTO;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello world im a responseEntity");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.badRequest().body(response);
    }
}
