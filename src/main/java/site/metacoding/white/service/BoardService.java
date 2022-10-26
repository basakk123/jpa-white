package site.metacoding.white.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
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

    public List<Board> findAll() {
        List<Board> boardList = boardRepository.findAll();
        // 1. List의 크기만큼 for문 돌리기

        // 2. Board -> dto로 옮겨야함
        // 3. Dto 를 List에 담기
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
