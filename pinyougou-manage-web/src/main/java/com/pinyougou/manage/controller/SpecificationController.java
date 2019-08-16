package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.vo.Result;
import com.pinyougou.vo.Specification;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/specification")
@RestController
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    /**
     * 查找规格列表
     * @return 下拉框规格列表
     */
    @GetMapping("/selectOptionList.do")
    public List<Map<String,Object>> selectOptionList(){
        List<TbSpecification> allSpecification = specificationService.findAll();
        HashMap<String, Object> map = null;
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (TbSpecification tbSpecification : allSpecification) {
            map = new HashMap<>();
            map.put("id",tbSpecification.getId());
            map.put("text",tbSpecification.getSpecName());
            list.add(map);
        }
        return list;
    }

    /**
     * 新增
     * @param specification 实体
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Specification specification){
        try {
            specificationService.add(specification);

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
    @GetMapping("/findOne")
    public Specification findOne(Long id){
        return specificationService.findById(id);
    }

    /**
     * 修改
     * @param specification 实体
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
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
            specificationService.removeByIds(ids);
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
     * @param specification 搜索条件
     * @return 分页信息
     */
    @PostMapping("/search")
    public PageInfo<TbSpecification> search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestBody TbSpecification specification) {
        return specificationService.search(pageNum, pageSize, specification);
    }

}
