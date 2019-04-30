package EasyShop.controllers;

import EasyShop.dto.PromoDTO;
import EasyShop.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PromoController {

    @Autowired
    private PromoService promoService;

    @RequestMapping(value = "/promo/verify", method = RequestMethod.GET)
    public ResponseEntity verifyPromoAndGetPercent(@RequestParam int userId, @RequestParam String promoCode){
        PromoDTO promoDTO = null;
        promoDTO = promoService.verifyPromoAndGetPercent(userId, promoCode);
        if(promoDTO != null){
            return new ResponseEntity(promoDTO, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/promo/use", method = RequestMethod.POST)
    public ResponseEntity useCode(@RequestBody PromoDTO promoDTO){
        Boolean ok = promoService.useCode(promoDTO);
        if(ok == true){
            return new ResponseEntity(ok, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/promo/user", method = RequestMethod.GET)
    public ResponseEntity getPromosByUserId(@RequestParam int user_id){
        List<PromoDTO> promoDTOList = promoService.getPromosByUserId(user_id);
        if(promoDTOList != null)
            return new ResponseEntity(promoDTOList, HttpStatus.OK);
        else return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/promo/custom", method = RequestMethod.POST)
    public ResponseEntity setCustomPromo(@RequestBody PromoDTO promoDTO) {
        Boolean ok = promoService.setCustomPromo(promoDTO);
        if(ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
