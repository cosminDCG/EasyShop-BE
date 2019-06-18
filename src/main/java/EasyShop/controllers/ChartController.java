package EasyShop.controllers;

import EasyShop.dto.chart.OverallDateOrderDTO;
import EasyShop.dto.chart.ShopDateOrderDTO;
import EasyShop.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChartController {

    @Autowired
    private ChartService chartService;

    @RequestMapping(value = "stats/shop/orders/week", method = RequestMethod.GET)
    public ResponseEntity getShopOrdersPerWeek(@RequestParam String shop){
        List<ShopDateOrderDTO> shopDateOrderDTOList = chartService.getShopOrdersPerWeek(shop);
        return new ResponseEntity(shopDateOrderDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "stats/shop/orders/month", method = RequestMethod.GET)
    public ResponseEntity getShopOrdersPerMonth(@RequestParam String shop){
        List<ShopDateOrderDTO> shopDateOrderDTOList = chartService.getShopOrdersPerMonth(shop);
        return new ResponseEntity(shopDateOrderDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "stats/shop/orders/year", method = RequestMethod.GET)
    public ResponseEntity getShopOrdersPerYear(@RequestParam String shop){
        List<ShopDateOrderDTO> shopDateOrderDTOList = chartService.getShopOrdersPerYear(shop);
        return new ResponseEntity(shopDateOrderDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "stats/easy/orders/week", method = RequestMethod.GET)
    public ResponseEntity getEasyOrdersPerWeek(){
        List<OverallDateOrderDTO> overallDateOrderDTOList = chartService.getEasyOrdersPerWeek();
        return new ResponseEntity(overallDateOrderDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "stats/easy/orders/month", method = RequestMethod.GET)
    public ResponseEntity getEasyOrdersPerMonth(){
        List<OverallDateOrderDTO> overallDateOrderDTOList = chartService.getEasyOrdersPerMonth();
        return new ResponseEntity(overallDateOrderDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "stats/easy/orders/year", method = RequestMethod.GET)
    public ResponseEntity getEasyOrdersPerYear(){
        List<OverallDateOrderDTO> overallDateOrderDTOList = chartService.getEasyOrdersPerYear();
        return new ResponseEntity(overallDateOrderDTOList, HttpStatus.OK);
    }
}
