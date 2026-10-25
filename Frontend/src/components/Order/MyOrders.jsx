import React, { useEffect } from "react";

import {
    useDispatch,
    useSelector
} from "react-redux";

import {
    getUserOrders
} from "../../State/Order/Action";

import {
    connectOrderTracking,
    disconnectOrderTracking
} from "../../services/orderTrackingService";

import OrderStatusChip from "./OrderStatusChip";

import OrderTrackingStepper
    from "./OrderTrackingStepper";

const MyOrders = () => {

    const dispatch = useDispatch();

    const user =
        JSON.parse(
            localStorage.getItem("user")
        );

    const jwt =
        localStorage.getItem("jwt");

    const {
        orders,
        loading,
        error
    } = useSelector(
        (store) => store.order
    );

    useEffect(() => {

        if (!user?.id || !jwt) return;

        dispatch(
            getUserOrders(
                user.id,
                jwt
            )
        );

        connectOrderTracking(

            user.id,

            () => {

                dispatch(
                    getUserOrders(
                        user.id,
                        jwt
                    )
                );
            }
        );

        return () => {

            disconnectOrderTracking();
        };

    }, [dispatch, user?.id, jwt]);

    if (loading) {

        return (

            <div className="p-5">

                <h1>
                    Loading Orders...
                </h1>

            </div>
        );
    }

    return (

        <div className="p-5">

            <h1 className="text-3xl font-bold mb-5">

                My Orders

            </h1>

            {

                orders?.length > 0

                    ?

                    orders.map(

                        (order) => (

                            <div
                                key={order.id}
                                className="
                                    border
                                    p-4
                                    mb-4
                                    rounded
                                    shadow-sm
                                "
                            >

                                <h2 className="font-semibold text-lg">

                                    Order #
                                    {order.id}

                                </h2>

                                <p className="mt-2">

                                    Amount :
                                    ₹{order.totalAmount}

                                </p>

                                <p className="mt-2">

                                    Status :
                                    {order.orderStatus}

                                </p>

                                <div className="mt-3">

                                    <OrderStatusChip
                                        status={
                                            order.orderStatus
                                        }
                                    />

                                </div>

                                <div className="mt-4">

                                    <OrderTrackingStepper
                                        currentStatus={
                                            order.orderStatus
                                        }
                                    />

                                </div>

                            </div>
                        )
                    )

                    :

                    <p>

                        No Orders Found

                    </p>
            }

            {

                error && (

                    <p className="text-red-500 mt-3">

                        {typeof error === "string"
                            ? error
                            : "Failed to load orders"}

                    </p>
                )
            }

        </div>
    );
};

export default MyOrders;