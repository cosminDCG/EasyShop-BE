package EasyShop.service.impl;

import EasyShop.dao.UserDAO;
import EasyShop.dto.UserDTO;
import EasyShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean registerUser(UserDTO userDTO) {

        UserDTO check = userDAO.getUserByEmail(userDTO.getEmail());
        if (check != null){
            return false;
        } else{
            userDAO.registerUser(userDTO);
            return true;
        }

    }

    public UserDTO login (String email, String password) {

        UserDTO userDTO = userDAO.getUserByEmail(email);
        if (userDTO == null) {
            return null;
        } else if (!passwordEncoder.matches(password, userDTO.getPassword())){
                        return null;
        } else return userDTO;
    }

    public Boolean updateUser(UserDTO userDTO){

        userDAO.updateUser(userDTO);
        return true;
    }

    public Boolean insertProfilePicture(String path, int id) {

        userDAO.insertProfilePicture(path, id);
        return true;
    }

    public UserDTO getUserById(int id){
        return userDAO.getUserById(id);
    }

    public Boolean deleteUserById(int user_id){
        userDAO.deleteUserById(user_id);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserDTO user = userDAO.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String password = user.getPassword();
        int id = user.getId();
        return new org.springframework.security.core.userdetails.User(email, password,
                auth);
    }
}
