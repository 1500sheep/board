package com.board.web;

import com.board.domain.FileInfo;
import com.board.domain.FileInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@Slf4j
public class FileController {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @RequestMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("download file working");

        request.setCharacterEncoding("UTF-8");
        FileInfo fileInfo = fileInfoRepository.findById(fileId).orElseThrow(EntityNotFoundException::new);

        log.info("fileInfo : " + fileInfo);
        //파일 업로드된 경로
        try {
            String fileUrl = fileInfo.getFileUrl();

            //실제 내보낼 파일명
            String oriFileName = fileInfo.getFileOriginName();
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";

            //파일을 읽어 스트림에 담기
            try {
                file = new File(fileUrl);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }

            client = request.getHeader("User-Agent");

            //파일 다운로드 헤더 지정
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");

            if (!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                log.info("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();
            os.close();
        } catch (Exception e) {
            log.error("ERROR : " + e.getMessage());
        }
    }
}
