package br.com.amil.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.amil.game.util.CarregarLog;

public class Main {

	
	public static void main(String[] args) {
		int nrMortosTotal = 0;
		Map<String, Integer> mapQtdeMortesPorJogador = new HashMap<String, Integer>();
		CarregarLog l = new CarregarLog();
		Map<String, List<Map<String, List<String>>>> mapaPartidas = l.gerarMapaPartidas("/home/felipe/game.log");
		List<Map<String, List<String>>> listaJogadores = mapaPartidas.get("11348965");
		for (Map<String, List<String>> map : listaJogadores) {
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				Integer mortes = null;
				List<String> listaMortos = map.get(key);
				nrMortosTotal =+ listaMortos.size();
				for (String nome : listaMortos) {
					if(mapQtdeMortesPorJogador.containsKey(nome)){
						mortes = mapQtdeMortesPorJogador.get(nome);
						mortes = mortes+1;
						mapQtdeMortesPorJogador.put(nome, mortes);						
					}else{
						mapQtdeMortesPorJogador.put(nome, 1);
					}
					nrMortosTotal++;
					System.out.println("qtde nick "+mortes);
					System.out.println("nome morto "+nome);
				}
				
			}
			System.out.println("nrMortosTotal"+nrMortosTotal);
		}
		
		System.out.println("nrMOrtes   "+nrMortosTotal);
		System.out.println("Nick mapa   --  "+mapQtdeMortesPorJogador.get("Nick"));
		
		for (Map<String, List<String>> map : listaJogadores) {
			List<String> list = map.get("Roman");
			if(list!=null){
				for (String string : list) {
					System.out.println(string);
				}
			}
		}

	}
}
