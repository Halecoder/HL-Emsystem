package com.hl.emsystem.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hl.emsystem.utils.excel.convert.ExcelLongConvert;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserExcelVo implements Serializable {
    @ExcelProperty(value = "用户名称")
    private String name;

    @ExcelProperty(value = "用户昵称")
    private String username;

    @ExcelProperty(value = "手机号码")
    private String phone;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "用户密码")
//    @ExcelIgnore
    private String password;

    @ExcelProperty(value = "角色(1-毕业生)",converter= ExcelLongConvert.class)
    @JsonProperty(value = "roleIds")
    private Long[] roleIds;

    @ExcelProperty(value = "用户备注")
    private String departmentName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
