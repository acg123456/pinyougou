package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.sellergoods.service.GoodsDescService;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/goodsDesc")
@RestController
public class GoodsDescController {

    @Reference
    private GoodsDescService goodsDescService;

    /**
     * 新增
     * @param goodsDesc 实体
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbGoodsDesc goodsDesc){
        try {
            goodsDescService.add(goodsDesc);

            return Result.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }

    /**
     * 根据主键查询
     * @param id 主键
     * @return 实体
     */
    @GetMapping("/findOne/{id}")
    public TbGoodsDesc findOne(@PathVariable Long id){
        return goodsDescService.findOne(id);
    }

    /**
     * 修改
     * @param goodsDesc 实体
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbGoodsDesc goodsDesc){
        try {
            goodsDescService.update(goodsDesc);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    /**
     * 根据主键数组批量删除
     * @param ids 主键数组
     * @return 实体
     */
    @GetMapping("/delete")
    public Result delete(Long[] ids){
        try {
            goodsDescService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 根据条件搜索
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @param goodsDesc 搜索条件
     * @return 分页信息
     */
    @PostMapping("/search")
    public PageInfo<TbGoodsDesc> search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestBody TbGoodsDesc goodsDesc) {
        return goodsDescService.search(pageNum, pageSize, goodsDesc);
    }

}
