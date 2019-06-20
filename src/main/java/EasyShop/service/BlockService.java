package EasyShop.service;

import java.util.List;

public interface BlockService {

    Boolean insertBlock(String shop);

    Boolean deleteBlock(String shop);

    List<String> getBlockedShops();
}
