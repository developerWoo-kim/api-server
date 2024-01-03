package gw.apiserver.common.paging;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
public class PagingForm {
    private String lastId;
    private int count = 5;
    private int page;

    /**
     * pageable 생성 메서드
     * @return createPageable
     */
    public Pageable createPageable() {
        return PageRequest.of(this.page >= 1 ? this.page-1 : this.page, 5);
    }
}
