package EasyShop.service;

public interface RepService {

    void insertRep(int user_id, String shop);

    int getShopRep(String user_id);

    void deleteRep(String shop);

    String getShopByRepId(int user_id);
}
