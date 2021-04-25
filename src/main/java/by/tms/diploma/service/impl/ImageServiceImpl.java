package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Image;
import by.tms.diploma.service.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    public Image upload(List<MultipartFile> files, String entityName, long entityId) throws IOException {
        Image image = new Image();
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
//            Map public_id = cloudinary.uploader().upload("data:"+file.getContentType()+";base64," +base64,
//                    ObjectUtils.asMap(
//                            "folder", entityName,
//                                   "public_id", entityName+"_"+entityId+"_"+file.getOriginalFilename()));
//            urls.add((String)public_id.get("url"));
            urls.add("https://clck.ru/UM7kv");
        }
        image.setUrls(urls);
        return image;
    }
}
