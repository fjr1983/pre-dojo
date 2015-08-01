package br.com.amil.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.amil.game.util.CarregarLog;

public class Game {
	
	private static final String PATH_ARQUIVO = "/home/felipe/game.log";

	public static void main(String[] args) {

		Map<String, List<Map<String, List<String>>>> mapaPartidas = CarregarLog.gerarMapaPartidas(PATH_ARQUIVO);

		Map<String, List<Map<String, Integer>>> mapResultadoPartida = prepararMapaResultados(mapaPartidas);
		
		exibirResultado(mapResultadoPartida);
	}

	private static Map<String, List<Map<String, Integer>>> prepararMapaResultados(
			Map<String, List<Map<String, List<String>>>> mapaPartidas) {

		int mortes = 1;
		int assassinatos = 1;
		List<Map<String, Integer> > listaResultadoPartida = null;
		Map<String, Integer> mapQtdeMortesSofridasPorJogador = null;
		Map<String, Integer> mapQtdeAssassinatosCometidosPorJogador = null;
		List<String> listaMortos = null;
		Map<String, List<Map<String, Integer> >> mapResultadoPartida  = new HashMap<String, List<Map<String, Integer> >>();

		for (String nrPartida : mapaPartidas.keySet()) {
			
			List<Map<String, List<String>>> listaJogadores = mapaPartidas.get(nrPartida);
			for (Map<String, List<String>> mapJogadorMatador : listaJogadores) {
				listaResultadoPartida = new ArrayList<Map<String,Integer>>();
				mapQtdeMortesSofridasPorJogador = new HashMap<String, Integer>();
				mapQtdeAssassinatosCometidosPorJogador = new HashMap<String, Integer>();

				Set<String> keySet = mapJogadorMatador.keySet();
				for (String nomeJogadorMatador : keySet) {
					listaMortos = mapJogadorMatador.get(nomeJogadorMatador);

					for (String nomeJogadorMorto : listaMortos) {

						if(nomeJogadorMatador.equals("<WORLD>")){
							if(mapQtdeAssassinatosCometidosPorJogador.containsKey(nomeJogadorMorto+" Matou :  ")) {
								assassinatos = mapQtdeAssassinatosCometidosPorJogador.get(nomeJogadorMorto+" Matou :  ");
								assassinatos = assassinatos+1;
								mapQtdeAssassinatosCometidosPorJogador.put(nomeJogadorMorto+" Matou :  ", assassinatos);						
							}else{
								mapQtdeAssassinatosCometidosPorJogador.put(nomeJogadorMorto+" Matou :  ", 1);
							}
						}else{
							if(mapQtdeAssassinatosCometidosPorJogador.containsKey(nomeJogadorMatador+" Matou :  ")) {
								assassinatos = mapQtdeAssassinatosCometidosPorJogador.get(nomeJogadorMatador+" Matou :  ");
								assassinatos = assassinatos+1;
								mapQtdeAssassinatosCometidosPorJogador.put(nomeJogadorMatador+" Matou :  ", assassinatos);						
							}else{
								mapQtdeAssassinatosCometidosPorJogador.put(nomeJogadorMatador+" Matou :  ", 1);
							}
							if(mapQtdeMortesSofridasPorJogador.containsKey(nomeJogadorMorto+" Morreu :  ")){
								mortes = mapQtdeMortesSofridasPorJogador.get(nomeJogadorMorto+" Morreu :  ");
								mortes = mortes+1;
								mapQtdeMortesSofridasPorJogador.put(nomeJogadorMorto+ " Morreu :  ", mortes);						
							}else{
								mapQtdeMortesSofridasPorJogador.put(nomeJogadorMorto+ " Morreu :  ", 1);
							}
						}

					}

				}
				listaResultadoPartida.add(mapQtdeAssassinatosCometidosPorJogador);
				listaResultadoPartida.add(mapQtdeMortesSofridasPorJogador);

			}
			mapResultadoPartida.put(nrPartida, listaResultadoPartida);
		}
		return mapResultadoPartida;
	}

	private static void exibirResultado(
			Map<String, List<Map<String, Integer>>> mapResultadoPartida) {
		for (String nrPartida : mapResultadoPartida.keySet()) {
			System.out.println("RANKING: Partida:   " + nrPartida);

			for (Map<String, Integer> mapResultadoPorJogador : mapResultadoPartida.get(nrPartida)) {
					
				for(String nome: mapResultadoPorJogador.keySet()) {
					System.out.println(nome +"  "+mapResultadoPorJogador.get(nome)+"     vez(es).");
				}
			}
		}
	}
}
