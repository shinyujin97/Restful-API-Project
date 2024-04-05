package shop.shopping.controller;

/**
    게시판
 */

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.shopping.dto.BoardDto;
import shop.shopping.dto.MemberJoinRequestDto;
import shop.shopping.entity.Board;
import shop.shopping.repository.BoardRepository;
import shop.shopping.service.BoardService;

@RestController
@RequestMapping(value = "api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/main")
    public String main(){

        return "게시판 메인페이지 조회";
    }

    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody BoardDto boardDto){
        boardService.write(boardDto.getTitle(),boardDto.getContent());
        return ResponseEntity.ok().body("작성완료");
    }



}
