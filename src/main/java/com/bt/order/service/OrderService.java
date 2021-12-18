package com.bt.order.service;
/*
 * Service 를 인터페이스로 만들어서 상속받는 것과
 * ServiceImpl로 컨테이너에 주입시키는 것의 차이
 * 
 *  인터페이스를 상속받는 것이 정석적인 방법.
 *  
 */

import java.util.List;
import java.util.Map;


public interface OrderService {
	// 발주상세번호 조회
	public Map<String, Object> getDetailOrder(int store_order_detail_no);

	public List<Map<String, Object>> getDetailOrderStatus(int store_order_detail_no);

	// 팝업창
	public List<Map<String, Object>> getProcessStaus(int store_order_detail_no);
}
