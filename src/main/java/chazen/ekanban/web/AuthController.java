package chazen.ekanban.web;

import chazen.ekanban.entity.JwtAuthenticationRequest;
import chazen.ekanban.entity.JwtAuthenticationResponse;
import chazen.ekanban.entity.SysUser;
import chazen.ekanban.service.AuthService;
import chazen.ekanban.service.UserService;
import chazen.ekanban.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${cbs.imagesPath.path}")
    private String imagesPath;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public SysUser register(@RequestBody SysUser addedUser,String tempAvatarName) throws AuthenticationException, IOException {
        SysUser preparedToReturn = authService.register(addedUser);
        if(preparedToReturn!=null){
            /* 注册用户时需考虑将c:/ekanban-data/images下临时头像重命名为各自的username */
            File file = new File(imagesPath+"/"+tempAvatarName+"_temp.jpg");
            File targetFile = new File(imagesPath+"/"+addedUser.getUsername()+".jpg");
            if(!file.exists()){/* 若已被定时任务删除或其他特殊情况 */
                File source = new File(imagesPath+"/"+"default.jpg");
                if(!source.exists()){/* 若default文件也不存在 */
                    File sourceBackUp = ResourceUtils.getFile("classpath:static/default.jpg");
                    Files.copy(sourceBackUp.toPath(),source.toPath());
                }
                Files.copy(source.toPath(),targetFile.toPath());
            }else{
                file.renameTo(targetFile);
            }
        }
        return preparedToReturn;
    }

    /* 处理文件上传，存储路径为绝对路径C:/ekanban-data/images */
    @RequestMapping(value="auth/uploadimg", method = RequestMethod.POST)
    public @ResponseBody
    String uploadImg(@RequestParam("avatar") MultipartFile file,@RequestParam("img-temp-name") String tempName,
                     HttpServletRequest request) {
        String contentType = file.getContentType();
        /* 头像只接受.jpg格式 */
        String fileName = tempName+"_temp.jpg";

        /* 通过配置项获取用户头像存储位置 */
        String filePath = imagesPath+"/";

        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        return "success";
    }

    /* 注册用户时实时检查是否有重命名 */
    @RequestMapping(value = "auth/checkUsername", method = RequestMethod.GET)
    public String checkUsername (String username) {
        SysUser user = userService.findUserByUsername(username);
        return user!=null?"have":"not-have";
    }
}