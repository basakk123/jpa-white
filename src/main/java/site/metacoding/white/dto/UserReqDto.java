package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class UserReqDto {

    @Setter
    @Getter
    public static class JoinReqDto { // 로그인 전 인증관련 로직들은 전부 다 앞에 엔티티 안붙임. POST /user => /join
        private String username;
        private String password;

        public User toEntity() {
            return User.builder().username(username).password(password).build();
        }
    }

    @Setter
    @Getter
    public static class LoginReqDto {
        private String username;
        private String password;
    }
}
