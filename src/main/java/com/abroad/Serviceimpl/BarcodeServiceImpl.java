package com.abroad.Serviceimpl;

import com.abroad.JWT.JwtUtil;
import com.abroad.Service.BarcodeService;
import com.abroad.Service.EnquiryService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class BarcodeServiceImpl implements BarcodeService {

    private static final String HR_INQUIRY_BASE_URL =
            "https://pjsofttech.in/hrleadformqrcode?";

    @Autowired
    private StaffService staffService;

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public byte[] generateHrInquiryQr(String role, String email)
            throws AccessDeniedException {

        try {

            if (!staffService.hasPermission(role, email, "GET")) {
                throw new AccessDeniedException("You don't have permission");
            }

            String branchCode = staffService.fetchBranchCodeByRole(role, email);

            if (branchCode == null || branchCode.isEmpty()) {
                throw new RuntimeException("Branch code not found");
            }

            String instituteEmail =
                    staffService.getInstituteEmailByBranchCode(branchCode);

            if (instituteEmail == null || instituteEmail.isEmpty()) {
                throw new RuntimeException("Institute email not found");
            }

            String encodedInstituteEmail =
                    URLEncoder.encode(instituteEmail, StandardCharsets.UTF_8);

            String encodedBranchCode =
                    Base64.getUrlEncoder()
                            .encodeToString(branchCode.getBytes(StandardCharsets.UTF_8));

            Map<String, Object> claims = new HashMap<>();
            claims.put("role", role.toUpperCase());
            claims.put("branchCode", encodedBranchCode);

            String jwt = jwtUtil.generateTokenWithClaims(
                    email,
                    claims,
                    Duration.ofDays(90)
            );

            String qrData = HR_INQUIRY_BASE_URL
                    + "token=" + jwt
                    + "&instituteEmail=" + encodedInstituteEmail;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    qrData,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return outputStream.toByteArray();

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generating QR Code", e);
        }
    }
}