package EasyShop.dao;

public interface RepDAO {

    void insertRep(int user_id, String shop);

    int getShopRep(String shop);

    void deleteRep(String shop);

    String getShopByRepId(int user_id);
}
