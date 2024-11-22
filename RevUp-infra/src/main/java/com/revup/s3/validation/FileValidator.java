package com.revup.s3.validation;

import com.revup.s3.exception.FileInvalidNameException;
import com.revup.s3.exception.FileUnsupportedExtensionException;
import org.springframework.web.multipart.MultipartFile;

import static com.revup.s3.validation.FileConstant.ALLOWED_EXTENSIONS;

public class FileValidator {
    public static void validateFile(MultipartFile file) {

        String originalFileName = validateFileName(file);
        validateFileExtension(originalFileName);

    }

    private static String validateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new FileInvalidNameException();
        }
        return originalFileName;
    }

    private static void validateFileExtension(String originalFileName) {

        String fileExtension = getFileExtension(originalFileName);

        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new FileUnsupportedExtensionException();
        }


    }

    private static String getFileExtension(String filename) {
        int index = filename.lastIndexOf(".");

        if (index == -1 || index == filename.length() - 1) {
            throw new FileUnsupportedExtensionException();
        }

        return filename.substring(index + 1);
    }
}
