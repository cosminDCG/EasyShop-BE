package EasyShop.controllers;

import EasyShop.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlockController {

    @Autowired
    private BlockService blockService;

    @RequestMapping(value = "block/insert", method = RequestMethod.POST)
    public ResponseEntity insertBlock(@RequestParam String shop){
        Boolean ok = blockService.insertBlock(shop);
        if (ok == true){
            return new ResponseEntity(ok, HttpStatus.OK);
        } else{
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "block/delete", method = RequestMethod.POST)
    public ResponseEntity deleteBlock(@RequestParam String shop){
        Boolean ok = blockService.deleteBlock(shop);
        if (ok == true){
            return new ResponseEntity(ok, HttpStatus.OK);
        } else{
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "block/all", method = RequestMethod.GET)
    public ResponseEntity getBlockedShops(){
        List<String> blockedShops = blockService.getBlockedShops();
        return new ResponseEntity(blockedShops, HttpStatus.OK);
    }
}
