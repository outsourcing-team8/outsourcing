package com.sparta.outsourcing.domain.menu.repository;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MenuRepository extends JpaRepository<Menu, Long> {

}
