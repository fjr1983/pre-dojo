package br.com.amil.game;

import java.util.ArrayList;
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
		Map<String, List<Map<String, Integer> >> mapResultadoPartida = new HashMap<String, List<Map<String, Integer> >>();
		List<Map<String, Integer> > listaResultadoPartida = new ArrayList<Map<String,Integer>>();
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
						nrMortosTotal++;
					}
				}
			}
			mapResultadoPartida.put(nrPartida, listaResultadoPartida);

		}
		
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
