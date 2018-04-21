package chazen.ekanban.web;

import chazen.ekanban.entity.*;
import chazen.ekanban.service.*;
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

    @Transactional
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

            /* 级联删除泳道，列，卡片 */
            kanbanColumnService.deleteColumnUnderKanban(kanban.getKanbanId());
            kanbanColumnService.deleteSwimlaneUnderKanban(kanban.getKanbanId());
            cardService.deleteCardUnderKanban(kanban.getKanbanId());
            kanbanService.deleteKanban(kanban.getKanbanId());
            return "success";
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

    @RequestMapping(value = "getKanbanData",method = RequestMethod.GET)
    public KanbanDataResponse getColumn(int kanbanId, String username, HttpServletRequest request){
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

            /* 设置columns */
            List<KanbanColumn> kanbanColumns = kanbanColumnService.getColumns(kanbanId);
            List<KanbanColumn> columns = new ArrayList<KanbanColumn>();
            Map<String,KanbanColumn> columnMap = new HashMap<String, KanbanColumn>();
            for(KanbanColumn kanbanColumn : kanbanColumns){
                if(kanbanColumn.getParentId().equals("0")){
                    columns.add(kanbanColumn);
                }
                kanbanColumn.setSubColumn(new ArrayList<KanbanColumn>());
                columnMap.put(kanbanColumn.getColumnId(), kanbanColumn);
            }
            Collections.sort(columns);
            for(KanbanColumn kanbanColumn : kanbanColumns){
                String[] parentId = kanbanColumn.getParentId().split(",");
                if(parentId.length>1){
                    List<KanbanColumn> subColumn = columnMap.get(parentId[parentId.length-1]).getSubColumn();
                    subColumn.add(kanbanColumn);
                    Collections.sort(subColumn);
                }
            }
            /* 设置swimlanes */
            List<Swimlane> swimlanes = kanbanColumnService.getSwimlanes(kanbanId);


            KanbanDataResponse kanbanDataResponse = new KanbanDataResponse();
            kanbanDataResponse.setColumns(columns);
            kanbanDataResponse.setSwimlanes(swimlanes);

            return kanbanDataResponse;
            /* 实际业务代码end */
        } else {
            return null;
        }
    }

    @Transactional
    @RequestMapping(value = "saveKanbanData",method = RequestMethod.POST)
    public String getColumn(@RequestBody KanbanDataRequest kanbanDataRequest, String username, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int kanbanId = kanbanDataRequest.getKanbanId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证当前看板是否存在 */
            Kanban kanbanTemp = kanbanService.getKanbanById(kanbanId);
            if(kanbanTemp==null){
                return "failure";
            }
            /* 确认调用者为项目所有者 */
            if(projectService.getProjectByKanbanId(kanbanId).getCreatedBy()!=user.getId()){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(kanbanTemp.getProjectId(), user.getId()) <= 0) {
                return "failure";
            }

            List<KanbanColumn> columns = kanbanDataRequest.getColumns();
            List<Swimlane> swimlanes = kanbanDataRequest.getSwimlanes();
            int kanbanHeight = kanbanDataRequest.getKanbanHeight();
            String[] toBeDeletedColumn = kanbanDataRequest.getToBeDeletedColumn();
            String[] toBeDeletedSwimlane = kanbanDataRequest.getToBeDeletedSwimlane();

            kanbanService.updateKanbanHeight(kanbanId,kanbanHeight);

            /* 删除泳道和列 */
            for(String columnId:toBeDeletedColumn){
                kanbanColumnService.deleteColumn(columnId);
            }
            for(String swimlaneId:toBeDeletedSwimlane){
                kanbanColumnService.deleteSwimlane(swimlaneId);
            }
            /* 保存泳道 */
            for(Swimlane swimlane:swimlanes){
                Swimlane swimlaneTemp = kanbanColumnService.getSwimlane(swimlane.getSwimlaneId());
                if(swimlaneTemp==null){
                    kanbanColumnService.saveSwimlane(swimlane);
                }else{
                    kanbanColumnService.updateSwimlane(swimlane);
                }
            }
            /* 保存列 */
            for(KanbanColumn kanbanColumn:columns){
                KanbanColumn kanbanColumnTemp = kanbanColumnService.getColumn(kanbanColumn.getColumnId());
                if(kanbanColumnTemp==null){
                    kanbanColumnService.saveColumn(kanbanColumn);
                }else{
                    kanbanColumnService.updateColumn(kanbanColumn);
                }
            }
            return "success";
            /* 实际业务代码end */
        } else {
            return "success";
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

    @RequestMapping(value = "getOpenedSprints",method = RequestMethod.GET)
    public List<Sprint> getOpenedSprints(int kanbanId, String username, HttpServletRequest request){
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
            Project projectTemp = projectService.getProjectByKanbanId(kanbanId);
            if(projectTemp==null){
                return null;
            }
            List<Sprint> sprints = sprintService.getTargetStatusSprints(projectTemp.getProjectId(),"open");
            for(Sprint sprint:sprints){
                sprint.setCardList(sprintService.getCardUnderSprintButWithoutKanbanId(sprint.getSprintId()));
                for(Card card:sprint.getCardList()){
                    int assignedPersonId = card.getAssignedPersonId();
                    if(assignedPersonId!=0){
                        card.setAssignedPerson(userService.findUserById(assignedPersonId));
                    }
                }
            }
            return sprints;
            /* 实际业务代码end */
        } else {
            return null;
        }
    }

    @RequestMapping(value = "getCardUnderKanban",method = RequestMethod.GET)
    public List<Card> getCardUnderKanban(int kanbanId, String username, HttpServletRequest request){
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
            Project projectTemp = projectService.getProjectByKanbanId(kanbanId);
            if(projectTemp==null){
                return null;
            }
            List<Card> cardList = cardService.getCardUnderKanban(kanbanId);
            for(Card card:cardList){
                int assignedPersonId = card.getAssignedPersonId();
                if(assignedPersonId!=0){
                    card.setAssignedPerson(userService.findUserById(assignedPersonId));
                }
                card.setKanban(kanbanService.getKanbanById(kanbanId));
            }

            return cardList;
            /* 实际业务代码end */
        } else {
            return null;
        }
    }

    @RequestMapping(value = "getAllUserUnderProjectByKanbanId",method = RequestMethod.GET)
    public List<SysUser> getAllUserUnderProjectByKanbanId(int kanbanId, String username, HttpServletRequest request){
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
            Project projectTemp = projectService.getProjectByKanbanId(kanbanId);
            if(projectTemp==null){
                return null;
            }
            return projectService.getAllUserUnderProject(projectTemp.getProjectId());
            /* 实际业务代码end */
        } else {
            return null;
        }
    }
    @Transactional
    @RequestMapping(value = "deleteUnusualCard",method = RequestMethod.POST)
    public String deleteUnusualCard(@RequestBody TobeDeletedCardList tobeDeletedCardList, String username, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int kanbanId = tobeDeletedCardList.getKanbanId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证当前看板是否存在 */
            Kanban kanbanTemp = kanbanService.getKanbanById(kanbanId);
            if(kanbanTemp==null){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(kanbanTemp.getProjectId(), user.getId()) <= 0) {
                return "failure";
            }
            Integer[] cardIdList = tobeDeletedCardList.getCardIdList();
            /* 检查待删除任务是否在当前看板上 */
            for(Integer cardId:cardIdList){
                if(cardService.getCardById(cardId).getKanbanId()!=kanbanId){
                    return "failure";
                }
            }
            for(Integer cardId:cardIdList){
                cardService.deleteCardByCardId(cardId);
            }

            return "success";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "moveCard",method = RequestMethod.POST)
    public String moveCard(@RequestBody Card card, String username, HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int kanbanId = card.getKanbanId();
        /* 用户身份验证成功(验证token是否和调用者匹配) */
        if (user.getUsername().equals(usernameTemp)) {
            /* 实际业务代码start */
            /* 验证当前看板是否存在 */
            Kanban kanbanTemp = kanbanService.getKanbanById(kanbanId);
            if(kanbanTemp==null){
                return "failure";
            }
            /* 确认调用者在当前项目内 */
            if (projectService.confirmTargetUserProjectExits(kanbanTemp.getProjectId(), user.getId()) <= 0) {
                return "failure";
            }
            /* 确认所移位置不存在其他卡片 */
            if(cardService.checkCurrentPositionCardNumber(card)>=1){
                return "failure";
            }
            int result = cardService.moveCard(card);
            return result>=1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }


}