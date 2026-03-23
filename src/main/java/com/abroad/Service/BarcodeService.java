package com.abroad.Service;

import java.nio.file.AccessDeniedException;

public interface BarcodeService {

    byte[] generateHrInquiryQr(String role, String email)
            throws AccessDeniedException;
}