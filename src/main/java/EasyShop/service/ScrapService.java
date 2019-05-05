package EasyShop.service;

import EasyShop.dto.ItemDTO;

import java.net.MalformedURLException;
import java.util.List;

public interface ScrapService {

    List<ItemDTO> getItemsFromEmag(String page);

    List<ItemDTO> getItemsFromCarrefour(String page) throws InterruptedException, MalformedURLException;

    void saveLink(String page);

    List<String> getAllLinks();
}
