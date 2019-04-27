package EasyShop.dao;

import EasyShop.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    void registerUser(UserDTO user);

    UserDTO getUserByEmail(String userName);

    void updateUser(UserDTO userDTO);

    void insertProfilePicture(String path, int id);

    UserDTO getUserById(int id);

    void deleteUserById(int user_id);

    List<UserDTO> getAllUsers();
}
