package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class UserRespDto {

    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;

        // 응답의 DTO는 생성자로 처리한다 엔티티를 받아서 dto로
        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }

    @Setter
    @Getter
    public static class UpdatePasswordRespDto {
        private Long id;
        private String username;
        private String password;

        public UpdatePasswordRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
        }
    }

    @Setter
    @Getter
    public static class InfoRespDto {
        private Long id;
        private String username;

        public InfoRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }
}
