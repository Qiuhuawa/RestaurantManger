package org.zkpk.cs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableAsync
@MapperScan("org.zkpk.cs.mapper")
@ComponentScan({"org.zkpk.cs"})
public class CsApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(CsApplication.class, args);
    }
	
}
