package EasyShop.service;

import EasyShop.dto.BillDTO;

import java.util.List;

public interface BillService {

    void insertBill(BillDTO billDTO);

    List<BillDTO> getAllBills();

    List<BillDTO> getBillsByShop(String shop);

    Boolean payBill(String payedBy, int billId);
}
