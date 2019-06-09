package EasyShop.service.impl;

import EasyShop.dao.ChartDAO;
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
}
