package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cms_subject_product_relation")
public class SubjectProductRelation {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long subjectId;

    private Long productId;

}