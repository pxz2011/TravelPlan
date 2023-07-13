package com.pxzq.travel_plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pxzq.travel_plan.entity.Plan;
import com.pxzq.travel_plan.mapper.PlanMapper;
import com.pxzq.travel_plan.service.PlanService;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
}
