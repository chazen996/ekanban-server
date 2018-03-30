package chazen.ekanban.web;

import chazen.ekanban.entity.Project;
import chazen.ekanban.entity.SysUser;
import chazen.ekanban.service.ProjectService;
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
@RequestMapping("/project")
@PreAuthorize("hasRole('USER')")
public class ProjectController{

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    /* 登陆状态下获取当前用户所参与的项目（包括自己创建的） */
    @RequestMapping(value = "getProject", method = RequestMethod.GET)
    public List<Project> getProjects(String username,HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        if(user.getUsername().equals(usernameTemp)){
            /* 实际业务代码start */
            return projectService.getProjects(user.getId());
            /* 实际业务代码end */
        }else{
            return null;
        }
    }

    /* 登陆状态下创建项目（使用了事务操作，在两张表里先后插入两条数据） */
    @Transactional
    @RequestMapping(value = "createProject",method = RequestMethod.POST)
    public String createProject(@RequestBody Project project,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        if(user.getUsername().equals(usernameTemp)){
            /* 实际业务代码start */
            projectService.saveProject(project);
            projectService.saveUserProject(user.getId(),project.getProjectId());
            return "success";
            /* 实际业务代码end */
        }else{
            return "failure";
        }
    }
}
