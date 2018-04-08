package chazen.ekanban.web;

import chazen.ekanban.entity.*;
import chazen.ekanban.service.*;
import chazen.ekanban.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/kanban")
@PreAuthorize("hasRole('USER')")
public class KanbanController {

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

    @Autowired
    private KanbanService kanbanService;

    @Autowired
    private KanbanColumnService kanbanColumnService;

    @RequestMapping(value = "getKanbans",method = RequestMethod.GET)
    public List<Kanban> getKanbans(int projectId,String username,HttpServletRequest request){
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
            return kanbanService.getKanbanUnderProject(projectId);
            /* 实际业务代码end */
        } else {
            return null;
        }
    }

    @RequestMapping(value = "createKanban",method = RequestMethod.POST)
    public String createKanban(@RequestBody Kanban kanban,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }

        int projectId = kanban.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            /* 验证目标项目是否存在(防止脏数据的存在) */
            Project projectTemp = projectService.getProject(projectId);
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者为项目所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }
            return kanbanService.saveKanban(kanban)==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "deleteKanban",method = RequestMethod.POST)
    public String deleteKanban(@RequestBody Kanban kanban,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }

        int projectId = kanban.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            Project projectTemp = projectService.getProject(projectId);
            /* 验证目标项目是否存在(防止脏数据的存在) */
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者为项目所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }
            return kanbanService.deleteKanban(kanban.getKanbanId())==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "updateKanban",method = RequestMethod.POST)
    public String updateKanban(@RequestBody Kanban kanban,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }

        int projectId = kanban.getProjectId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证业务权限（是否有权限操作此项目 ） */
            Project projectTemp = projectService.getProject(projectId);
            /* 验证目标项目是否存在(防止脏数据的存在) */
            if(projectTemp==null){
                return "failure";
            }
            /* 验证调用者为项目所有人 */
            if(projectTemp.getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(projectId, user.getId()) <= 0) {
                return "failure";
            }
            return kanbanService.updateKanban(kanban)==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "getColumns",method = RequestMethod.GET)
    public List<KanbanColumn> getColumn(int kanbanId, String username, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证当前看板是否存在 */
            Kanban kanbanTemp = kanbanService.getKanbanById(kanbanId);
            if(kanbanTemp==null){
                return null;
            }

            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(kanbanTemp.getProjectId(), user.getId()) <= 0) {
                return null;
            }

            List<KanbanColumn> kanbanColumns = kanbanColumnService.getColumns(kanbanId);
            List<KanbanColumn> result = new ArrayList<KanbanColumn>();
            Map<Integer,KanbanColumn> columnMap = new HashMap<Integer, KanbanColumn>();
            for(KanbanColumn kanbanColumn : kanbanColumns){
                if(kanbanColumn.getParentId().equals("0")){
                    result.add(kanbanColumn);
                }
                kanbanColumn.setSubColumn(new ArrayList<KanbanColumn>());
                columnMap.put(kanbanColumn.getColumnId(), kanbanColumn);
            }
            Collections.sort(result);
            for(KanbanColumn kanbanColumn : kanbanColumns){
                String[] parentId = kanbanColumn.getParentId().split(",");
                if(parentId.length>1){
                    List<KanbanColumn> subColumn = columnMap.get(Integer.parseInt(parentId[parentId.length-1])).getSubColumn();
                    subColumn.add(kanbanColumn);
                    Collections.sort(subColumn);
                }
            }

            return result;
            /* 实际业务代码end */
        } else {
            return null;
        }
    }

    @RequestMapping(value = "getProjectByKanbanId",method = RequestMethod.GET)
    public Project getProjectByKanbanId(int kanbanId, String username, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证当前看板是否存在 */
            Kanban kanbanTemp = kanbanService.getKanbanById(kanbanId);
            if(kanbanTemp==null){
                return null;
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(kanbanTemp.getProjectId(), user.getId()) <= 0) {
                return null;
            }

            return projectService.getProjectByKanbanId(kanbanId);
            /* 实际业务代码end */
        } else {
            return null;
        }
    }

    @RequestMapping(value = "getKanban",method = RequestMethod.GET)
    public Kanban getKanban(int kanbanId,String username,HttpServletRequest request){
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
            Kanban kanbanTemp = kanbanService.getKanbanById(kanbanId);
            if(kanbanTemp==null){
                return null;
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(kanbanTemp.getProjectId(), user.getId()) <= 0) {
                return null;
            }
            return kanbanTemp;
            /* 实际业务代码end */
        } else {
            return null;
        }
    }
}