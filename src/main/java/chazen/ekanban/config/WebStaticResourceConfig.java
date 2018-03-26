package chazen.ekanban.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/* 改变spring的默认静态资源路径，可实现文件上传后直接存在本地指定目录下，且前端项目可
 * 无差别使用静态文件 */
@Configuration
public class WebStaticResourceConfig extends WebMvcConfigurationSupport {

    /* 配置本地文件存储路径 */
    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")){
            String imagesPath = WebStaticResourceConfig.class.getClassLoader().getResource("").getPath();
            if(imagesPath.indexOf(".jar")>0){
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            }else if(imagesPath.indexOf("classes")>0){
                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"));
            mImagesPath = imagesPath;
        }
//        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
        registry.addResourceHandler("/**").addResourceLocations(mImagesPath);
        // TODO Auto-generated method stub
        super.addResourceHandlers(registry);
    }
}