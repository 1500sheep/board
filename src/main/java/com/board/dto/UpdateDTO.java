package com.board.dto;

import com.board.exception.UnAuthenticationException;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import utils.ValidateRegex;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDTO {

    @NotNull
    @Pattern(regexp = ValidateRegex.USERNAME)
    private String name;

    @NotNull
    @Pattern(regexp = ValidateRegex.PHONE)
    private String phoneNumber;

    private MultipartFile file;

    public boolean checkFileIsImage() {

        if (file == null) {
            return false;
        }

        try (InputStream input = file.getInputStream()) {
            try {
                ImageIO.read(input).toString();
            } catch (Exception e) {
                throw UnAuthenticationException.invalidFile("이미지 파일이 아닙니다.");
            }
        } catch (IOException e) {
            throw UnAuthenticationException.invalidFile("이미지 파일이 아닙니다.");
        }

        return true;
    }

}
