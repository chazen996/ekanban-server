package chazen.ekanban.web;

import chazen.ekanban.entity.Card;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/card")
@PreAuthorize("hasRole('USER')")
public class CardController {

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


    @RequestMapping(value = "createCard",method = RequestMethod.POST)
    public String createCard(@RequestBody Card card,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = card.getProjectId();
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

            return cardService.createCard(card)==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "deleteCard",method = RequestMethod.POST)
    public String deleteCard(@RequestBody Card card,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = card.getProjectId();
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

            return cardService.deleteCardByCardId(card.getCardId())==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }

    @RequestMapping(value = "updateCard",method = RequestMethod.POST)
    public String updateCard(@RequestBody Card card,String username,HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String usernameTemp = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if (user == null) {
            return "failure";
        }
        int projectId = card.getProjectId();
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

            return cardService.updateCard(card)==1?"success":"failure";
            /* 实际业务代码end */
        } else {
            return "failure";
        }
    }
}