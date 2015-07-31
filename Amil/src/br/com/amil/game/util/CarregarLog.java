package br.com.amil.game.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarregarLog { 

	private static final String PATTERN = ".*";
	private static final String HAS_STARTED = "has started";
	private static final String NEW_MATCH = "New match";
	private static final String BY = "by";
	private static final String USING = "using";
	private static final String KILLED = "killed";

	public Map<String, List<Map<String, List<String> >>> gerarMapaPartidas (String path)  { 
		Map<String, List<Map<String, List<String> >>> mapaPartidas = new HashMap<String, List<Map<String, List<String>>>>();
		Map<String, List<String>> mapaJogadores = new HashMap<String, List<String>>();

		String nrPartida = "";
		Integer beginIndex = 21;
		Integer beginIndexArma = null;
		List<String> listaDeMortos = null;

		try { 
			FileReader arq = new FileReader(path); 
			BufferedReader lerArq = new BufferedReader(arq); 
			String linha = lerArq.readLine(); 
			while (linha != null) {
				if(linha.matches(PATTERN+NEW_MATCH+PATTERN)){
					int newMatchIndex = linha.indexOf(NEW_MATCH)+NEW_MATCH.length();
					int hasStartedIndex = linha.indexOf(HAS_STARTED);
					nrPartida = linha.substring(newMatchIndex+1, hasStartedIndex-1);
				}else if(linha.matches(PATTERN+KILLED+PATTERN)){
					int beginKilledIndex = linha.indexOf(KILLED);
					int endKilledIndex = linha.indexOf(KILLED)+KILLED.length();
//					Integer indexArma = null;
					if(linha.matches(PATTERN+USING+PATTERN)){
						beginIndexArma = linha.indexOf(USING);
//						indexArma = linha.indexOf(USING)+USING.length();
					}else {
//						indexArma = linha.indexOf(BY);
						beginIndexArma = linha.indexOf(BY);
					}
					String jogadorMatador = linha.substring(beginIndex+1, beginKilledIndex-1);
					String jogadorMorto = linha.substring(endKilledIndex+1, beginIndexArma-1);
//					String arma = linha.substring(indexArma);

//					23/04/2013 15:34:22 - New match 11348965 has started
//					23/04/2013 15:36:04 - Roman killed Nick using M16
//					23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN
//					23/04/2013 15:39:22 - Match 11348965 has ended					
					
					if(mapaJogadores.containsKey(jogadorMatador)){
						listaDeMortos = mapaJogadores.get(jogadorMatador);
						listaDeMortos.add(jogadorMorto);
					}else{
						listaDeMortos = new ArrayList<String>();
						listaDeMortos.add(jogadorMorto);
						mapaJogadores.put(jogadorMatador, listaDeMortos);
					}
					List<Map<String, List<String> >> listaJogadores = null;
					if(mapaPartidas.containsKey(nrPartida)){
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
