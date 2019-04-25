package EasyShop.service.impl;

import EasyShop.dao.PromoDAO;
import EasyShop.dto.OrderDTO;
import EasyShop.dto.PromoDTO;
import EasyShop.service.PromoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDAO promoDAO;

    public Boolean insertPromo(PromoDTO promoDTO){
        promoDAO.insertPromo(promoDTO);
        return true;
    }

    public String generatePromoCode(){
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }

    public void setPromo(OrderDTO orderDTO){

        PromoDTO promoDTO = new PromoDTO();
        if (orderDTO.getPrice() >= 300 && orderDTO.getPrice() <= 500){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(3);
            Boolean promoCheck = insertPromo(promoDTO);
        }

        if (orderDTO.getPrice() >= 501 && orderDTO.getPrice() <= 1000){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(5);
            Boolean promoCheck = insertPromo(promoDTO);
        }

        if (orderDTO.getPrice() >= 1001 && orderDTO.getPrice() <= 2000){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(7);
            Boolean promoCheck = insertPromo(promoDTO);
        }

        if (orderDTO.getPrice() >= 2001 && orderDTO.getPrice() <= 3500){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(9);
            Boolean promoCheck = insertPromo(promoDTO);
        }

        if (orderDTO.getPrice() >= 3501 && orderDTO.getPrice() <= 6000){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(11);
            Boolean promoCheck = insertPromo(promoDTO);
        }

        if (orderDTO.getPrice() >= 6001 && orderDTO.getPrice() <= 9000){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(13);
            Boolean promoCheck = insertPromo(promoDTO);
        }

        if (orderDTO.getPrice() >= 9001){
            promoDTO.setUserId(orderDTO.getUserId());
            promoDTO.setPromoCode(generatePromoCode());
            promoDTO.setPromoPercent(15);
            Boolean promoCheck = insertPromo(promoDTO);
        }

    }

    public PromoDTO verifyPromoAndGetPercent(int userId, String promoCode){
        return promoDAO.verifyPromoAndGetPercent(userId, promoCode);
    }

    public Boolean useCode(PromoDTO promoDTO){
        promoDAO.useCode(promoDTO);
        return true;
    }

    public List<PromoDTO> getPromosByUserId(int user_id){
        return promoDAO.getPromosByUserId(user_id);
    }
}
