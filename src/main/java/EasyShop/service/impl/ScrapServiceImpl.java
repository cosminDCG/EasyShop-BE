package EasyShop.service.impl;

import EasyShop.configuration.SeleniumConfigurations;
import EasyShop.dao.ItemDAO;
import EasyShop.dao.ScrapDAO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.ItemPropertiesDTO;
import EasyShop.service.ScrapService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrapServiceImpl implements ScrapService {

    private String DASHBOARD_FOLDER = "F:\\EasyShop-FE\\src\\assets\\dashboard";

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private ScrapDAO scrapDAO;

    public void saveImageFromUrl(String imageUrl, int imageName) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        TrustManager[] trustManager = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certificate, String str) {}
                    public void checkServerTrusted(X509Certificate[] certificate, String str) {}
                }
        };
        context.init(null, trustManager, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(DASHBOARD_FOLDER + "/item" + imageName + ".jpg");

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public List<ItemDTO> getItemsFromEmag(String page){
        List<ItemDTO> itemDTOList = new ArrayList<>();
        Document document;
        String shop = page.split("\\.")[1];
        int imageCount = itemDAO.getItemCount() + 1;

        try{
            document = Jsoup.connect(page).get();
            String category = document.select("span.title-phrasing-xl").get(0).text().split("\\s+")[0];

            Elements links = document.select("a.js-product-url").select("a[href]");
            
            List<String> urls = new ArrayList<>();
            for(int i = 0; i < links.size(); i++){
                urls.add(links.get(i).attr("href"));
            }
            for (int i = 1; i<urls.size()-1; i++) {
                if (urls.get(i).contains("reviews-section")) {
                    urls.remove(i);
                }
            }
            urls = urls.stream().distinct().collect(Collectors.toList());

            for (String url : urls) {
                ItemDTO newItem = new ItemDTO();
                List<ItemPropertiesDTO> newItemProperties = new ArrayList<>();
                document = Jsoup.connect(url).get();
                Elements propertyName = document.select("td.col-xs-4");
                Elements propertyDescription = document.select("td.col-xs-8");
                for(int i = 0; i < propertyName.size(); i++){
                    ItemPropertiesDTO itemPropertiesDTO = new ItemPropertiesDTO();
                    itemPropertiesDTO.setItemId(imageCount);
                    itemPropertiesDTO.setName(propertyName.get(i).text());
                    itemPropertiesDTO.setDescription(propertyDescription.get(i).text());
                    newItemProperties.add(itemPropertiesDTO);
                }
                String title = document.select("h1").get(0).text();
                String price = document.select("p.product-new-price").get(0).text();
                String priceToSave = price.substring(0, price.length()-6) + "," + price.substring(price.length()-6);
                String[] description = document.select("div#description-body").text().split("\\.");
                String descToSave;
                if (description.length < 5){
                    descToSave = "";
                }
                else descToSave = description[0] + ". " + description[1] + ". " + description[2] + ". " + description[3] + ". " + description[4] + ".";
                String photo = document.select("a.product-gallery-image").select("img[src]").get(0).attr("src");
                newItem.setName(title);
                if(descToSave.length() >= 999)
                    descToSave = descToSave.substring(0,999);
                newItem.setDescription(descToSave);
                newItem.setCategory(category);
                newItem.setPrice(priceToSave);
                newItem.setShop(shop);
                newItem.setPhoto("item" + Integer.toString(imageCount) + ".jpg");
                newItem.setProperties(newItemProperties);
                itemDTOList.add(newItem);
                saveImageFromUrl(photo, imageCount);
                imageCount ++;
            }

        }catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return  itemDTOList;
    }

    public List<ItemDTO> getItemsFromCarrefour(String page) throws InterruptedException, MalformedURLException {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        int imageCount = itemDAO.getItemCount() + 1;
        String shop = "Carrefour";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "F:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe"
        );
        WebDriver driver = new PhantomJSDriver(caps);
        driver.get(page);
        String sourceHtml = driver.getPageSource();
        Element document = Jsoup.parseBodyFragment(sourceHtml);
        Elements links;
        List<String> urls = new ArrayList<>();
        if(page.contains("supermarket")) {
            links = document.select("a.product-name").select("a[href]");
            for(int i = 0; i < links.size(); i++){
                urls.add("https://carrefour.ro" + links.get(i).attr("href"));
            }
        }
        else {
            links = document.select("a.product-item-link").select("a[href]");
            for(int i = 0; i < links.size(); i++){
                urls.add(links.get(i).attr("href"));
            }
        }

        for(String url : urls) {
            ItemDTO newItem = new ItemDTO();
            List<ItemPropertiesDTO> newItemProperties = new ArrayList<>();
            WebDriver driverItems = new PhantomJSDriver(caps);
            driverItems.get(url);
            sourceHtml = driverItems.getPageSource();
            document = Jsoup.parseBodyFragment(sourceHtml);
            Elements propertyName = document.select("th.col").select("th.label");
            Elements propertyDescription = document.select("td.col").select("td.data");
            for(int i = 0; i < propertyName.size(); i++){
                ItemPropertiesDTO itemPropertiesDTO = new ItemPropertiesDTO();
                itemPropertiesDTO.setItemId(imageCount);
                itemPropertiesDTO.setName(propertyName.get(i).text());
                itemPropertiesDTO.setDescription(propertyDescription.get(i).text());
                newItemProperties.add(itemPropertiesDTO);
            }

            String title = document.select("h1").get(0).text();
            String category = null;
            String price = null;
            String[] description = null;
            String photo = null;
            if(page.contains("supermarket")) {
                category = document.select("li.ng-scope").select("a.ng-binding").last().text().split(" ")[0];
                price = document.select("h3").get(0).text();
                description = document.select("div#product-description").select("p").text().split("\\.");
                photo = document.select("img[src].zoomImg").get(0).attr("src");

            } else {
                price = document.select("span.price").get(0).text();
                description = document.select("div.value").text().split("\\.");
                photo = document.select("img[src].fotorama__img").get(0).attr("src");
                category = document.select("li.product").select("strong").text().split(" ")[0];
            }

            String descToSave;
            if (description.length < 5){
                descToSave = "";
            }
            else descToSave = description[0] + ". " + description[1] + ". " + description[2] + ". " + description[3] + ". " + description[4] + ".";
            newItem.setName(title);
            newItem.setDescription(descToSave);
            newItem.setCategory(category);
            newItem.setPrice(price);
            newItem.setShop(shop);
            newItem.setPhoto("item" + Integer.toString(imageCount) + ".jpg");
            newItem.setProperties(newItemProperties);
            itemDTOList.add(newItem);
            try {
                saveImageFromUrl(photo, imageCount);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            imageCount ++;
            
            driverItems.close();
            if(itemDTOList.size() == 20)
                break;
        }

        driver.close();
        return itemDTOList;
    }


    public List<ItemDTO> getItemsFromAuchan(String page){
        List<ItemDTO> itemDTOList = new ArrayList<>();
        Document document;
        String shop = "Auchan";
        int imageCount = itemDAO.getItemCount() + 1;

        try{
            document = Jsoup.connect(page).validateTLSCertificates(false).get();

            String category = document.select("span.last").get(0).text();
            Elements links = document.select("a.productMainLink").select("a[href]");

            List<String> urls = new ArrayList<>();
            for(int i = 0; i < links.size(); i++){
                urls.add(links.get(i).attr("href"));
            }

            urls = urls.stream().distinct().collect(Collectors.toList());

            for(String url : urls){
                ItemDTO newItem = new ItemDTO();
                List<ItemPropertiesDTO> newItemProperties = new ArrayList<>();
                document = Jsoup.connect("https://www.auchan.ro" + url).validateTLSCertificates(false).get();
                Elements properties = document.select("span.col-xs-12");
                for(int i = 0; i < properties.size(); i = i + 2){
                    ItemPropertiesDTO itemPropertiesDTO = new ItemPropertiesDTO();
                    itemPropertiesDTO.setItemId(imageCount);
                    itemPropertiesDTO.setName(properties.get(i).text());
                    itemPropertiesDTO.setDescription(properties.get(i+1).text());
                    newItemProperties.add(itemPropertiesDTO);
                }

                String title = document.select("h1").get(0).text();
                String price = document.select("div.big-price").select("p.big-price-multiple").get(0).attr("data-price").replace(".", ",") + " Lei";
                String[] description = document.select("h2").get(0).text().split("\\.");
                String descToSave;
                if (description.length < 5){
                    descToSave = "";
                }
                else descToSave = description[0] + ". " + description[1] + ". " + description[2] + ". " + description[3] + ". " + description[4] + ".";

                if(descToSave.length() >= 999)
                    descToSave = descToSave.substring(0,999);

                String photo = "https://www.auchan.ro" + document.select("img[src].imageLink").get(0).attr("src");
                newItem.setName(title);
                newItem.setDescription(descToSave);
                newItem.setCategory(category);
                newItem.setPrice(price);
                newItem.setShop(shop);
                newItem.setPhoto("item" + Integer.toString(imageCount) + ".jpg");
                newItem.setProperties(newItemProperties);
                itemDTOList.add(newItem);
                saveImageFromUrl(photo, imageCount);
                imageCount ++;


                if(itemDTOList.size() == 20)
                    break;
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return itemDTOList;
    }

    public void saveLink(String page){
        scrapDAO.saveLink(page);
    }

    public List<String> getAllLinks(){
        return scrapDAO.getAllLinks();
    }
    
}
