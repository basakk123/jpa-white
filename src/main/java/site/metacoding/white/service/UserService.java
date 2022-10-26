package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserReqDto.UpdatePasswordReqDto;
import site.metacoding.white.dto.UserRespDto.InfoRespDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.dto.UserRespDto.UpdateRespDto;

// 트랜잭션 관리
// Dto 변환해서 컨트롤러에게 돌려줘야 함

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 응답의 DTO는 서비스에서 만든다
    @Transactional // 트랜잭션을 붙이지 않으면 영속화되어 있는 객체가 flush가 안됨
    public JoinRespDto save(JoinReqDto joinReqDto) {
        User userPS = userRepository.save(joinReqDto.toEntity());
        return new JoinRespDto(userPS);
        // 트랜잭션 종료
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginReqDto loginReqDto) {
        User userPS = userRepository.findByUsername(loginReqDto.getUsername());
        if (userPS.getPassword().equals(loginReqDto.getPassword())) {
            return new SessionUser(userPS);
        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }
    }

    public UpdateRespDto updatePassword(UpdatePasswordReqDto updatePasswordReqDto) {
        Long id = updatePasswordReqDto.getId();
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isPresent()) {
            User userPS = userOP.get();
            userPS.update(updatePasswordReqDto.getPassword());
            System.out.println("==========================");
            System.out.println(updatePasswordReqDto.getPassword());
            System.out.println(userPS.getPassword());
            return new UpdateRespDto(userPS);
        } else {
            throw new RuntimeException("해당 " + id + "로 수정을 할 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public InfoRespDto findById(Long id) {
        Optional<User> userOp = userRepository.findById(id);
        if (userOp.isPresent()) {
            InfoRespDto InfoRespDto = new InfoRespDto(userOp.get());
            return InfoRespDto;
        } else {
            throw new RuntimeException("해당 " + id + "로 상세보기를 할 수 없습니다");
        }
    }

}
