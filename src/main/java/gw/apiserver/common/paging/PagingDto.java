package gw.apiserver.common.paging;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 *	Class Name	: PagingDto.java
 *	Description	: 공통 페이징 DTO
 *	Modification Information
 *	수정일		수정자		수정 내용
 *	-----------	----------	---------------------------
 *	2023.08.25	gwkim		최초 생성
 *
 *
 *	@author  gwkim
 *	@since  2023.08.25
 *	@version 1.0
 */
@Getter @Setter
public class PagingDto<T> {
    /** 1. 현재 페이지 **/
    private int page = 1;
    /** 2. 다음 데이터 있는지 **/
    private boolean hasNextData = true;

    List<T> data = new ArrayList<>();

    public PagingDto(List<T> data, long totalListCnt, int page, int pageSize) {
        setPage(++page);
        setData(data);

        int totalPageCnt = (int) Math.ceil(totalListCnt * 1.0 / pageSize);
        if(totalPageCnt <= page) {
            hasNextData = false;
        }
    }
}
