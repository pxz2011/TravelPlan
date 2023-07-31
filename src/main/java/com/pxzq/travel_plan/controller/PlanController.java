package com.pxzq.travel_plan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pxzq.travel_plan.common.OauthContext;
import com.pxzq.travel_plan.common.R;
import com.pxzq.travel_plan.entity.Plan;
import com.pxzq.travel_plan.entity.User;
import com.pxzq.travel_plan.service.PlanService;
import com.pxzq.travel_plan.service.exception.UnknownException;
import com.pxzq.travel_plan.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    /**
     * 分页查询
     *
     * @param pageNum  当前页
     * @param pageSize 每页数量
     * @param cond     查询条件
     * @return 返回查询数据
     */
    @GetMapping("/page")
    public R<Page<Plan>> page(int pageNum, int pageSize, String cond) {
        String token = OauthContext.get();
        log.info("页码为:{},每页数据为:{},查询条件为:{}", pageNum, pageSize, cond);
        //获取token
        Page<Plan> page = new Page<>(pageNum, pageSize);
        try {
            User user = JwtUtil.parse(token);
            //分页查询
            LambdaQueryWrapper<Plan> planLambdaQueryWrapper = new LambdaQueryWrapper<>();
            Page<Plan> res = new Page<>();
            if (user != null) {
                planLambdaQueryWrapper.eq(Plan::getUserId, user.getId());
                planLambdaQueryWrapper.orderByDesc(Plan::getTime);
                //获取该用户所有数据
                planService.page(page, planLambdaQueryWrapper);
                //copy
                List<Plan> list;
                List<Plan> resList = new ArrayList<>();
                list = page.getRecords();
                for (Plan plan : list) {
                    //模糊查询
                    String remark = plan.getRemark().toLowerCase();
                    String time = plan.getTime().toLowerCase();
                    String place = plan.getPlace().toLowerCase();
                    String thing = plan.getThing().toLowerCase();
                    cond = cond.toLowerCase();
                    if (remark.contains(cond) ||
                            time.contains(cond) ||
                            place.contains(cond) ||
                            thing.contains(cond)
                    ) {
                        resList.add(plan);
                    }
                }
                res.setRecords(resList);
                return R.success(res, token);
            }
            return R.error("查询失败!");
        } catch (Exception ex) {
            throw new UnknownException(ex.getMessage());
        }
    }

    /**
     * 保存新计划
     *
     * @param plan 请求体
     * @return 返回是否添加成功
     */
    @PutMapping("/save")
    public R<String> save(@RequestBody Plan plan) {
        log.info("数据为:{}", plan);
        String token = OauthContext.get();
        try {
            User user = JwtUtil.parse(token);
            plan.setUserId(Objects.requireNonNull(user).getId());
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
        plan.setUpdateTime(new Date());
        if (plan.getRemark() == null || plan.getRemark().isEmpty()) {
            plan.setRemark("无");
        }
        planService.save(plan);
        return R.success("添加成功!", token);
    }

    /**
     * 删除一条信息(根据id删除)
     *
     * @param id 请求体
     * @return 返回是否删除成功
     */
    @DeleteMapping("/{id}")
    public R<String> del(@PathVariable Long id) {
        log.info("id为:{}", id);
        String token = OauthContext.get();
        boolean removeById = this.planService.removeById(id);
        if (removeById) {
            return R.success("删除成功!", token);
        } else {
            return R.error("删除失败");
        }

    }
}
