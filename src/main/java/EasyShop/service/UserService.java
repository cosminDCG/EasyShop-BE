package EasyShop.service;

import EasyShop.dto.UserDTO;

import java.util.List;

public interface UserService {

    Boolean registerUser(UserDTO userDTO);

    UserDTO login( String email, String password);

    UserDTO getUserByEmail(String email);

    Boolean updateUser(UserDTO userDTO);

    Boolean insertProfilePicture(String path, int id);

    UserDTO getUserById(int id);

    Boolean deleteUserById(int user_id);

    List<UserDTO> getAllUsers();

    Boolean changePassword(String email, String password, String newPassword);

    Boolean recoverAccount(String email, String newPass);

    Boolean updateRole(int user_id, String role);

    List<UserDTO> getAllReps();
}
