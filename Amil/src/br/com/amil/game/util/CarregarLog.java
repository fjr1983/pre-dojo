package br.com.amil.game.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarregarLog { 

	private static final String SEARCH_PATTERN = ".*";
	private static final String HAS_STARTED = "has started";
	private static final String NEW_MATCH = "New match";
	private static final String BY = "by";
	private static final String USING = "using";
	private static final String KILLED = "killed";

	public static Map<String, List<Map<String, List<String> >>> gerarMapaPartidas (String path)  { 
		Map<String, List<Map<String, List<String> >>> mapaPartidas = new HashMap<String, List<Map<String, List<String>>>>();
		Map<String, List<String>> mapaJogadores = null;

		String nrPartida = "";
		Integer beginIndex = 21;
		Integer beginIndexArma = null;
		List<String> listaDeMortos = null;
		List<Map<String, List<String> >> listaJogadores = null;

		try { 
			FileReader arq = new FileReader(path); 
			BufferedReader lerArq = new BufferedReader(arq); 
			String linha = lerArq.readLine(); 
			while (linha != null) {
				if(linha.matches(SEARCH_PATTERN+NEW_MATCH+SEARCH_PATTERN)){
					int newMatchIndex = linha.indexOf(NEW_MATCH)+NEW_MATCH.length();
					int hasStartedIndex = linha.indexOf(HAS_STARTED);
					nrPartida = linha.substring(newMatchIndex+1, hasStartedIndex-1);
					mapaJogadores = new HashMap<String, List<String>>();
				}else if(linha.matches(SEARCH_PATTERN+KILLED+SEARCH_PATTERN)){
					int beginKilledIndex = linha.indexOf(KILLED);
					int endKilledIndex = linha.indexOf(KILLED)+KILLED.length();
					if(linha.matches(SEARCH_PATTERN+USING+SEARCH_PATTERN)){
						beginIndexArma = linha.indexOf(USING);
					}else {
						beginIndexArma = linha.indexOf(BY);
					}
					String jogadorMatador = linha.substring(beginIndex+1, beginKilledIndex-1);
					String jogadorMorto = linha.substring(endKilledIndex+1, beginIndexArma-1);
					
					if(mapaJogadores.containsKey(jogadorMatador)){
						listaDeMortos = mapaJogadores.get(jogadorMatador);
						listaDeMortos.add(jogadorMorto);
					}else{
						listaDeMortos = new ArrayList<String>();
						listaDeMortos.add(jogadorMorto);
						mapaJogadores.put(jogadorMatador, listaDeMortos);
					}
					if(mapaPartidas.containsKey(nrPartida) ){
						listaJogadores = mapaPartidas.get(nrPartida);
					}else{
						listaJogadores = new ArrayList<Map<String, List<String>>>();
						listaJogadores.add(mapaJogadores);
						mapaPartidas.put(nrPartida, listaJogadores);
					}
				}
				linha = lerArq.readLine();
			}
			arq.close();
		} 
		catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		} 
		return mapaPartidas;
	}


	
}
