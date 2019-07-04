package com.bs.library.order;


import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOs(List<Order> order);

    Order toOrder(OrderDTO orderDTO);
}
