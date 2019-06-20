package EasyShop.dao;

import java.util.List;

public interface BlockDAO {

    void insertBlock(String shop);
    void deleteBlock(String shop);
    List<String> getBlockedShops();
}
