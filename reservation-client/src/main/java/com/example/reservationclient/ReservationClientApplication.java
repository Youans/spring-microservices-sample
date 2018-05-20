package com.example.reservationclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){

		return new RestTemplate();
	}

}
@RestController
@RequestMapping(path = "reservations")
class ReservationGatewayController{
	@Autowired
	public RestTemplate restTemplate;

		@GetMapping(path = "names")
	public Collection<String> getNames(){
		ParameterizedTypeReference<Resources<Reservation>> ptr=new ParameterizedTypeReference<Resources<Reservation>>(){};
		ResponseEntity<Resources<Reservation>> entity= restTemplate.exchange("http://localhost://reservation-service/reservations", HttpMethod.GET,null,ptr);
		return entity.getBody().getContent().stream().map(Reservation::getReservationNames).collect(Collectors.toList());
	}

}
class Reservation{
	private String reservationNames;

	public String getReservationNames(){

		return reservationNames;
	}


}