package EasyShop.controllers;

import EasyShop.dto.UserDTO;
import EasyShop.service.EmailService;
import EasyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO){

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Boolean ok =  userService.registerUser(userDTO);
        if (ok == true) {
            emailService.sendSimpleMessage(userDTO);
            return new ResponseEntity(ok, HttpStatus.OK);
        }
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity login (@RequestParam String email, @RequestParam String password) {
        UserDTO userDTO = userService.login(email, password);
        if (userDTO == null) {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        } else {
            Authentication request = new UsernamePasswordAuthenticationToken( email, password );
            Authentication result = authenticationManager.authenticate( request );
            SecurityContextHolder.getContext().setAuthentication( result );
            return new ResponseEntity(userDTO, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/password/change", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestParam String email, @RequestParam String password, @RequestParam String newPassword) {
        Boolean ok = userService.changePassword(email, password, newPassword);
        return new ResponseEntity(ok, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResponseEntity updateUser (@RequestBody UserDTO userDTO){
        Boolean ok = userService.updateUser(userDTO);
        if (ok == true){
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/user/signout", method = RequestMethod.GET)
    public ResponseEntity logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity getUserById(@RequestParam int id){
        UserDTO userDTO = userService.getUserById(id);
        if(userDTO == null)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public ResponseEntity deleteUserById(@RequestParam int id){
        Boolean ok = userService.deleteUserById(id);
        if(ok == true)
            return new ResponseEntity(ok, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        List<UserDTO> userDTOList = userService.getAllUsers();
        if (userDTOList != null)
            return new ResponseEntity(userDTOList, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

}
