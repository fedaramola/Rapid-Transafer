package com.ecobank.rapidtransfer.controller;

import com.ecobank.rapidtransfer.obj.Staff;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    //xml and json
    @GetMapping("/staff")
    public ResponseEntity<?> rest(){

        List<Staff> staffList = new ArrayList<>();

        Staff staff = new Staff();
        staff.setStaffId(121122121);
        staff.setName("Enny");
        staff.setDept("QA");

        Staff staff2 = new Staff();
        staff2.setStaffId(686788);
        staff2.setName("Joel");
        staff2.setDept("Sofyt");

        staffList.add(staff);
        staffList.add(staff2);



        return ResponseEntity.status(HttpStatus.OK).body(staffList);
    }
}
