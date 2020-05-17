package com.czl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构Entity
 *
 * @author qmy
 * @since 2019/4/22 15:59
 */
@Data
public class TreeEntity implements Serializable {

    private String classId;

    private String className;

    private String level;

    private String parentId;


    private List<TreeEntity> children;

}
