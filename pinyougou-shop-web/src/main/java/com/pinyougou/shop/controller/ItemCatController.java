package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/itemCat")
@RestController
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;


    @GetMapping("/findByParentId")
    public List<TbItemCat> findByParentId(Long parentId){
        TbItemCat tbItemCat = new TbItemCat();
        tbItemCat.setParentId(parentId);
        List<TbItemCat> itemCatList = itemCatService.findByWhere(tbItemCat);
        return itemCatList;
    }

    @GetMapping("/findOne/{id}")
    public TbItemCat findOne(@PathVariable Long id){
        TbItemCat one = itemCatService.findOne(id);
        return one;
    }

    @GetMapping("/findAll")
    public List<TbItemCat> findAll(){
        return itemCatService.findAll();
    }
}
