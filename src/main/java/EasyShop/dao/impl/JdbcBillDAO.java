package EasyShop.dao.impl;

import EasyShop.dao.BillDAO;
import EasyShop.dto.BillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcBillDAO implements BillDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insertBill(BillDTO billDTO){
        String sqlInsert = "" +
                "INSERT INTO bill(shop, bill_value, payed, emitted_date) VALUES( " +
                "    :shop, " +
                "    :bill_value, " +
                "    0, " +
                "    SYSDATE() " +
                ")";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", billDTO.getShop());
        namedParameters.addValue("bill_value", billDTO.getBillValue());

        jdbcTemplate.update(sqlInsert, namedParameters);
    }

    @Override
    public List<BillDTO> getAllBills(){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM bill ";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<BillDTO> results = new ArrayList<>();
            while(rs.next()) {
                BillDTO billDTO = new BillDTO();
                billDTO.setId(rs.getInt("bill_id"));
                billDTO.setShop(rs.getString("shop"));
                billDTO.setBillValue(rs.getFloat("bill_value"));
                billDTO.setPayed(rs.getInt("payed"));
                billDTO.setPayedBy(rs.getString("payed_by"));
                billDTO.setEmittedDate(rs.getDate("emitted_date"));
                billDTO.setPayDate(rs.getDate("pay_date"));

                results.add(billDTO);
            }
            return results;

        });
    }

    @Override
    public List<BillDTO> getBillsByShop(String shop){
        String sqlSelect = "" +
                "SELECT " +
                "    * " +
                "FROM bill " +
                "WHERE shop = :shop";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("shop", shop);

        return jdbcTemplate.execute(sqlSelect, namedParameters, preparedStatement ->{
            ResultSet rs = preparedStatement.executeQuery();
            List<BillDTO> results = new ArrayList<>();
            while(rs.next()) {
                BillDTO billDTO = new BillDTO();
                billDTO.setId(rs.getInt("bill_id"));
                billDTO.setShop(rs.getString("shop"));
                billDTO.setBillValue(rs.getFloat("bill_value"));
                billDTO.setPayed(rs.getInt("payed"));
                billDTO.setPayedBy(rs.getString("payed_by"));
                billDTO.setEmittedDate(rs.getDate("emitted_date"));
                billDTO.setPayDate(rs.getDate("pay_date"));

                results.add(billDTO);
            }
            return results;

        });
    }

    @Override
    public void payBill(String payedBy, int billId){
        String sqlUpdate = "" +
                "UPDATE bill " +
                "SET payed = 1, " +
                " payed_by =:payed_by, " +
                " pay_date = SYSDATE() " +
                "WHERE bill_id =:billId";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("payed_by", payedBy);
        namedParameters.addValue("billId", billId);

        jdbcTemplate.update(sqlUpdate, namedParameters);
    }
}

