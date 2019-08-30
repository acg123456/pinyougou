package com.pinyougou.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.service.BaseService;

import java.util.List;

public interface ItemService extends BaseService<TbItem> {
    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param item 搜索条件
     * @return 分页信息
     */
    PageInfo<TbItem> search(Integer pageNum, Integer pageSize, TbItem item);

}
