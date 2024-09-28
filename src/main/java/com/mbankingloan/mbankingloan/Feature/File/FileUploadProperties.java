package com.mbankingloan.mbankingloan.Feature.File;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {

    private String maxSize;
    private List<String> allowedExtensions;


}
