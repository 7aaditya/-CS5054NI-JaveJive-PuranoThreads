package com.puranothread.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.http.Part;

public class imageutil {
    
    // Directory where images will be stored (relative to the web application)
    private static final String UPLOAD_DIRECTORY = "product_images";
    
    /**
     * Saves an uploaded image to the server's file system
     * 
     * @param part The uploaded file part from multipart request
     * @param applicationPath The absolute path to the web application
     * @return The relative path to the saved image
     * @throws IOException If an error occurs during file saving
     */
    public static String saveImage(Part part, String applicationPath) throws IOException {
        // Create directory if it doesn't exist
        String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // Generate a unique filename to prevent overwriting
        String fileName = getUniqueFileName(part.getSubmittedFileName());
        
        // Save the file
        try (InputStream input = part.getInputStream();
             FileOutputStream output = new FileOutputStream(new File(uploadPath, fileName))) {
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
        
        // Return the relative path to the image
        return UPLOAD_DIRECTORY + "/" + fileName;
    }
    
    /**
     * Generates a unique filename for an uploaded file
     * 
     * @param originalFileName The original filename
     * @return A unique filename
     */
    private static String getUniqueFileName(String originalFileName) {
        // Extract file extension
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        
        // Generate a UUID and append the extension
        return UUID.randomUUID().toString() + extension;
    }
    
    /**
     * Deletes an image from the server's file system
     * 
     * @param imagePath The relative path to the image
     * @param applicationPath The absolute path to the web application
     * @return true if the image was deleted successfully, false otherwise
     */
    public static boolean deleteImage(String imagePath, String applicationPath) {
        try {
            Path path = Paths.get(applicationPath + File.separator + imagePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}