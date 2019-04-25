package EasyShop.controllers;

import EasyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    private static String UPLOADED_FOLDER = "F:\\EasyShop-FE\\src\\assets\\";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/photo/upload", method = RequestMethod.POST)
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") int id) {

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            Boolean ok = userService.insertProfilePicture( file.getOriginalFilename(), id);
            if (ok == false)
                return new ResponseEntity(false,HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(file.getOriginalFilename(), HttpStatus.OK);
    }

}
