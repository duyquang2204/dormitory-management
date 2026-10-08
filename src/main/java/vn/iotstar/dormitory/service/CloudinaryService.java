package vn.iotstar.dormitory.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name:}")
    private String cloudName;

    public String uploadImage(MultipartFile file) {
        return uploadImage(file, null);
    }

    public String uploadImage(MultipartFile file, String prefix) {
        try {
            if (cloudName != null && !cloudName.trim().isEmpty()) {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                return uploadResult.get("url").toString();
            } else {
                String uploadDirStr = "uploads/";
                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDirStr);
                if (!java.nio.file.Files.exists(uploadPath)) {
                    java.nio.file.Files.createDirectories(uploadPath);
                }
                
                String extension = "";
                String originalName = file.getOriginalFilename();
                if (originalName != null && originalName.lastIndexOf(".") > 0) {
                    extension = originalName.substring(originalName.lastIndexOf("."));
                }
                
                String filename;
                if (prefix != null && !prefix.isEmpty()) {
                    filename = prefix + "_" + java.util.UUID.randomUUID().toString().substring(0, 5) + extension;
                } else {
                    filename = java.util.UUID.randomUUID().toString() + "_" + originalName;
                }
                
                // Remove invalid characters to ensure safe saving
                filename = filename.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

                java.nio.file.Path filePath = uploadPath.resolve(filename);
                java.nio.file.Files.copy(file.getInputStream(), filePath);
                return "/uploads/" + filename;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
