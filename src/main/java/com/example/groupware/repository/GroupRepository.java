package com.example.groupware.repository;

import com.example.groupware.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<User, Long>{
    
    //public void saveUser(User user);

    public User findUserByEmail(String email);

    public User findById(String id);
	// @Query("select t from Member t where name=:name and age < :age")
	// List<Member> findByNameAndAgeLessThanSQL(@Param("name") String name, @Param("age") int age);
}
