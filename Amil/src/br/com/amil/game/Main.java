package br.com.amil.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.amil.game.util.CarregarLog;

public class Main {
	
	private static final String PATH_ARQUIVO = "/home/felipe/game.log";

	public static void main(String[] args) {
		int nrMortosTotal = 0;
		int mortes = 1;
		int assassinatos = 1;
		Map<String, Integer> mapQtdeMortesSofridasPorJogador = new HashMap<String, Integer>();
		Map<String, Integer> mapQtdeAssassinatosCometidosPorJogador = new HashMap<String, Integer>();

		Map<String, List<Map<String, List<String>>>> mapaPartidas = CarregarLog.gerarMapaPartidas(PATH_ARQUIVO);
		Set<String> keySetPartidas = mapaPartidas.keySet();
		for (String nrPartida : keySetPartidas) {
		
			List<Map<String, List<String>>> listaJogadores = mapaPartidas.get(nrPartida);
			for (Map<String, List<String>> map : listaJogadores) {
				Set<String> keySet = map.keySet();
				for (String nomeJogador : keySet) {

					List<String> listaMortos = map.get(nomeJogador);
					nrMortosTotal =+ listaMortos.size();
					for (String nome : listaMortos) {
						if(nomeJogador.equals("<WORLD>")){
							if(mapQtdeAssassinatosCometidosPorJogador.containsKey(nome)) {
								assassinatos = mapQtdeAssassinatosCometidosPorJogador.get(nome);
								assassinatos = assassinatos+1;
								mapQtdeAssassinatosCometidosPorJogador.put(nome, assassinatos);						
							}else{
								mapQtdeAssassinatosCometidosPorJogador.put(nome, assassinatos);
							}
						}else{
							if(mapQtdeMortesSofridasPorJogador.containsKey(nome)){
								mortes = mapQtdeMortesSofridasPorJogador.get(nome);
								mortes = mortes+1;
								mapQtdeMortesSofridasPorJogador.put(nome, mortes);						
							}else{
								mapQtdeMortesSofridasPorJogador.put(nome, mortes);
							}
						}
						nrMortosTotal++;
					}
				}					
			}
		}
		System.out.println("RANKING: ");
		System.out.println("Número de Mortos Total: "+nrMortosTotal);

		for(String nomeJogadorMorto: mapQtdeMortesSofridasPorJogador.keySet()){
			System.out.println(nomeJogadorMorto +": Morreu:   "+mapQtdeMortesSofridasPorJogador.get(nomeJogadorMorto)+"     vez(es).");
		}
		
		for(String nomeJogadorAssassinato: mapQtdeAssassinatosCometidosPorJogador.keySet()){
			System.out.println(nomeJogadorAssassinato +": Matou:   "+mapQtdeMortesSofridasPorJogador.get(nomeJogadorAssassinato)+"     vez(es).");
		}

	}
}
