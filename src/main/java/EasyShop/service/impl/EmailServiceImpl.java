package EasyShop.service.impl;

import EasyShop.dao.UserDAO;
import EasyShop.dto.BanDTO;
import EasyShop.dto.PromoDTO;
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

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public UserDAO userDAO;

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

    public void sendBanMessage(BanDTO banDTO){

        UserDTO bannedBy = userDAO.getUserById(banDTO.getBannedBy());
        UserDTO bannedUser = userDAO.getUserById(banDTO.getBannedUser());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(bannedUser.getEmail());
        message.setSubject("Ban on Easy Shop!");
        message.setText("Hello, " + bannedUser.getFirstName() + " " + bannedUser.getLastName() + "!\n\n " +
                        "Unfortunately, you were banned by " + bannedBy.getFirstName() + " " + bannedUser.getLastName() + ".\n " +
                        "From this day until the end day of your ban you won't be able to use Easy Shop.\n " +
                        "Reason: " + banDTO.getReason() + "\n " +
                        "End Date of the ban: " + banDTO.getEndDate() + "\n " +
                        "For any qustion, send a mail to easyshopcontact01@gmail.com and one of our admins will reply you as soon as possible!\n \n" +
                        "Have a nice day!\n Easy Shop Team");

        emailSender.send(message);
    }

    public void sendPromoMessage(PromoDTO promoDTO){

        UserDTO userDTO = userDAO.getUserById(promoDTO.getUserId());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(userDTO.getEmail());
        message.setSubject("Promo code on Easy Shop!");
        message.setText("Hello, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!\n \n " +
                        "You just received a promo code on Easy Shop!\n" +
                        "It's value is " + promoDTO.getPromoPercent() + "% and you can use it whenever you want.\n " +
                        "Promo Code: " + promoDTO.getPromoCode() + "\n " +
                        "You can also see it in the promo section on your profile.\n \n " +
                        "Have a nice day!\n Easy Shop Team");

        emailSender.send(message);
    }

    public void sendRecoveryMessage(UserDTO userDTO, String newPass){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(userDTO.getEmail());
        message.setSubject("Easy Shop - Recover Your Account");
        message.setText("Hello, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!\n \n " +
                "We received a request for a new password for your account!\n" +
                "Your new password is: " + newPass + "\n " +
                "For security reasons, we recommend to change it as soon as possible!\n" +
                "Have a nice day!\n Easy Shop Team");

        emailSender.send(message);
    }
}
