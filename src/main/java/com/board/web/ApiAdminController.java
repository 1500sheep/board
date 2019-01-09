package com.board.web;

import com.board.domain.User;
import com.board.security.LoginUser;
import com.board.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.RestResponse;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class ApiAdminController {

    @Resource(name = "adminService")
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<RestResponse> getDataByPage(@LoginUser User adminUser, @PageableDefault(page = 0, size = 5) Pageable pageable, @RequestParam(name = "selectedType") String selectedType) {
        // generic type으로 하는 것이 맞을까? 아니면 controller에서 확인 후 반환하는 것이 맞을까?
//        if (selectedType.equals("user")) {
//
//        } else if (selectedType.equals("post")) {
//
//        } else if (selectedType.equals("reply")) {
//
//        }

        return ResponseEntity.ok(RestResponse.success(adminService.getDataByPage(adminUser, pageable, selectedType)));
    }

    @DeleteMapping("/{dataId}")
    public ResponseEntity<Void> deleteData(@LoginUser User adminUser, @PathVariable Long dataId, @RequestParam(name = "selectedType") String selectedType) {
        adminService.deleteData(adminUser, dataId, selectedType);
        return ResponseEntity.ok().build();
    }
}
