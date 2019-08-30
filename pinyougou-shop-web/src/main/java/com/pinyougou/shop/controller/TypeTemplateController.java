package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.SpecificationOptionService;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.vo.Result;
import com.pinyougou.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/typeTemplate")
@RestController
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;

    @Reference
    private SpecificationOptionService specificationOptionService;

    @Reference
    private SpecificationService specificationService;

    @GetMapping("/findSpecList")
    public List<Map<String,Object>> findSpecList(Long id){
        TbTypeTemplate typeTemplate = typeTemplateService.findOne(id);
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        List<Map> mapList = JSONObject.parseArray(typeTemplate.getSpecIds(), Map.class);
        ArrayList<Map<String,Object>> specList = new ArrayList<>();
        for (Map map : mapList) {
            //得到规格id
            Long id1 = Long.parseLong(map.get("id")+"");
            tbSpecificationOption.setSpecId(id1);
            //根据id查找规格选项
            List<TbSpecificationOption> optionList = specificationOptionService.findByWhere(tbSpecificationOption);

            Specification specification = specificationService.findById(id1);
            HashMap<String, Object> specMap = new HashMap<>();
            specMap.put("id", id1);
            specMap.put("specName",specification.getSpecification().getSpecName());
            specMap.put("options",optionList);
            specList.add(specMap);
        }

        return specList;
    }

    /**
     * 新增
     * @param typeTemplate 实体
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbTypeTemplate typeTemplate){
        try {
            typeTemplateService.add(typeTemplate);

            return Result.ok("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增失败");
    }

    /**
     * 根据主键查询
     * @param typeid 主键
     * @return 实体
     */
    @GetMapping("/findOne/{typeid}")
    public TbTypeTemplate findOne(@PathVariable Long typeid){
        return typeTemplateService.findOne(typeid);
    }

    /**
     * 修改
     * @param typeTemplate 实体
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbTypeTemplate typeTemplate){
        try {
            typeTemplateService.update(typeTemplate);
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
            typeTemplateService.deleteByIds(ids);
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
     * @param typeTemplate 搜索条件
     * @return 分页信息
     */
    @PostMapping("/search")
    public PageInfo<TbTypeTemplate> search(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestBody TbTypeTemplate typeTemplate) {
        return typeTemplateService.search(pageNum, pageSize, typeTemplate);
    }

}
