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
			for (Map<String, List<String>> map : listaJogadores) {

				mapQtdeMortesSofridasPorJogador = new HashMap<String, Integer>();
				mapQtdeAssassinatosCometidosPorJogador = new HashMap<String, Integer>();
				listaResultadoPartida = new ArrayList<Map<String,Integer>>();

				Set<String> keySet = map.keySet();
				for (String nomeJogador : keySet) {
					listaMortos = map.get(nomeJogador);

					for (String nome : listaMortos) {

						if(nomeJogador.equals("<WORLD>")){
							if(mapQtdeAssassinatosCometidosPorJogador.containsKey(nome)) {
								assassinatos = mapQtdeAssassinatosCometidosPorJogador.get(nome);
								assassinatos = assassinatos+1;
								mapQtdeAssassinatosCometidosPorJogador.put(nome+" Matou :  ", assassinatos);						
							}else{
								mapQtdeAssassinatosCometidosPorJogador.put(nome+" Matou :  ", assassinatos);
							}
							listaResultadoPartida.add(mapQtdeAssassinatosCometidosPorJogador);
						}else{
							if(mapQtdeMortesSofridasPorJogador.containsKey(nome)){
								mortes = mapQtdeMortesSofridasPorJogador.get(nome);
								mortes = mortes+1;
								mapQtdeMortesSofridasPorJogador.put(nome+ "  Morreu :  ", mortes);						
							}else{
								mapQtdeMortesSofridasPorJogador.put(nome+ "  Morreu :  ", mortes);
							}
							listaResultadoPartida.add(mapQtdeMortesSofridasPorJogador);
						}
					}
				}
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
