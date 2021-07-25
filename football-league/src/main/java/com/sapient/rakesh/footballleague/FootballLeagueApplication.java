package com.sapient.rakesh.footballleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FootballLeagueApplication {
	

  public static void main(String[] args) {
    SpringApplication.run(FootballLeagueApplication.class, args);
    
    System.out.println("\n\n********* Application started*********");
    
    
  }

}
