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


    @Override
    public Image upload(MultipartFile file, String entityName, long entityId) throws IOException {
        Image image = new Image();
        byte[] bytes = file.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);
//        Map public_id = cloudinary.uploader().upload("data:"+file.getContentType()+";base64," +base64,
//                ObjectUtils.asMap(
//                            "folder", entityName,
//                                   "public_id", entityName+"_"+entityId+"_"+file.getOriginalFilename()));
//        image.setUrl((String)public_id.get("url"));
        image.setUrl("https://clck.ru/UM7kv");
        return image;
    }
}
