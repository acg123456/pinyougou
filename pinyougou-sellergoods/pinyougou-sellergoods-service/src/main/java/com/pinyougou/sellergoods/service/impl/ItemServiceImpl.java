package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.sellergoods.service.ItemService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemServiceImpl extends BaseServiceImpl<TbItem> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<TbItem> search(Integer pageNum, Integer pageSize, TbItem item) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbItem.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        /**if (StringUtils.isNotBlank(item.getProperty())) {
            criteria.andLike("property", "%" + item.getProperty() + "%");
        }*/

        List<TbItem> list = itemMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

}
