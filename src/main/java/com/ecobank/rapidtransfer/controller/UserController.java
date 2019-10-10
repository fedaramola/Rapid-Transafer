package com.ecobank.rapidtransfer.controller;

import com.ecobank.rapidtransfer.obj.Student;
import com.ecobank.rapidtransfer.obj.UserInfo;
import com.ecobank.rapidtransfer.obj.SetStatus;
import com.ecobank.rapidtransfer.utils.Utilities;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @GetMapping(value = "/users/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserRecord() {


        List<UserInfo> userInfoList = new ArrayList<>();

        UserInfo userInfo = null;

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            conn = Utilities.getConnection();
            preparedStatement = conn.prepareStatement("SELECT * FROM RT_USER");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setEmail(resultSet.getString("EMAIL_ADDRESS"));
                userInfo.setFirstName(resultSet.getString("FIRST_NAME"));
                userInfo.setLastName(resultSet.getString("LAST_NAME"));
                userInfo.setUserId(resultSet.getString("USER_ID"));
                userInfoList.add(userInfo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userInfoList);

    }
    @GetMapping("/users")
    public ResponseEntity<?> getUserRecordById(@RequestParam("id") String id){


        UserInfo userInfo = null;

        Connection conn = null;
        OracleCallableStatement callableStatement = null;
        ResultSet resultSet = null;


        try {
            conn = Utilities.getConnection();
            conn = conn.unwrap(OracleConnection.class);
            callableStatement = (OracleCallableStatement)conn.prepareCall("{? =call get_user(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setString(2, id);

            callableStatement.execute();
            resultSet = (ResultSet)callableStatement.getObject(1);

            while (resultSet.next()) {
                userInfo = new UserInfo();
                userInfo.setEmail(resultSet.getString("EMAIL_ADDRESS"));
                userInfo.setFirstName(resultSet.getString("FIRST_NAME"));
                userInfo.setLastName(resultSet.getString("LAST_NAME"));
                userInfo.setUserId(resultSet.getString("USER_ID"));

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userInfo);

    }

    @GetMapping("/teller")
    public ResponseEntity <?> getUserByTellerBrnCode (@RequestParam ("id") String id){

        UserInfo userInfo = null;
        Connection conn = null;
        OracleCallableStatement cll = null;
        ResultSet rs = null;

        try{
             conn = Utilities.getConnection();
             conn = conn.unwrap(OracleConnection.class);
             cll = (OracleCallableStatement)conn.prepareCall( "{?=call FN_GET_USER(?)}");
             cll.registerOutParameter(1,OracleTypes.CURSOR);
             cll.setString(2,id);

              cll.execute();

               rs = (ResultSet) cll.getObject(1);

              while(rs.next()){
                  userInfo = new UserInfo();
                  userInfo.setEmail(rs.getString("EMAIL_ADDRESS"));
                  userInfo.setFirstName(rs.getString("FIRST_NAME"));
                  userInfo.setLastName(rs.getString("LAST_NAME"));
                  userInfo.setUserId(rs.getString("USER_ID"));

              }


        }  catch (Exception  e){
            e.printStackTrace();
        }  finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cll != null) {
                    cll.close();
                }
                if (rs != null) {
                    rs.close();
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }

    @PostMapping(value = "/InsertRecord" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> updateStudentData (@RequestBody Student std){

        Connection conn = null;
        OracleCallableStatement statement = null;
        ResultSet Rs = null;
        String Output = null;


          final String Response_Status = "success";
          final String Response_Message = "Record added Successfully";
          final  String Failure_status = "Failed";
          final String Failure_message =  "Failed to Add Record";

        SetStatus sts = new SetStatus();

        try{

            conn = Utilities.getConnection();
            conn = conn.unwrap(OracleConnection.class);
            statement = (OracleCallableStatement)conn.prepareCall("{?=call pr_student_record(?,?,?)}");
            statement.registerOutParameter(1, OracleTypes.VARCHAR);
            statement.setString(2,std.getUserName());
            statement.setString(3,std.getFirstName());
            statement.setString(4,std.getLastName());

            statement.execute();

            Output = (String) statement.getObject(1);


            if(Output.equalsIgnoreCase("SUCCESS")){

                sts.setStatus(Response_Status);
                sts.setMessage(Response_Message);

            }else {
                sts.setStatus(Failure_status);
                sts.setMessage(Failure_message);
            }

        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(conn!=null){
                    conn.close();
                }
                if(statement!=null){
                    statement.close();
                }
                if(Rs!=null){
                    Rs.close();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
         return  ResponseEntity.status(HttpStatus.OK).body(sts);
    }

}
