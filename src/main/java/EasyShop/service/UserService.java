package EasyShop.service;

import EasyShop.dto.UserDTO;

public interface UserService {

    Boolean registerUser(UserDTO userDTO);

    UserDTO login( String email, String password);

    Boolean updateUser(UserDTO userDTO);

    Boolean insertProfilePicture(String path, int id);

    UserDTO getUserById(int id);

    Boolean deleteUserById(int user_id);
}
