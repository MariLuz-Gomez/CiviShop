package com.empresa.civishop.backend.Service;

import java.util.List;
import java.util.Optional;

import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetail;
import com.empresa.civishop.backend.Entity.OrderDetail.OrderDetailId;

public interface OrderDetailService {
    List<OrderDetail> findAll();
    Optional<OrderDetail> findById(OrderDetailId  id);
    OrderDetail save(OrderDetail orderDetail);
    void deleteById(OrderDetailId  id);
}
