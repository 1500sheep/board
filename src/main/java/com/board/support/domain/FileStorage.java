package com.board.support.domain;

import com.board.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    public FileInfo upload(MultipartFile file);
}
