package com.bs.library.mapper;


import com.bs.library.dto.OrderDTO;
import com.bs.library.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOs(List<Order> order);

    Order toOrder(OrderDTO orderDTO);
}
