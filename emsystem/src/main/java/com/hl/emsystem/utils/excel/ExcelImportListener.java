package com.hl.emsystem.utils.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hl.emsystem.exception.EmSystemException;
import com.hl.emsystem.model.pojo.User;
import com.hl.emsystem.model.vo.UserExcelVo;
import com.hl.emsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ExcelImportListener  extends AnalysisEventListener<UserExcelVo> implements ExcelListener<UserExcelVo>{

    @Autowired
    private UserService userService;

    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    private  Boolean isUpdate;

    //需要添加无参构造才能注入
    public ExcelImportListener(){}
    public ExcelImportListener(Boolean isUpdate){
        this.isUpdate = isUpdate;
        this.userService = SpringUtil.getBean(UserService.class);
    }


    @Override
    public void invoke(UserExcelVo userVo, AnalysisContext context) {
        User user = this.userService.selectUserByUsername(userVo.getUsername());
//        List<Long> roleIds =this.userService.selectAllRoleIdByUserId(user.getUserId());
//        user.setRoleIds(roleIds.toArray(new Long[0]));
        try {
            // 验证是否存在这个用户,不存在就插入，存在就是更新
            if (ObjectUtil.isNull(user)) {
                user = BeanUtil.toBean(userVo, User.class);
                this.userService.addUserInfo(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getName()).append(" 导入成功");
            } else if (isUpdate) {
                Long userId = user.getUserId();
                Long peopleId = user.getPeopleId();
                user = BeanUtil.toBean(userVo, User.class);
                user.setUserId(userId);
                user.setPeopleId(peopleId);
                this.userService.updateUserInfo(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUsername()).append(" 更新成功");
            } else {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getUsername()).append(" 已存在");
            }
        } catch (Exception e) {
            failureNum++;
            String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
//            log.error(msg, e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public ExcelResult<UserExcelVo> getExcelResult() {
        return new ExcelResult<UserExcelVo>() {

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new EmSystemException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                }
                return successMsg.toString();
            }

            @Override
            public List<UserExcelVo> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return null;
            }
        };
    }
}
