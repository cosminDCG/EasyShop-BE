package EasyShop.service;

import EasyShop.dto.chart.ShopDateOrderDTO;

import java.util.List;

public interface ChartService {

    List<ShopDateOrderDTO> getShopOrdersPerWeek(String shop);
    List<ShopDateOrderDTO> getShopOrdersPerMonth(String shop);
}
