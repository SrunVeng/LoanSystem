package com.mbankingloan.mbankingloan.Feature.File;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.File;
import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.File.dto.FileResponse;
import com.mbankingloan.mbankingloan.Mapper.FileMapper;
import com.mbankingloan.mbankingloan.Util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final FileUploadProperties fileUploadProperties;
    private final FileUtil fileUtil;

    @Value("${file-server.server-path}")
    private String serverPath;

    @Value("${file-server.base-uri}")
    private String baseUri;


    @Override
    @Transactional
    public FileResponse upload(MultipartFile file, String toProfile) throws IOException {
        // Get the allowed extensions and max size from the properties
        List<String> allowedExtensions = fileUploadProperties.getAllowedExtensions();
        long maxSize = fileUtil.parseSize(fileUploadProperties.getMaxSize());

        // Extract extension and validate against the allowed list
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert extension != null;
        if (!allowedExtensions.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("Only " + allowedExtensions + " files are allowed.");
        }

        // Validate file size
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("File size exceeds the maximum allowed size of " + fileUploadProperties.getMaxSize() + ".");
        }

        // Generate new file name and save the file
        String newName = FileUtil.generateFileName(file.getOriginalFilename());
        Path path = Path.of(serverPath + newName);
        Files.copy(file.getInputStream(), path);

            // Create and return the response
            FileResponse response = FileResponse.builder()
                    .name(newName)
                    .size(file.getSize())
                    .extension(extension)
                    .contentType(file.getContentType())
                    .uri(baseUri + newName)
                    .build();


        File saved = fileMapper.fromFileResponse(response);
        Customer customer =customerRepository.findByphoneNumber(toProfile);
        User user = userRepository.findByStaffId(toProfile);

        if( customer != null) {
            saved.setCustomer(customerRepository.findByphoneNumber(toProfile));
            customer.setProfileImageUrl(baseUri + newName);
            customer.setFile(saved);
        }
        if(user != null) {
            saved.setUser(userRepository.findByStaffId(toProfile));
            user.setProfileImageUrl(baseUri + newName);
            user.setFile(saved);
        }

        fileRepository.save(saved);

        return response;
    }

}
