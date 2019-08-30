package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.GoodsDescMapper;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.sellergoods.service.GoodsDescService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class GoodsDescServiceImpl extends BaseServiceImpl<TbGoodsDesc> implements GoodsDescService {

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Override
    public PageInfo<TbGoodsDesc> search(Integer pageNum, Integer pageSize, TbGoodsDesc goodsDesc) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbGoodsDesc.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        /**if (StringUtils.isNotBlank(goodsDesc.getProperty())) {
            criteria.andLike("property", "%" + goodsDesc.getProperty() + "%");
        }*/

        List<TbGoodsDesc> list = goodsDescMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

}
