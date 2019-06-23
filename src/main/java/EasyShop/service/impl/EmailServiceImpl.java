package EasyShop.service.impl;

import EasyShop.dao.UserDAO;
import EasyShop.dto.*;
import EasyShop.service.EmailService;
import EasyShop.service.ItemService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.MalformedURLException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public UserDAO userDAO;

    @Autowired
    private ItemService itemService;

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

    public void sendOrderEmail(OrderDTO orderDTO) throws MessagingException {
        createInvoice(orderDTO);
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        UserDTO userDTO = userDAO.getUserById(orderDTO.getUserId());
        helper.setTo(userDTO.getEmail());
        helper.setSubject("Your Easy Shop Order");
        helper.setText("Hello, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!\n \n" +
                        "Here you have attached the bill with all the details about your last order on Easy Shop!\n \n" +
                        "If you have any question, don't hesitate to contact us." +
                        "Thank you for your order!\n\n" +
                        "Have a nice day!\n Easy Shop Team");

        FileSystemResource file
                = new FileSystemResource(new File("F:\\EasyShop-BE\\src\\main\\resources\\Invoice\\Invoice_" + orderDTO.getId() + ".pdf"));
        helper.addAttachment("Bill_" + userDTO.getFirstName() + "_" + userDTO.getLastName() + ".pdf", file);
        emailSender.send(message);
    }

    public void createInvoice(OrderDTO orderDTO){
        try {
            OutputStream file = new FileOutputStream(new File("F:\\EasyShop-BE\\src\\main\\resources\\Invoice\\Invoice_" + orderDTO.getId() + ".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            Image image = Image.getInstance ("F:\\EasyShop-FE\\src\\eS1.jpg");
            image.scaleAbsolute(120f, 60f);

            PdfPTable table=new PdfPTable(5);
            PdfPCell cell = new PdfPCell (new Paragraph("Order no." + orderDTO.getId() + " - " + orderDTO.getData()));

            cell.setColspan (5);
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setPadding (10.0f);

            table.addCell(cell);
            table.addCell("No.");
            table.addCell("Item");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Subtotal");
            int count = 1;
            for(ItemDTO itemDTO : orderDTO.getItems()){
                table.addCell(Integer.toString(count));
                count++;
                table.addCell(itemDTO.getName());
                table.addCell(Integer.toString(itemDTO.getQuantity()));
                table.addCell(itemDTO.getPrice());
                table.addCell(Float.toString(itemDTO.getQuantity()*itemService.convertPriceToFloat(itemDTO.getPrice())) + " Lei");
            }

            if(orderDTO.getPrice() < 300){
                table.addCell(Integer.toString(count));
                count++;
                table.addCell("Transport");
                table.addCell("1");
                table.addCell("30 Lei");
                table.addCell("30 Lei");
            }
            PdfPCell cellTotal = new PdfPCell (new Paragraph("TOTAL: " + orderDTO.getPrice() + " Lei"));
            cellTotal.setColspan (5);
            cellTotal.setHorizontalAlignment (Element.ALIGN_CENTER);
            cellTotal.setPadding (10.0f);
            table.addCell(cellTotal);
            table.setSpacingBefore(30.0f);
            table.setSpacingAfter(30.0f);

            document.open();
            document.add(image);
            document.add(new Paragraph("Customer: " + orderDTO.getDeliveryPerson()));
            document.add(new Paragraph("Customer address: " + orderDTO.getDeliveryAddress()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Billing person: " + orderDTO.getBillingPerson()));
            document.add(new Paragraph("Billing address: " + orderDTO.getBillingAddress()));
            document.add(table);
            document.close();

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
