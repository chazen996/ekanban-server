package chazen.ekanban;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("chazen.ekanban.mapper")
public class EkanbanServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EkanbanServerApplication.class, args);
	}
}
