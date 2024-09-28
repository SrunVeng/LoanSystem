package com.mbankingloan.mbankingloan.Feature.File;

import com.mbankingloan.mbankingloan.Feature.File.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;


    @PostMapping(value = "/upload/profileImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResponse upload(@RequestPart MultipartFile files, Principal principal) throws IOException {
        String toProfile = principal.getName();
        return fileService.upload(files,toProfile);
    }


}
