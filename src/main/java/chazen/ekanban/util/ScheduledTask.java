package chazen.ekanban.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;

@Component
public class ScheduledTask {

    @Value("${cbs.imagesPath.path}")
    private String imagesPath;

//    @Scheduled(fixedDelay = 1000*10)
    /* 清理用户上传的无效临时头像，每天下午5点准时执行 */
    @Scheduled(cron = "0 0 17 * * ?")
    public void cleanUselessAvatar() throws Exception{

        ArrayList<File> arrayList = new ArrayList<File>();
        File imgageDir = new File(imagesPath);
        if(!imgageDir.exists()){
            return;
        }
        File[] fileArray = imgageDir.listFiles();
        for (File file : fileArray) {
            if (file.getName().endsWith("_temp.jpg")) {
                arrayList.add(file);
            }
        }

        for(File file:arrayList){
            if(file.exists()&&file.isFile()){
                file.delete();
            }
        }
        System.out.println("清除用户无用临时头像");
    }
}
