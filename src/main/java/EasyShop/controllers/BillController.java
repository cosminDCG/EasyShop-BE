package EasyShop.controllers;

import EasyShop.dto.BillDTO;
import EasyShop.dto.OrderDTO;
import EasyShop.dto.UserDTO;
import EasyShop.service.BillService;
import EasyShop.service.ItemService;
import EasyShop.service.OrderService;
import EasyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 27 16 * * *", zone = "Europe/Bucharest")
    public void generateBillsForAllShops(){
        List<UserDTO> reps = userService.getAllReps();

        for(UserDTO rep : reps){
            List<OrderDTO> orderDTOList = orderService.getLastMonthOrdersFromShop(rep.getShop());
            float total = 0.0f;
            for (OrderDTO orderDTO : orderDTOList){
                total += itemService.getTotalPriceFromCartList(orderDTO.getItems());
            }

            BillDTO billDTO = new BillDTO();
            billDTO.setBillValue((float)(total * 0.05));
            billDTO.setShop(rep.getShop());

            billService.insertBill(billDTO);
        }
    }

    @RequestMapping(value = "/bill/all", method = RequestMethod.GET)
    public ResponseEntity getAllBills(){
        List<BillDTO> billDTOList = billService.getAllBills();
        return new ResponseEntity(billDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/bill/shop", method = RequestMethod.GET)
    public ResponseEntity getBillsByShop(@RequestParam String shop){
        List<BillDTO> billDTOList = billService.getBillsByShop(shop);
        return new ResponseEntity(billDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/bill/pay", method = RequestMethod.POST)
    public ResponseEntity payBill(@RequestParam String payedBy, @RequestParam int billId){
        Boolean ok = billService.payBill(payedBy, billId);
        if (ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
