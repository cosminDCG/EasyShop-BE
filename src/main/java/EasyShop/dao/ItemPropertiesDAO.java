package EasyShop.dao;

import EasyShop.dto.ItemPropertiesDTO;

import java.util.List;

public interface ItemPropertiesDAO {

    void insertProperties(List<ItemPropertiesDTO> itemPropertiesDTOS);

    List<ItemPropertiesDTO> getPropertiesByProductId(int id);
}
