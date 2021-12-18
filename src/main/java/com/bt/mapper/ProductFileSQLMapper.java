package com.bt.mapper;
import org.apache.ibatis.annotations.Param;

import com.bt.vo.ProductFileVo;

public interface ProductFileSQLMapper {
	

	public ProductFileVo selectByProductNo(int product_no);

	public void insert(@Param("product_no") int product_no, 
			@Param("product_file_link_path")String product_file_link_path, 
			@Param("product_file_real_path")String product_file_real_path);

	public void modify(@Param("product_no")int product_no, 
			@Param("product_file_link_path")String product_file_link_path,
			@Param("product_file_real_path")String product_file_real_path);

	public void delete(int product_no);

}
