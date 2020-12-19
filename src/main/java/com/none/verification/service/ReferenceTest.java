package com.none.verification.service;

import com.none.verification.pojo.UserDTO;

/**
 * @Author wyn
 * @Date 2020/12/3
 * @Description TODO
 */
public class ReferenceTest {
    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setName("none");
        System.out.println("修改前值" + userDTO.getName());
        updateUser2(userDTO);
        System.out.println("修改后值" + userDTO.getName());
    }

    public static void updateUser(UserDTO  u) {
        u.setName("修改");
    }

    public static void updateUser2(UserDTO  u) {
        u = new UserDTO();
        u.setName("修改2");
    }
}
