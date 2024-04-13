package shop.shopping.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
@Getter
@Setter
@Builder
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column (unique = true)
    private String username;  // 로그인 아이디
    @Column (nullable = false)
    private String password;  // 비밀번호
    @Column (nullable = false)
    private String nickname;  // 닉네임
    @Column (nullable = false)
    private String gender;    // 성별

    private String authority = "ROLE_USER";

    private String refreshToken;

    public Member(String username, String password, String nickname, String gender ) {
        this.username  = username;
        this.password  = password;
        this.nickname  = nickname;
        this.gender    = gender;
    }

    public void updateRefreshToken(String refreshToken){

        this.refreshToken = refreshToken;
    }

//    private Integer receivedLikeCnt; // 유저가 받은 좋아요 개수 (본인 제외)
//
//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private List<Board> boards;     // 작성글
//
//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private List<Like> likes;       // 유저가 누른 좋아요
//
//    @OneToMany(mappedBy = "member", orphanRemoval = true)
//    private List<Comment> comments; // 댓글

}
