package GameManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface Adapter{
    void saveGameData(LinkedHashMap<String, Player> data);
    Map<String, Player> loadGameData();
    void saveRankingData(ArrayList<Player> ranking);
    ArrayList<Player> loadRankingData();
    void saveAdminData(ArrayList<Admin> data);
    ArrayList<Admin> loadAdminData();
    
}
