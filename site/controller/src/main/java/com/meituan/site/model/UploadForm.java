package com.meituan.site.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadForm {
    MultipartFile[] files;

}
