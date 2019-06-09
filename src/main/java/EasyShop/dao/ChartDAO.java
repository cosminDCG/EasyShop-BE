package EasyShop.dao;

import EasyShop.dto.chart.ShopDateOrderDTO;

import java.util.List;

public interface ChartDAO {

    List<ShopDateOrderDTO> getShopOrdersPerWeek(String shop);
    List<ShopDateOrderDTO> getShopOrdersPerMonth(String shop);
}
