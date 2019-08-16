package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.Specification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public PageInfo<TbSpecification> search(Integer pageNum, Integer pageSize, TbSpecification specification) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbSpecification.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (StringUtils.isNotBlank(specification.getSpecName())) {
            criteria.andLike("specName", "%" + specification.getSpecName() + "%");
        }

        List<TbSpecification> list = specificationMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    /**
     * 根据id查询
     * @param id 规格id
     * @return 返回包装的规格(规格,规格选项列表)
     */
    @Override
    public Specification findById(Long id) {
        TbSpecification tbSpecification = findOne(id);
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        tbSpecificationOption.setSpecId(id);
        List<TbSpecificationOption> tbSpecificationOptionList = specificationOptionMapper.select(tbSpecificationOption);
        Specification specification = new Specification();
        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(tbSpecificationOptionList);
        return specification;
    }

    @Override
    public void add(Specification specification) {
        TbSpecification specification1 = specification.getSpecification();
        specificationMapper.insert(specification1);
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
            tbSpecificationOption.setSpecId(specification1.getId());
            specificationOptionMapper.insert(tbSpecificationOption);
        }
    }

    @Override
    public void update(Specification specification) {
        TbSpecification specification1 = specification.getSpecification();
        update(specification1);
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        tbSpecificationOption.setSpecId(specification1.getId());
        List<TbSpecificationOption> list = specificationOptionMapper.select(tbSpecificationOption);
        for (TbSpecificationOption specificationOption : list) {
            specificationOptionMapper.delete(specificationOption);
        }
        List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
        for (TbSpecificationOption specificationOption : specificationOptionList) {
            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public void removeByIds(Long[] ids) {
        deleteByIds(ids);
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        for (Long id : ids) {
            tbSpecificationOption.setSpecId(id);
            specificationOptionMapper.delete(tbSpecificationOption);
        }
    }
}
