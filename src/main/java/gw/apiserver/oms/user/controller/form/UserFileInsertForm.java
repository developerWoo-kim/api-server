package gw.apiserver.oms.user.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UserFileInsertForm {
    private String userSn;
    private MultipartFile leftImg;                  // 좌측 첨부파일 일련번호
    private MultipartFile rightImg;                 // 우측 첨부파일 일련번호
    private MultipartFile backImg;                  // 후방 첨부파일 일련번호
    private MultipartFile dashBoardImg;             // 계기판 첨부파일 일련번호
    private MultipartFile crcImg;                   // 차량 등록증 첨부파일 일련번호
    private MultipartFile idCardImg;                // 신분증 첨부파일 일련번호
}
