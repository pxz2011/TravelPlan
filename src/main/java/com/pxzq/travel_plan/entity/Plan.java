package com.pxzq.travel_plan.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Plan implements Serializable {
    @TableId
    private Long id; //id
    private String thing; //事情
    private String time; //时间
    @TableField(fill = FieldFill.INSERT)//自动插入
    private Date createTime;//创建时间
    @TableField(fill = FieldFill.UPDATE)//自动更新
    private Date updateTime;//更新时间
    private Long userId;//用户id(关联用户表)
    private String place;//地点
    private String remark;//备注(不写则插入无)
}
