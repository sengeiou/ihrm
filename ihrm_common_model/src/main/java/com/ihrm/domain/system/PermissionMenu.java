package com.ihrm.domain.system;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/3/22 10:24
 * Description: 菜单权限实体类
 * 菜单资源
 */
@Entity
@Table(name = "pe_permission_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionMenu implements Serializable {
    private static final long serialVersionUID = -1002411490113957485L;

    /**
     * 主键
     */
    @Id
    private String id;

    //展示图标
    private String menuIcon;

    //排序号
    private String menuOrder;
}