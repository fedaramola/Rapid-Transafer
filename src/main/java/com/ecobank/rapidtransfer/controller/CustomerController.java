package com.ecobank.rapidtransfer.controller;

import com.ecobank.rapidtransfer.obj.CustParameter;
import com.ecobank.rapidtransfer.obj.Customer;
import com.ecobank.rapidtransfer.utils.Utilities;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    @PostMapping(value = "/customerdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerRecord (@RequestBody CustParameter cust){

        Connection connection = null;
        OracleCallableStatement statement = null;
        ResultSet resultSet = null;
        Date dtToday = null;
        DateFormat dtFmt = null;

        List<Customer> dtls = new ArrayList<>();

        Customer customer = null;

        try{
            dtFmt = new SimpleDateFormat("dd-MM-yyyy");
            dtToday = dtFmt.parse(cust.getPdob());

            java.sql.Date sqlDate = new java.sql.Date(dtToday.getTime());


             connection = Utilities.getConnection();
             connection = connection.unwrap(OracleConnection.class);
             statement = (OracleCallableStatement)connection.prepareCall("{?=call rt_customer_validation(?,?,?,?,?,?)}");
             statement.registerOutParameter(1, OracleTypes.CURSOR);
             statement.setString(2,cust.getProduct());
             statement.setString(3,cust.getCustomerid());
             statement.setString(4,cust.getFirstname());
             statement.setString(5,cust.getLastname());
             statement.setString(6,cust.getP_phone_num());
             statement.setDate(7,sqlDate);

            System.out.println("sqlDate: "+ sqlDate);
            System.out.println("product: "+ cust.getProduct());
            System.out.println("customerid: "+ cust.getCustomerid());
            System.out.println("firstName: "+ cust.getFirstname());
            System.out.println("Lastname: "+ cust.getLastname());
            System.out.println("phonenumber: "+ cust.getP_phone_num());



             statement.execute();

             resultSet = (ResultSet)statement.getObject(1);

             while (resultSet.next()){
                 customer = new Customer();
                 customer.setRecord_id(resultSet.getString("record_id") == null ? "": resultSet.getString("record_id"));
                 customer.setCustomer_id(resultSet.getString("customer_id"));
                 customer.setTitle(resultSet.getString("title") == null ? "": resultSet.getString("title"));
                 customer.setFirst_name(resultSet.getString("first_name"));
                 customer.setLast_name(resultSet.getNString("last_name"));
                 customer.setGender(resultSet.getNString("gender"));
                 customer.setEmail_address(resultSet.getString("email_address"));
                 customer.setMobile_phone(resultSet.getString("mobile_phone"));
                 customer.setAffiliate(resultSet.getString("affiliate"));
                 customer.setBranch_code(resultSet.getString("branch_code"));
                 customer.setCustomer_type(resultSet.getString("customer_type"));
                 customer.setCreate_user(resultSet.getString("create_user"));
                 customer.setCreate_date(resultSet.getString("create_date"));
                 customer.setVerify_user(resultSet.getString("verify_user"));
                 customer.setVerify_date(resultSet.getString("verify_date"));
                 customer.setStatus(resultSet.getString("status"));
                 customer.setContact_address(resultSet.getString("contact_address"));
                 customer.setAprov_flg(resultSet.getString("aprov_flg"));
                 customer.setModify_create_user(resultSet.getString("modify_create_user"));
                 customer.setModify_create_date(resultSet.getString("modify_create_date"));
                 customer.setApprov_action(resultSet.getString("approv_action"));
                 customer.setModify_verify_user(resultSet.getString("modify_verify_user"));
                 customer.setModify_verify_date(resultSet.getString("modify_verify_date"));
                 customer.setDob(resultSet.getString("dob"));
                 customer.setNationality(resultSet.getString("nationality"));
                 customer.setCountry(resultSet.getString("country"));
                 customer.setIs_sms_sent(resultSet.getString("is_sms_sent"));
                 customer.setFlexcube_customerid(resultSet.getString("flexcube_customerid"));
                 dtls.add(customer);
             }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Null pointer exception");
        } finally {
            try{ if(connection!=null){
                connection.close();
            } if(statement!=null){
                statement.close();
            }if(resultSet!=null){
                resultSet.close();
            }

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Null pointer exception 2");
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(dtls);
    }
}
