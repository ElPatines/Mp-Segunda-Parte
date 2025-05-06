package GameManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ranking implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Player> ranking;
    private Adapter a;

    public Ranking(Adapter adapter) {
        this.a = adapter;
        if (a.loadRankingData() != null) {
            this.ranking = adapter.loadRankingData();
        }
        else {
            this.ranking = new ArrayList<Player>();
        }
        
    }
    public Ranking(){

    }

    public void showRanking() {
        System.out.println("Ranking de jugadores: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(ranking.get(i).getUserName() + " - " + ranking.get(i).getMoney() + " gold");
        }
        ;

    }

    public void updateRanking(LinkedHashMap<String, Player> userList) {
        List<Player> ordenados = userList.values().stream()
            .sorted((u1, u2) -> Integer.compare(u2.getMoney(), u1.getMoney()))
            .collect(Collectors.toList());
    
        List<Player> primeros10 = ordenados.subList(0, Math.min(10, ordenados.size()));
        ranking.clear();
        ranking.addAll(primeros10);
        a.saveRankingData(ranking);
    }
}
