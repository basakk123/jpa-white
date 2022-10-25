package site.metacoding.white.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.white.domain.Board;

public class BoardRespDto {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class BoardSaveRespDto {
        private Long id;
        private String title;
        private String content;
        private UserDto user;

        @Setter
        @Getter
        public static class UserDto {
            private Long id;
            private String username;
        }

        // 뭔가 메서드 만들어서 코드 리팩토링 하기

        public BoardSaveRespDto(Board board) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.user = user;
        }

    }
}
