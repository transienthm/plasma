package com.meituan.site.controller;

import com.meituan.site.model.UploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

@Controller
public class FileUploadController {
    private static String UPLOADED_FOLDER = "F://temp/";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String multiFileUpload(@ModelAttribute UploadForm form, RedirectAttributes redirectAttributes) {
        StringJoiner sj = new StringJoiner(",");

        for (MultipartFile file : form.getFiles()) {
            if (file.isEmpty()) {
                continue;
            }

            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                sj.add(file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String uploadedFileName = sj.toString();
        if (StringUtils.isEmpty(uploadedFileName)) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
        } else {
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + uploadedFileName + "'");
        }
        return "redirect:/uploadStatus";
    }

    @RequestMapping(value = "/uploadStatus", method = RequestMethod.GET)
    public String uploadStatus() {
        return "uploadStatus";
    }
}
