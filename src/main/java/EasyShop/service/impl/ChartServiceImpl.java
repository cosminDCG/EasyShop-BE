package EasyShop.service.impl;

import EasyShop.dao.ChartDAO;
import EasyShop.dto.chart.OverallDateOrderDTO;
import EasyShop.dto.chart.ShopDateOrderDTO;
import EasyShop.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartDAO chartDAO;

    public List<ShopDateOrderDTO> getShopOrdersPerWeek(String shop){
        return chartDAO.getShopOrdersPerWeek(shop);
    }

    public List<ShopDateOrderDTO> getShopOrdersPerMonth(String shop){
        return chartDAO.getShopOrdersPerMonth(shop);
    }

    public List<ShopDateOrderDTO> getShopOrdersPerYear(String shop) { return chartDAO.getShopOrdersPerYear(shop); }
    
    public List<OverallDateOrderDTO> getEasyOrdersPerWeek(){ return chartDAO.getEasyOrdersPerWeek(); }

    public List<OverallDateOrderDTO> getEasyOrdersPerMonth() { return chartDAO.getEasyOrdersPerMonth(); }

    public List<OverallDateOrderDTO> getEasyOrdersPerYear() { return chartDAO.getEasyOrdersPerYear(); }

    public List<ShopDateOrderDTO> getShopPricePerWeek(String shop){ return chartDAO.getShopPricePerWeek(shop); }

    public List<ShopDateOrderDTO> getShopPricePerMonth(String shop){
        return chartDAO.getShopPricePerMonth(shop);
    }

    public List<ShopDateOrderDTO> getShopPricePerYear(String shop){
        return chartDAO.getShopPricePerYear(shop);
    }

    public List<ShopDateOrderDTO> getShopStatsOverall(String shop) { return chartDAO.getShopStatsOverall(shop); }

    public List<OverallDateOrderDTO> getEasyPricePerWeek(){
        return chartDAO.getEasyPricePerWeek();
    }

    public List<OverallDateOrderDTO> getEasyPricePerMonth() {
        return chartDAO.getEasyPricePerMonth();
    }

    public List<OverallDateOrderDTO> getEasyPricePerYear() {
        return chartDAO.getEasyPricePerYear();
    }

    public List<OverallDateOrderDTO> getEasyStatsOverall() { return chartDAO.getEasyStatsOverall(); }
}
