package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.GoodsDescService;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.sellergoods.service.ItemService;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/goods")
@RestController
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @Reference
    private GoodsDescService goodsDescService;

    @Reference
    private ItemService itemService;

    @PostMapping("/add")
    public Result add(@RequestBody Goods goods){
        try {
            //设置卖家seller_id
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            goods.getGoods().setSellerId(name);
            //设置为未审核状态
            goods.getGoods().setAuditStatus("0");
            goodsService.addGoods(goods);
            return Result.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("添加失败");
        }
    }

    @PostMapping("/search")
    public PageInfo<TbGoods> search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    @RequestBody TbGoods tbGoods) {
        return goodsService.search(pageNum, pageSize,tbGoods);
    }

    @GetMapping("/findOne/{id}")
    public Goods findOne(@PathVariable Long id){
        Goods goods = new Goods();
        TbGoods tbGoods = goodsService.findOne(id);
        goods.setGoods(tbGoods);

        TbGoodsDesc tbGoodsDesc = goodsDescService.findOne(id);
        goods.setGoodsDesc(tbGoodsDesc);

        TbItem tbItem = new TbItem();
        tbItem.setGoodsId(id);
        List<TbItem> tbItemList = itemService.findByWhere(tbItem);
        goods.setItemList(tbItemList);
        return goods;
    }
}
