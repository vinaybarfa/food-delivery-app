import React from "react";

const statuses = [

    "PENDING",

    "CONFIRMED",

    "PREPARING",

    "READY",

    "OUT_FOR_DELIVERY",

    "DELIVERED"
];

const OrderTrackingStepper =
({
    currentStatus
}) => {

    const currentIndex =
        statuses.indexOf(
            currentStatus
        );

    return (

        <div className="flex flex-wrap gap-4">

            {
                statuses.map(

                    (
                        status,
                        index
                    ) => (

                        <div
                            key={status}
                            className={
                                index <= currentIndex
                                    ? "text-green-500 font-bold"
                                    : "text-gray-500"
                            }
                        >
                            ✓ {status}
                        </div>
                    )
                )
            }

        </div>
    );
};

export default OrderTrackingStepper;