package gw.apiserver.common.paging;

import lombok.Getter;
import lombok.Setter;

/**
 *	Class Name	: SearchCondition.java
 *	Description	: 공통 검색 조건
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
public class SearchCondition {
    private String searchText;
}
