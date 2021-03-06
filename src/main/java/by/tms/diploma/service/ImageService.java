package by.tms.diploma.service;

import by.tms.diploma.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image upload(MultipartFile file, String entityName, long entityId) throws IOException;
}
