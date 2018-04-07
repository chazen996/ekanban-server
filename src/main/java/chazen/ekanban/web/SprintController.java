package chazen.ekanban.web;

import chazen.ekanban.entity.Project;
import chazen.ekanban.entity.Sprint;
import chazen.ekanban.entity.SysUser;
import chazen.ekanban.service.CardService;
import chazen.ekanban.service.ProjectService;
import chazen.ekanban.service.SprintService;
import chazen.ekanban.service.UserService;
import chazen.ekanban.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sprint")
@PreAuthorize("hasRole('USER')")
public class SprintController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "getSprint", method = RequestMethod.GET)
    public List<Sprint> getSprintUnderProject(int projectId, String username, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            /* 验证目标项目是否存在(防止脏数据的存在) */
            if(projectService.getProject(projectId)==null){
                return null;
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return null;
            }
            List<Sprint> sprintList = sprintService.getSprintUnderProject(projectId);
            for(Sprint sprint:sprintList){
                sprint.setCardList(sprintService.getCardUnderSprint(sprint.getSprintId()));
            }
            return sprintList;
            /* 实际业务代码end */
        } else {
            return null;
        }
    }


    @RequestMapping(value = "createSprint",method = RequestMethod.POST)
    public String createSprint(@RequestBody Sprint sprint,String username,HttpServletRequest request ){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = sprint.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            /* 验证目标项目是否存在(防止脏数据的存在) */
            Project projectTemp = projectService.getProject(projectId);
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者是否为项目的所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }
            return sprintService.createSprint(sprint)==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }
    @RequestMapping(value = "updateSprint",method = RequestMethod.POST)
    public String updateSprint(@RequestBody Sprint sprint,String username,HttpServletRequest request ){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = sprint.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            /* 验证目标项目是否存在(防止脏数据的存在) */
            Project projectTemp = projectService.getProject(projectId);
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者是否为项目的所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }

            return sprintService.updateSprint(sprint)==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @Transactional
    @RequestMapping(value = "openOrCloseSprint",method = RequestMethod.POST)
    public String openOrCloseSprint(@RequestBody Sprint sprint,String username,HttpServletRequest request ){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = sprint.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            /* 验证目标项目是否存在(防止脏数据的存在) */
            Project projectTemp = projectService.getProject(projectId);
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者是否为项目的所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }

            sprintService.updateSprint(sprint);
            /* 还要执行其他相关操作 */
            if("closed".equals(sprint.getSprintStatus())){
                /* 当前迭代下所有在看板上的卡片删除 */
                cardService.deleteCardUnderSprintButOnKanban(sprint.getSprintId());
                /* 其它卡片状态变为冻结 */
                cardService.changeCardStatusUnderSprint("freezd",sprint.getSprintId());
            }else if("open".equals(sprint.getSprintStatus())){
                /* 当前迭代下所有卡片状态变为pretodo */
                cardService.changeCardStatusUnderSprint("pretodo",sprint.getSprintId());
            }
            return "success";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @Transactional
    @RequestMapping(value = "deleteSprint",method = RequestMethod.POST)
    public String deleteSprint(@RequestBody Sprint sprint,String username,HttpServletRequest request ){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = sprint.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            /* 验证目标项目是否存在(防止脏数据的存在) */
            Project projectTemp = projectService.getProject(projectId);
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者是否为项目的所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }
            /* 删除目标迭代 */
            sprintService.deleteSprint(sprint.getSprintId());
            /* 删除迭代下任务 */
            cardService.deleteCardUnderSprint(sprint.getSprintId());

            return "success";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

}