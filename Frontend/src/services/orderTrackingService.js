import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectOrderTracking = (
    userId,
    callback
) => {

    stompClient = new Client({

        brokerURL:
            "ws://localhost:1013/ws",

        reconnectDelay:
            5000,

        onConnect: () => {

            console.log(
                "Order Tracking Connected"
            );

            stompClient.subscribe(

                `/topic/order/${userId}`,

                (message) => {

                    const event =
                        JSON.parse(
                            message.body
                        );

                    callback(
                        event
                    );
                }
            );
        },

        onStompError:
            (frame) => {

                console.error(
                    "STOMP Error",
                    frame
                );
            }
    });

    stompClient.activate();
};

export const disconnectOrderTracking =
() => {

    stompClient?.deactivate();
};