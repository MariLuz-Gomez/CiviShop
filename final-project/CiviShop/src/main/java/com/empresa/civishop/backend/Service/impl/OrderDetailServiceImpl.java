package com.empresa.civishop.backend.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;
import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetailId;
import com.empresa.civishop.backend.Repository.OrderDetailRepository;
import com.empresa.civishop.backend.Service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(OrderDetailId  id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(OrderDetailId  id) {
        orderDetailRepository.deleteById(id);
    }
}
