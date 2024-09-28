package com.mbankingloan.mbankingloan.Mapper;



import com.mbankingloan.mbankingloan.Domain.File;
import com.mbankingloan.mbankingloan.Feature.File.dto.FileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    File fromFileResponse(FileResponse fileResponse);
    FileResponse toFileResponse(File file);


}
