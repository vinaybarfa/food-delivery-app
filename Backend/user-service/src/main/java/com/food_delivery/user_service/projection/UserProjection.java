package com.food_delivery.user_service.projection;

import com.vinay.common_service.model.CardDetails;
import com.vinay.common_service.model.User;
import com.vinay.common_service.quries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
        // Ideally Get the details from the DB
        CardDetails cardDetails = CardDetails
                .builder()
                .name("Hari Om")
                .validUntilYear(2027)
                .validUntilMonth(7)
                .cardNumber("83829081")
                .cvv(111)
                .build();
        return User.builder()
                .userId(query.getUserId())
                .name("Hari")
                .email("hari@gmail.com")
                .password("Hari@123")
                .cardDetails(cardDetails)
                .build();
    }

}
