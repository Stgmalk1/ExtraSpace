
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author xhunter
 */
public class FileUpload { 
    private Part uploadedFile;
    private String folder = "c:\\files";

    public Part getUploadedFile() {
    return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
    this.uploadedFile = uploadedFile;
    }


    public void saveFile(){

    try (InputStream input = uploadedFile.getInputStream()) {
    String fileName = uploadedFile.getSubmittedFileName();
            Files.copy(input, new File(folder, fileName).toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
