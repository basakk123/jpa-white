package site.metacoding.white.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;

// 트랜잭션 관리
// Dto 변환해서 컨트롤러에게 돌려줘야 함

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {
        // 핵심 로직
        Board boardPS = boardRepository.save(boardSaveReqDto.toEntity());

        // DTO 전환
        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);

        return boardSaveRespDto;
    }

    @Transactional(readOnly = true)
    public BoardDetailRespDto findById(Long id) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            BoardDetailRespDto boardDetailRespDto = new BoardDetailRespDto(boardOP.get());
            return boardDetailRespDto;
        } else {
            throw new RuntimeException("해당 " + id + "로 상세보기를 할 수 없습니다");
        }
    }

    @Transactional
    public void update(Long id, Board board) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isEmpty()) {
            throw new RuntimeException("해당 " + id + "로 수정을 할 수 없습니다");
        }
        boardOP.get().update(board.getTitle(), board.getContent());
    } // 트랜잭션 종료시 => 더티체킹을 함

    @Transactional(readOnly = true)
    public List<BoardAllRespDto> findAll() {
        List<Board> boardList = boardRepository.findAll();

        List<BoardAllRespDto> boardAllRespDtoList = new ArrayList<>();
        for (Board board : boardList) {
            boardAllRespDtoList.add(new BoardAllRespDto(board));
        }

        return boardAllRespDtoList;
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
