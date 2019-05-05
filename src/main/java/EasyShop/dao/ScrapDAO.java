package EasyShop.dao;

import java.util.List;

public interface ScrapDAO {

    void saveLink(String page);

    List<String> getAllLinks();
}
