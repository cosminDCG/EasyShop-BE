package EasyShop.controllers;


import EasyShop.service.RepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RepController {

    @Autowired
    RepService repService;

    @RequestMapping(value = "/rep", method = RequestMethod.GET)
    public ResponseEntity getShopRep(@RequestParam String shop){
        int ok = repService.getShopRep(shop);
        return new ResponseEntity(ok, HttpStatus.OK);
    }

    @RequestMapping(value = "/rep/insert", method = RequestMethod.POST)
    public ResponseEntity insertRep(@RequestParam int id, @RequestParam String shop){
        repService.insertRep(id, shop);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/rep/delete", method = RequestMethod.POST)
    public ResponseEntity deleteRep(@RequestParam String shop){
        repService.deleteRep(shop);
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/rep/shop", method = RequestMethod.GET)
    public ResponseEntity getShopByRepId(@RequestParam int id){
        String shop = repService.getShopByRepId(id);
        return new ResponseEntity(shop, HttpStatus.OK);
    }
}
