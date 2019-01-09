package com.board.support.domain;

import com.board.domain.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Slf4j
@Service
public class LocalFileUploadService implements FileStorage {


    //    @Value("${user.home}")
    private String home;

    //    @Value("${local.storage.path}")
    private String relativePath = "src/main/resources/static/images";

    private String absolutePath;

    public final static String DELEMITER = ".";

    @PostConstruct
    public void initPath() throws IOException {
        home = System.getProperty("user.dir");
        Path absolutePath = Paths.get(home + relativePath);
        log.debug("### {}", absolutePath);
        if (!Files.exists(absolutePath)) {
            Files.createDirectories(absolutePath);
        }
        this.absolutePath = absolutePath.toString();
    }

    public FileInfo upload(MultipartFile multipartFile) {
        try {
            String originFileName = multipartFile.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(originFileName).toLowerCase();
            String filename;
            String filepath;
            File file;
            do {
                filename = RandomStringUtils.randomAlphabetic(32) + DELEMITER + fileExtension;
                filepath = getFilePath(filename);
                file = new File(filepath);
            } while (file.exists());

            file.getParentFile().mkdirs();
            multipartFile.transferTo(file);

            return FileInfo.builder()
                    .fileOriginName(originFileName)
                    .fileName(filename)
                    .fileUrl(filepath)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        String filePath = getFilePath(multipartFile.getOriginalFilename());
        File file = new File(filePath);
        log.info("convert filename : " + filePath);
        if (file.createNewFile()) {
            copyFileStream(file, multipartFile.getInputStream());
        }
        return file;
    }

    private void copyFileStream(File target, InputStream input) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(target)) {
            IOUtils.copy(input, fos);
        } catch (IOException e) {
            Files.deleteIfExists(Paths.get(target.getAbsolutePath()));
        }
    }

    private String getFilePath(String fileName) {
        return String.join("/", home, relativePath, fileName);
    }

    public Resource asResource(String fileName) throws MalformedURLException, FileNotFoundException {
        Path file = Paths.get(this.absolutePath).resolve(fileName);
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return resource;
    }

}
