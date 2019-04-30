package EasyShop.controllers;

import EasyShop.dto.BanDTO;
import EasyShop.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BanController {

    @Autowired
    private BanService banService;

    @RequestMapping(value = "/ban/add", method = RequestMethod.POST)
    public ResponseEntity addBan(@RequestBody BanDTO banDTO){
        Boolean ok = banService.addBan(banDTO);
        if(ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
