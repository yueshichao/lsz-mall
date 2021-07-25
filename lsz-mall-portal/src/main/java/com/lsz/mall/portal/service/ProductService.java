package com.lsz.mall.portal.service;

import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.entity.ProductDetailVO;
import com.lsz.mall.portal.entity.ProductSearchGoodsVO;

public interface ProductService {

    CommonPage<ProductSearchGoodsVO> search(String keyword, Long goodsCategoryId, String orderBy, Integer pageNumber, Integer pageSize);

    ProductDetailVO getDetail(Long goodsId);

}
