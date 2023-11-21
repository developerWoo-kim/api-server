package gw.apiserver.member.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "tb_mm_member")
public class Member {
    @Id
    @Column(name = "member_id")
    private String memberId;
    private String name;
    private String password;
    private String roles; // USER,ADMIN

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }

    @Builder
    public Member(String memberId, String name, String password) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
    }
}
