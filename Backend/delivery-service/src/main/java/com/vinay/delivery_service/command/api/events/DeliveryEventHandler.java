package com.vinay.delivery_service.command.api.events;

import com.vinay.common_service.events.OrderDeliveryEvent;
import com.vinay.delivery_service.command.api.data.Delivery;
import com.vinay.delivery_service.command.api.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DeliveryEventHandler {

    private final DeliveryRepository deliveryRepository;

    @EventHandler
    public void on(OrderDeliveryEvent event) {
        Delivery delivery = new Delivery();
        BeanUtils.copyProperties(event, delivery);
        deliveryRepository.save(delivery);      // We are simply save Delivery in the Database using DeliveryEventHandler.
    }


}
