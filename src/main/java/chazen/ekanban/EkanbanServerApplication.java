package chazen.ekanban;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("chazen.ekanban.mapper")
@EnableScheduling
public class EkanbanServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EkanbanServerApplication.class, args);
	}
}
