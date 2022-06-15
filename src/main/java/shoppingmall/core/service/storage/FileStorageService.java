package shoppingmall.core.service.storage;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.core.service.storage.StorageService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor
@Service
public class FileStorageService implements StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String basePath;

    @Override
    public void init(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to create directory : " + path);
        }
    }

    @Override
    public String store(String uploadPath, MultipartFile file) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("File is empty");
            }

            Path dirPath = Paths.get(basePath + uploadPath);
            System.out.println("dirPath = " + dirPath);
            if (!Files.exists(dirPath)) {
                init(dirPath);
            }

            String filePath = dirPath + "/" + getUploadFileName(file.getOriginalFilename());
            System.out.println("filePath = " + filePath);
            file.transferTo(new File(filePath));

            return filePath;
        } catch (Exception e) {
            throw new Exception("Fail to upload file");
        }
    }

    private String getUploadFileName(String fileFullName) {
        System.out.println("fileFullName = " + fileFullName);
        return fileFullName.substring(0, fileFullName.lastIndexOf("."))
                + "_" + System.currentTimeMillis()
                + fileFullName.substring(fileFullName.lastIndexOf("."));
    }

    @Override
    public Resource load(String filePath) throws Exception {
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath);
            if(!resource.exists()) {
                throw new Exception("Cannot read file : " + filePath);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new Exception("Cannot read file : " + filePath);
        }
    }

    @Override
    public boolean delete(String filePath) throws IOException {
        return Files.deleteIfExists(Paths.get(filePath));
    }
}