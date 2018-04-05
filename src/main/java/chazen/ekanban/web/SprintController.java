package chazen.ekanban.web;

import chazen.ekanban.entity.Project;
import chazen.ekanban.entity.Sprint;
import chazen.ekanban.entity.SysUser;
import chazen.ekanban.service.ProjectService;
import chazen.ekanban.service.SprintService;
import chazen.ekanban.service.UserService;
import chazen.ekanban.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
            return sprintService.getSprintUnderProject(projectId);
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
            if(projectService.getProject(projectId)==null){
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
}


    /* 登陆状态下获取当前用户所参与的项目（包括自己创建的） */
//    @RequestMapping(value = "getProject", method = RequestMethod.GET)
//    public List<Project> getProjects(String username,HttpServletRequest request) {
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return null;
//        }
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            return projectService.getProjects(user.getId());
//            /* 实际业务代码end */
//        }else{
//            return null;
//        }
//    }
//
//    /* 登陆状态下创建项目（使用了事务操作，在两张表里先后插入两条数据） */
//    @Transactional
//    @RequestMapping(value = "createProject",method = RequestMethod.POST)
//    public String createProject(@RequestBody Project project,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return "failure";
//        }
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            projectService.saveProject(project);
//            projectService.saveUserProject(user.getId(),project.getProjectId());
//            return "success";
//            /* 实际业务代码end */
//        }else{
//            return "failure";
//        }
//    }
//
//    @Transactional
//    @RequestMapping(value = "deleteProject",method = RequestMethod.GET)
//    public String deleteProject(int projectId,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return "failure";
//        }
//        /* 用户身份验证成功 */
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 验证业务权限（是否有权限操作此项目 ） */
//            Project project = projectService.getProject(projectId);
//            if(project==null){
//                return "failure";
//            }
//            if(user.getId()!=project.getCreatedBy()){
//                return "failure";
//            }
//            projectService.deleteProject(projectId);
//            projectService.deleteUserProject(projectId);
//            return "success";
//            /* 实际业务代码end */
//        }else{
//            return "failure";
//        }
//    }
//
//    @Transactional
//    @RequestMapping(value = "updateProject",method = RequestMethod.POST)
//    public String updateProject(@RequestBody Project project,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return "failure";
//        }
//        /* 用户身份验证成功 */
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 验证业务权限（是否有权限操作此项目 ） */
//            Project projectTemp = projectService.getProject(project.getProjectId());
//            if(projectTemp==null){
//                return "failure";
//            }
//            if(user.getId()!=projectTemp.getCreatedBy()){
//                return "failure";
//            }
//            projectService.updateProject(project);
//            return "success";
//            /* 实际业务代码end */
//        }else{
//            return "failure";
//        }
//    }
//
//    @RequestMapping(value = "getAllUserUnderProject",method = RequestMethod.GET)
//    public List<SysUser> getAllUserUnderProject(int projectId,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return null;
//        }
//        /* 用户身份验证成功(验证token是否和调用者匹配) */
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 验证业务权限（是否有权限操作此项目 ） */
//            /* 验证目标项目是否存在 */
//            Project projectTemp = projectService.getProject(projectId);
//            if(projectTemp==null){
//                return null;
//            }
//            /* 确认调用者在当前项目内 */
//            if(projectService.confirmTargetUserProjectExits(projectId,user.getId())<=0){
//                return null;
//            }
//            return projectService.getAllUserUnderProject(projectId);
//
//            /* 实际业务代码end */
//        }else{
//            return null;
//        }
//    }
//
//    @RequestMapping(value = "removeUserFromProject",method = RequestMethod.GET)
//    public String removeUserFromProject(int projectId,int userId,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return "failure";
//        }
//        /* 用户身份验证成功(验证token是否和调用者匹配) */
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 验证业务权限（是否有权限操作此项目 ） */
//            /* 验证目标项目是否存在 */
//            Project projectTemp = projectService.getProject(projectId);
//            if(projectTemp==null){
//                return "failure";
//            }
//            /* 确认调用者在当前项目内 */
//            if(projectService.confirmTargetUserProjectExits(projectId,user.getId())<=0){
//                return "failure";
//            }
//            /* 防止目标项目仅剩待移除对象 */
//            if(projectService.tgetTargetProjectUserAmount(projectId)<=1){
//                return "only-one-user";
//            }
//
//            return projectService.removeUserFromProject(userId,projectId)>=1?"success":"failure";
//            /* 实际业务代码end */
//        }else{
//            return "failure";
//        }
//    }
//
//    @RequestMapping(value = "getUserLikeTheUsername",method = RequestMethod.GET)
//    public List<SysUser> getUserLikeTheUsername(String targetUsername,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return null;
//        }
//        /* 用户身份验证成功(验证token是否和调用者匹配) */
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 验证业务权限（是否有权限操作此项目 ） */
//            /* 验证目标项目是否存在 */
//           return projectService.getUserLikeTheUsername(targetUsername);
//            /* 实际业务代码end */
//        }else{
//            return null;
//        }
//    }
//
//    @RequestMapping(value = "addUserIntoProject",method = RequestMethod.GET)
//    public String addUserIntoProject(int projectId,int userId,String username,HttpServletRequest request){
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return "failure";
//        }
//        /* 用户身份验证成功(验证token是否和调用者匹配) */
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 验证业务权限（是否有权限操作此项目 ） */
//            /* 验证目标项目和用户是否存在 */
//            Project projectTemp = projectService.getProject(projectId);
//            if(projectTemp==null){
//                return "failure";
//            }
//            SysUser userTemp = userService.findUserById(userId);
//            if(userTemp==null){
//                return "failure";
//            }
//            /* 确认调用者在当前项目内 */
//            if(projectService.confirmTargetUserProjectExits(projectId,user.getId())<=0){
//                return "failure";
//            }
//            /* 防止重复插入 */
//            if(projectService.confirmTargetUserProjectExits(projectId,userId)>0){
//                return "failure";
//            }
//            return projectService.saveUserProject(userId,projectId)==1?"success":"failure";
//            /* 实际业务代码end */
//        }else{
//            return "failure";
//        }
//    }
//
//    @RequestMapping(value = "getTargetProject", method = RequestMethod.GET)
//    public Project getTargetProject(int projectId,String username,HttpServletRequest request) {
//        String token = request.getHeader(tokenHeader);
//        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
//        SysUser user = userService.findUserByUsername(username);
//        if (user == null) {
//            return null;
//        }
//        if(user.getUsername().equals(usernameTemp)){
//            /* 实际业务代码start */
//            /* 确认调用者在当前项目内 */
//            if(projectService.confirmTargetUserProjectExits(projectId,user.getId())<=0) {
//                return null;
//            }
//            return projectService.getTargetProject(projectId);
//            /* 实际业务代码end */
//        }else{
//            return null;
//        }
//    }
