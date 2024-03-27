package com.babydevcode.stockhouse;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.babydevcode.stockhouse.presentation.StockSystemFx;

import javafx.application.Application;

@SpringBootApplication
public class StockHouseApplication {

	public static void main(String[] args) {
		Application.launch(StockSystemFx.class,args);
	}

}
