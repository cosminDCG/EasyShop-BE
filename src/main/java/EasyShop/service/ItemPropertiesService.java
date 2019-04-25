package EasyShop.service;

import EasyShop.dto.ItemPropertiesDTO;

import java.util.List;

public interface ItemPropertiesService {

    Boolean insertProperties(List<ItemPropertiesDTO> itemPropertiesDTOS);

    List<ItemPropertiesDTO> getPropertiesByProductId(int id);
}
