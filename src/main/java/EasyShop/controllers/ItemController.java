package EasyShop.controllers;

import EasyShop.dao.ItemDAO;
import EasyShop.dto.ItemDTO;
import EasyShop.dto.ItemPropertiesDTO;
import EasyShop.service.ItemPropertiesService;
import EasyShop.service.ItemService;
import EasyShop.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemPropertiesService itemPropertiesService;

    @Autowired
    private ScrapService scrapService;

    @RequestMapping(value = "/scrap", method = RequestMethod.POST)
    public ResponseEntity scrapPage(@RequestParam String page) throws InterruptedException, MalformedURLException {
        List<ItemDTO> itemDTOList;

        if (page.contains("emag"))
            itemDTOList = scrapService.getItemsFromEmag(page);
        else itemDTOList = scrapService.getItemsFromCarrefour(page);
        
        for(ItemDTO itemDTO : itemDTOList){
            itemService.insertItem(itemDTO);
            itemPropertiesService.insertProperties(itemDTO.getProperties());
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/all", method = RequestMethod.GET)
    public ResponseEntity getAllItems(){
        List<ItemDTO> itemDTOList = itemService.getAllItems();
        return new ResponseEntity(itemDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/categories", method = RequestMethod.GET)
    public ResponseEntity getAllCategories(){
        List<String> categories = itemService.getAllCategories();
        return new ResponseEntity(categories, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/properties", method = RequestMethod.GET)
    public ResponseEntity getPropertiesByProductId(@RequestParam int id){
        List<ItemPropertiesDTO> itemPropertiesDTOList = itemPropertiesService.getPropertiesByProductId(id);
        return new ResponseEntity(itemPropertiesDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/cheapest", method = RequestMethod.POST)
    public ResponseEntity getCheapestChoices(@RequestBody List<String> criterias) {

        List<ItemDTO> itemDTOList = new ArrayList<>();
        for(String criteria : criterias){
            itemDTOList.add(itemService.getCheapestChoice(criteria));
        }

        return new ResponseEntity(itemDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/shops", method = RequestMethod.GET)
    public ResponseEntity getAllShops() {
        List<String> shops = itemService.getAllShops();
        return new ResponseEntity(shops, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/single", method = RequestMethod.POST)
    public ResponseEntity getCheapestSinglePlace(@RequestBody List<String> criterias) {

        List<String> shops = itemService.getAllShops();
        List<ItemDTO> result = new ArrayList<>();
        float minim = Float.MAX_VALUE;

        for(String shop : shops){
            List<ItemDTO> currentShopItems = new ArrayList<>();
            for ( String criteria : criterias){
                currentShopItems.add(itemService.getCheapestSinglePlace(criteria, shop));
            }
            if(itemService.getTotalPriceFromList(currentShopItems) <= minim){
                result = currentShopItems;
            }
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
