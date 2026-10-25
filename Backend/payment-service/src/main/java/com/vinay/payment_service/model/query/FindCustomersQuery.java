package com.vinay.payment_service.model.query;

public record FindCustomersQuery(
        int pageNo, int pageSize, String sortBy, String sortDir
) {
}
