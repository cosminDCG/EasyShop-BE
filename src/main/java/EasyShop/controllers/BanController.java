package EasyShop.controllers;

import EasyShop.dto.BanDTO;
import EasyShop.service.BanService;
import EasyShop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class BanController {

    @Autowired
    private BanService banService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/ban/add", method = RequestMethod.POST)
    public ResponseEntity addBan(@RequestBody BanDTO banDTO){
        BanDTO current_ban = banService.getActiveBanByUserId(banDTO.getBannedUser());
        if(current_ban.getId() != 0){
            banService.updateBanDate(current_ban.getId(), banDTO.getBanDays(), banDTO.getReason());
            emailService.sendBanMessage(banService.getActiveBanByUserId(banDTO.getBannedUser()));
            return new ResponseEntity(true, HttpStatus.OK);
        }
        Boolean ok = banService.addBan(banDTO);
        if(ok == true){
            emailService.sendBanMessage(banService.getActiveBanByUserId(banDTO.getBannedUser()));
            return new ResponseEntity(ok, HttpStatus.OK);
        }
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
