package shop.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
@Getter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 내용

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;      // 작성자

//    @OneToMany(mappedBy = "board", orphanRemoval = true)
//    private List<Like> likes;       // 좋아요
//    private Integer likeCnt;        // 좋아요 수
//
//    @OneToMany(mappedBy = "board", orphanRemoval = true)
//    private List<Comment> comments; // 댓글
//    private Integer commentCnt;     // 댓글 수
//
//    @OneToOne(fetch = FetchType.LAZY)
//    private UploadImage uploadImage;
//
//    public void update(BoardDto dto) {
//        this.title = dto.getTitle();
//        this.body = dto.getBody();
//    }
//
//    public void likeChange(Integer likeCnt) {
//        this.likeCnt = likeCnt;
//    }
//
//    public void commentChange(Integer commentCnt) {
//        this.commentCnt = commentCnt;
//    }
//
//    public void setUploadImage(UploadImage uploadImage) {
//        this.uploadImage = uploadImage;
//    }






}
