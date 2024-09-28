package com.mbankingloan.mbankingloan.Feature.File.dto;


import lombok.Builder;

@Builder
public record FileResponse(
        String name,
        Long size,
        String extension,
        String contentType,
        String uri
) {
}