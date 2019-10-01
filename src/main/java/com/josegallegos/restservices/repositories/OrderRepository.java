package com.josegallegos.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josegallegos.restservices.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
