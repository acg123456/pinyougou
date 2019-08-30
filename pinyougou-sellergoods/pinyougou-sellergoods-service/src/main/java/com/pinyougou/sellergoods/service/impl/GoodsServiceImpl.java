package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.GoodsDescService;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.sellergoods.service.ItemService;
import com.pinyougou.sellergoods.service.SpecificationOptionService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.Goods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.*;

@Transactional
@Service
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<TbGoods> search(Integer pageNum, Integer pageSize, TbGoods goods) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);
        //创建查询对象
        Example example = new Example(TbGoods.class);

        //创建查询条件对象
        Example.Criteria criteria = example.createCriteria();

        //模糊查询
        if (StringUtils.isNotBlank(goods.getGoodsName())) {
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }

        List<TbGoods> list = goodsMapper.selectByExample(example);
        return new PageInfo<>(list);
    }


    /**
     * 商家添加商品
     * @param goods
     */
    public void addGoods(Goods goods){
        TbGoods tbGoods = goods.getGoods();
        //添加商品spu信息
        add(tbGoods);
        TbGoodsDesc tbGoodsDesc = goods.getGoodsDesc();
        tbGoodsDesc.setGoodsId(goods.getGoods().getId());
        //判断是否启用规格
        if (tbGoods.getIsEnableSpec().equals("1")){
            TbTypeTemplate tbTypeTemplate = typeTemplateMapper.selectByPrimaryKey(tbGoods.getTypeTemplateId());
            //根据goods的模板id查询出typetemplate规格字段
            String specIds = tbTypeTemplate.getSpecIds();
            JSONArray jsonArray = JSONArray.parseArray(specIds);

            ArrayList<Map<String, Object>> list = new ArrayList<>();

            HashMap<String, Object> map = new HashMap<>();

            for (int i =0;i<jsonArray.size();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Long id = jsonObject.getLong("id");

                //根据id查询选项列表
                TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
                tbSpecificationOption.setSpecId(id);
                List<TbSpecificationOption> optionList = specificationOptionMapper.select(tbSpecificationOption);

                for (TbSpecificationOption specificationOption : optionList) {
                    map.put("attributeValue",specificationOption.getOptionName());
                }
                Object text = jsonObject.get("text");
                map.put("attributeName",text);
                list.add(map);
            }
            String jsonString = JSONObject.toJSONString(list);
            System.out.println(jsonString);
            tbGoodsDesc.setSpecificationItems(jsonString);
            //根据模板id查询规格选项
        }
        goodsDescMapper.insertSelective(tbGoodsDesc);
        //添加商品SKU
        saveTbItems(goods);
    }

    /**
     * 保存sku
     * @param goods
     */
    public void saveTbItems(Goods goods){

        String title = goods.getGoods().getGoodsName();

        Long category3Id = goods.getGoods().getCategory3Id();
        String itemCategoryName = itemCatMapper.selectByPrimaryKey(category3Id).getName();

        Long brandId = goods.getGoods().getBrandId();
        String itemBrand = brandMapper.selectByPrimaryKey(brandId).getName();

        List<TbItem> itemList = goods.getItemList();

        if (itemList != null && itemList.size()>0){
            for (TbItem tbItem : itemList) {
                String spec = tbItem.getSpec();
                Map<String,Object> map = JSON.parseObject(spec, Map.class);
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    title += entry.getValue();
                }

                tbItem.setTitle(title);
                tbItem.setCreateTime(new Date());
                tbItem.setGoodsId(goods.getGoods().getId());
                tbItem.setSellerId(goods.getGoods().getSellerId());
                tbItem.setCategory(itemCategoryName);
                tbItem.setBrand(itemBrand);
                tbItem.setCategoryid(category3Id);
                tbItem.setPrice(goods.getGoods().getPrice());
                tbItem.setUpdateTime(new Date());
            }
        }
        itemMapper.insertList(itemList);
    }
}
