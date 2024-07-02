package com.eventhive.eventHive.Voucher;

import com.eventhive.eventHive.Response.Response;
import com.eventhive.eventHive.Voucher.Service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> validateVoucher(@PathVariable Long id){
        return Response.successResponse("Voucher is valid", voucherService.validateVoucher(id));
    }
}
