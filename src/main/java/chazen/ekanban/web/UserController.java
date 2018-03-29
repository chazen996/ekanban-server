package chazen.ekanban.web;

import chazen.ekanban.entity.SysUser;
import chazen.ekanban.service.UserService;
import chazen.ekanban.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Value("${cbs.imagesPath.path}")
    private String imagesPath;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @RequestMapping(method = RequestMethod.GET)
    public String getUsers() {
        return "hahaha终于通了";
    }


    /* 登陆状态下使用密码更改密码 */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(@RequestBody SysUser user, String oldPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        SysUser userTemp = userService.findUserByUsername(user.getUsername());
        if (userTemp == null) {
            return "failure";
        }
        if (encoder.matches(oldPassword, userTemp.getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
            return userService.updatePassword(user) == 1 ? "success" : "failure";
        } else {
            return "failure";
        }
    }

    /* 登陆状态下获取登陆用户个人信息 */
    @RequestMapping(value = "getPersonalInfo", method = RequestMethod.GET)
    public SysUser getPersonalInfo(String targetUsername,HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(targetUsername);
        if (user == null) {
            return null;
        }
        return user.getUsername().equals(username) ? user : null;
    }

    /* 登陆状态下修改用户头像 */
    @RequestMapping(value = "changeUserAvatar",method = RequestMethod.GET)
    public String changeUserAvatar(String username,HttpServletRequest request) throws FileNotFoundException {
        String token = request.getHeader(tokenHeader);
        String usernameWithToken = jwtTokenUtil.getUsernameFromToken(token.substring(tokenHead.length()));
        SysUser user = userService.findUserByUsername(username);
        if(user == null){
            return null;
        }
        if(user.getUsername().equals(usernameWithToken)){
            File imgageDir = new File(imagesPath);
            if(!imgageDir.exists()){
                return "failure";
            }

            File targetFile = new File(imagesPath+"/"+username+".jpg");
            File tempFile = new File(imagesPath+"/"+username+"_temp.jpg");
            if(targetFile.exists()){
                targetFile.delete();
                if(tempFile.exists()){
                    tempFile.renameTo(targetFile);
                    return "success";
                }else{
                    return "failure";
                }
            }else{
                return "failure";
            }

        }else{
            return "failure";
        }
    }

}
//    @RequestMapping(method = RequestMethod.OPTIONS)
//    public ResponseEntity handle(){
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @PostMapping("/signup")
//    public void signUp(@RequestBody SysUser user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userService.save(user);
//    }

    /*@Autowired
    private UserService userService;

    *//**
     * 根据ID查询用户
     * @param id
     * @return
     *//*
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserById (@PathVariable(value = "id") Integer id){
        JsonResult r = new JsonResult();
        try {
            User user = userService.getUserById(id);
            r.setResult(user);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    *//**
     * 查询用户列表
     * @return
     *//*
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList (){
        JsonResult r = new JsonResult();
        try {
            List<User> users = userService.getUserList();
            r.setResult(users);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    *//**
     * 添加用户
     * @param user
     * @return
     *//*
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add (@RequestBody User user){
        JsonResult r = new JsonResult();
        try {
            int orderId = userService.add(user);
            if (orderId < 0) {
                r.setResult(orderId);
                r.setStatus("fail");
            } else {
                r.setResult(orderId);
                r.setStatus("ok");
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    *//**
     * 根据id删除用户
     * @param id
     * @return
     *//*
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
        JsonResult r = new JsonResult();
        try {
            int ret = userService.delete(id);
            if (ret < 0) {
                r.setResult(ret);
                r.setStatus("fail");
            } else {
                r.setResult(ret);
                r.setStatus("ok");
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    *//**
     * 根据id修改用户信息
     * @param user
     * @return
     *//*
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody User user){
        JsonResult r = new JsonResult();
        try {
            int ret = userService.update(id, user);
            if (ret < 0) {
                r.setResult(ret);
                r.setStatus("fail");
            } else {
                r.setResult(ret);
                r.setStatus("ok");
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }*/


