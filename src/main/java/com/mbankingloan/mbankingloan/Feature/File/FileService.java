package com.mbankingloan.mbankingloan.Feature.File;

import com.mbankingloan.mbankingloan.Feature.File.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileResponse upload(MultipartFile files, String toProfile) throws IOException;
}
