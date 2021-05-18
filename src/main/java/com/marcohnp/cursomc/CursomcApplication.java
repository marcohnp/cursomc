package com.marcohnp.cursomc;

import com.marcohnp.cursomc.domain.*;
import com.marcohnp.cursomc.domain.enums.EstadoPagamento;
import com.marcohnp.cursomc.domain.enums.TipoCliente;
import com.marcohnp.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
