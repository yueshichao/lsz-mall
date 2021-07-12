package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
[{"subjectId":5,"subjectName":"夏季精选"}]
* */
@Data
@TableName("sms_home_recommend_subject")
public class HomeRecommendSubject {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long subjectId;

    private String subjectName;

    private Integer recommendStatus;

    private Integer sort;

}