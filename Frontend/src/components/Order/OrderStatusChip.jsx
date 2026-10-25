import React from "react";

const getColor = (
    status
) => {

    switch (status) {

        case "PENDING":
            return "bg-yellow-500";

        case "CONFIRMED":
            return "bg-blue-500";

        case "PREPARING":
            return "bg-orange-500";

        case "READY":
            return "bg-purple-500";

        case "OUT_FOR_DELIVERY":
            return "bg-pink-500";

        case "DELIVERED":
            return "bg-green-600";

        case "CANCELLED":
            return "bg-red-600";

        default:
            return "bg-gray-500";
    }
};

const OrderStatusChip = ({
    status
}) => {

    return (

        <span
            className={`
                px-3
                py-1
                rounded-full
                text-white
                text-sm
                ${getColor(status)}
            `}
        >
            {status}
        </span>
    );
};

export default OrderStatusChip;