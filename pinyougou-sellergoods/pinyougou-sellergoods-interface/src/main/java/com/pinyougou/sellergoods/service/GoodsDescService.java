package com.pinyougou.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.service.BaseService;

import java.util.List;

public interface GoodsDescService extends BaseService<TbGoodsDesc> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param goodsDesc 搜索条件
     * @return 分页信息
     */
    PageInfo<TbGoodsDesc> search(Integer pageNum, Integer pageSize, TbGoodsDesc goodsDesc);

}
