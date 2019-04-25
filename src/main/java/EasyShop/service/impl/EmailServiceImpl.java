package EasyShop.service.impl;

import EasyShop.dto.UserDTO;
import EasyShop.service.EmailService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage( UserDTO userDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userDTO.getEmail());
        message.setSubject("Welcome to Easy Shop!");
        message.setText("Welcome to Easy Shop platform, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!\n " +
                        "You can connect using your email address: " + userDTO.getEmail() + ".\n " +
                        "Follow this link: http://localhost:4200/authentication to login!\n " +
                        "Have a nice day!\n Easy Shop Team");
        emailSender.send(message);
    }
}
