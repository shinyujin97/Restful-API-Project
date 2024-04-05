package shop.shopping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.shopping.entity.Board;
import shop.shopping.repository.BoardRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public String write(String title , String content) {

        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();
        boardRepository.save(board);
        return "작성 완료";
    }
}
