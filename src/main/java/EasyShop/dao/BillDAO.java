package EasyShop.dao;

import EasyShop.dto.BillDTO;

import java.util.List;

public interface BillDAO {

    void insertBill(BillDTO billDTO);

    List<BillDTO> getAllBills();

    List<BillDTO> getBillsByShop(String shop);

    void payBill(String payedBy, int billId);
}
