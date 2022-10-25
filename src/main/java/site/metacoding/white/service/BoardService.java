package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto.UserDto;

// 트랜잭션 관리
// Dto 변환해서 컨트롤러에게 돌려줘야 함

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {
        User userPS = userRepository.findById(boardSaveReqDto.getSessionUser().getId());
        Board board = new Board();
        board.setTitle(boardSaveReqDto.getTitle());
        board.setContent(boardSaveReqDto.getContent());
        board.setUser(userPS);
        Board boardPS = boardRepository.save(board);

        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);
        boardSaveRespDto.setId(boardPS.getId());
        boardSaveRespDto.setTitle(boardPS.getTitle());
        boardSaveRespDto.setContent(boardPS.getContent());
        User user = boardPS.getUser();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        boardSaveRespDto.setUser(userDto);
        return boardSaveRespDto;
    }

    @Transactional(readOnly = true) // 세션 종료 안됨
    public Board findById(Long id) {
        Board boardPS = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료
        boardPS.getUser().getUsername(); // Lazy 로딩됨. (근데 Eager이면 이미 로딩되서 select 두번
        // 4. user select 됨?
        System.out.println("서비스단에서 지연로딩 함. 왜? 여기까지는 디비커넥션이 유지되니까");
        return boardPS;
    }

    @Transactional
    public void update(Long id, Board board) {
        Board boardPS = boardRepository.findById(id);
        boardPS.setTitle(board.getTitle());
        boardPS.setContent(board.getContent());

    } // 트랜잭션 종료시 => 더티체킹을 함

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
