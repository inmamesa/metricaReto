package com.metricareto.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class TestController {

	private final static Logger logger = LoggerFactory.getLogger(TestController.class);
	private Counter consulta;
	private Counter convertir;
	
	public TestController(MeterRegistry registry)
	{
		this.consulta =Counter.builder("Invocaciones.contador").description("totales").register(registry);
		this.convertir =Counter.builder("Invocaciones.convertir").description("totales").register(registry);
	}
	
	@GetMapping("/")
	public ResponseEntity<String> index()
	{
		logger.info("Llamada endpoint principal");
		return new ResponseEntity<String>(HttpStatus.OK).ok("Hola");
	}
	
	@GetMapping(path="/consulTemperatura")
	public String consulTemperatura(int temperaturaC)
	{
		logger.info("Llamada consulta Temperatura");
		consulta.increment();
		return "La temperatura Farenheit es : "+(temperaturaC*1.8)+32;
		
	}
	@GetMapping(path="/convertir/{tempC}")
	public int coverTemperatura(@PathVariable int temperaturaC)
	{
		logger.info("Llamada conver Temperatura");
		convertir.increment();
		return (int) ((temperaturaC*1.8)+32);
		
	}
}
