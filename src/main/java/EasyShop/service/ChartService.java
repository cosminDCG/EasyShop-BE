package EasyShop.service;

import EasyShop.dto.chart.OverallDateOrderDTO;
import EasyShop.dto.chart.ShopDateOrderDTO;

import java.util.List;

public interface ChartService {

    List<ShopDateOrderDTO> getShopOrdersPerWeek(String shop);
    List<ShopDateOrderDTO> getShopOrdersPerMonth(String shop);
    List<ShopDateOrderDTO> getShopOrdersPerYear(String shop);
    List<ShopDateOrderDTO> getShopPricePerWeek(String shop);
    List<ShopDateOrderDTO> getShopPricePerMonth(String shop);
    List<ShopDateOrderDTO> getShopPricePerYear(String shop);
    List<ShopDateOrderDTO> getShopStatsOverall(String shop);
    List<OverallDateOrderDTO> getEasyOrdersPerWeek();
    List<OverallDateOrderDTO> getEasyOrdersPerMonth();
    List<OverallDateOrderDTO> getEasyOrdersPerYear();
    List<OverallDateOrderDTO> getEasyPricePerWeek();
    List<OverallDateOrderDTO> getEasyPricePerMonth();
    List<OverallDateOrderDTO> getEasyPricePerYear();
    List<OverallDateOrderDTO> getEasyStatsOverall();
}
