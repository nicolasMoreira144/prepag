package com.wex.prepag.role;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateCard {
	
	private static final Logger log = LoggerFactory.getLogger(GenerateCard.class);
	
	private static final Random generator = new Random();
	private Long binCard = 0L; 
	private Long numberCard = 0L;
	private String cvv = ""; 
	private LocalDate date = LocalDate.now();
	private String password = "";
	private String passwordCrip = "";
	
	private int generateBinCard() {
		int nx = 100000 + generator.nextInt(900000);
		binCard = (long) nx;
		return nx;
	}

	private int generateNumberCard() {
		return 100000000 + generator.nextInt(900000000);
	}
	
	private int generateLastNumberCard() {
		return 1 + generator.nextInt(9);
	}
	
	public void generateCard() {
		numberCard = createNumberCard();
		cvv = generateCVV(date, numberCard);
		generatePasswordCrip();
	}
	
	private Long createNumberCard() {
		String numerosSepados = generateBinCard() + "" + generateNumberCard() + "" + generateLastNumberCard();
		Long numberCard = Long.valueOf(numerosSepados);
		log.info("Numero do cartão criado : {} ", numberCard );
		return numberCard;
	}
	
	private void generatePasswordCrip() {
		Integer nx = 1000 + generator.nextInt(9000);
		password  = nx.toString(); 
		passwordCrip = criptografia(nx.toString());
		log.info("Password criptografado criado");
	}
	
	private String generateCVV(LocalDate date, Long numberCard){
		String stringDate = date.toString();
		stringDate = stringDate.replace("-", "");
		String stringNumberCard = numberCard.toString(); 
		
		StringBuilder cvv = new StringBuilder();
		// sorteando 1 a 7 ou 6 
		int x = 1 + generator.nextInt(stringDate.length() - 1);
		cvv.append(stringDate.charAt(x));
		// sorteando 1 a 15
		int x2 = 1 + generator.nextInt(stringNumberCard.length() - 1);
		int x3 = 1 + generator.nextInt(stringNumberCard.length() - 1);
		
		cvv.append(stringNumberCard.charAt(x2));
		cvv.append(stringNumberCard.charAt(x3));
		
		String StringCvv = cvv.toString();
		
		log.info("CVV do cartão criado");
		
		return StringCvv;
	}
	
	
	private LocalDate criarValidade(){
		return date = date.plusYears(2);
	}
	
	public static String criptografia(String password) {
		String senhaCrip = "";
		
		String s= password;
	     MessageDigest m;
		
	     try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			senhaCrip  =  new BigInteger(1,m.digest()).toString(16);
			//System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
		
		} catch (NoSuchAlgorithmException e) {
			// acresentar log dps
			e.printStackTrace();
		}
		
	     return senhaCrip;
	}
	
	public Long getBinCard() {
		return binCard;
	}

	public Long getNumberCard() {
		return numberCard;
	}

	public String getCvv() {
		return cvv;
	}

	public LocalDate getValidade(){
		return criarValidade();
	}

	public String getPasswordCrip() {
		return passwordCrip;
	}

	public String getPassword() {
		return password;
	}
	
	
}
