package EasyShop.service.impl;

import EasyShop.dao.BillDAO;
import EasyShop.dto.BillDTO;
import EasyShop.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDAO billDAO;

    public void insertBill(BillDTO billDTO){
        billDAO.insertBill(billDTO);
    }

    public List<BillDTO> getAllBills(){
        return billDAO.getAllBills();
    }

    public List<BillDTO> getBillsByShop(String shop){
        return billDAO.getBillsByShop(shop);
    }

    public Boolean payBill(String payedBy, int billId){

        billDAO.payBill(payedBy, billId);
        return true;
    }
}
