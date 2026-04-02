package com.abroad.Controller;

import com.abroad.Service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "https://pjsofttech.in",
        "https://wayabroad.in"
})
@RestController
@RequestMapping("/barcode")
public class BarcodeController {

    @Autowired
    private BarcodeService barcodeService;

    @GetMapping("/createHRInquiryBarcode")
    public ResponseEntity<byte[]> generateInquiryQRCode(
            @RequestParam String role,
            @RequestParam String email) {

        try {

            byte[] qrImage = barcodeService.generateHrInquiryQr(role, email);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setCacheControl(CacheControl.noCache());

            return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("QR generation failed: " + e.getMessage()).getBytes());
        }
    }
}