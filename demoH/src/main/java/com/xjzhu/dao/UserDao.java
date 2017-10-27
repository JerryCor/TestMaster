package com.xjzhu.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xjzhu.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
}
