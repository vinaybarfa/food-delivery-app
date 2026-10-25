const initialState = {

    cartItems:
        JSON.parse(
            localStorage.getItem("cartItems")
        ) || [],

};

export const cartReducer = (
    state = initialState,
    action
) => {

    switch (action.type) {

        // ================= ADD =================

        case "ADD_TO_CART": {

            const existingItem =
                state.cartItems.find(
                    (item) =>
                        item.id === action.payload.id
                );

            let updatedCart;

            if (existingItem) {

                updatedCart =
                    state.cartItems.map(
                        (item) =>

                            item.id === action.payload.id

                                ? {
                                      ...item,
                                      quantity:
                                          item.quantity + 1,
                                  }

                                : item
                    );

            } else {

                updatedCart = [

                    ...state.cartItems,

                    {
                        ...action.payload,
                        quantity: 1,
                    },
                ];
            }

            localStorage.setItem(
                "cartItems",
                JSON.stringify(updatedCart)
            );

            return {
                ...state,
                cartItems: updatedCart,
            };
        }

        // ================= INCREASE =================

        case "INCREASE_QUANTITY": {

            const updatedCart =
                state.cartItems.map(
                    (item) =>

                        item.id === action.payload

                            ? {
                                  ...item,
                                  quantity:
                                      item.quantity + 1,
                              }

                            : item
                );

            localStorage.setItem(
                "cartItems",
                JSON.stringify(updatedCart)
            );

            return {
                ...state,
                cartItems: updatedCart,
            };
        }

        // ================= DECREASE =================

        case "DECREASE_QUANTITY": {

            const updatedCart =
                state.cartItems
                    .map(
                        (item) =>

                            item.id === action.payload

                                ? {
                                      ...item,
                                      quantity:
                                          item.quantity - 1,
                                  }

                                : item
                    )
                    .filter(
                        (item) =>
                            item.quantity > 0
                    );

            localStorage.setItem(
                "cartItems",
                JSON.stringify(updatedCart)
            );

            return {
                ...state,
                cartItems: updatedCart,
            };
        }

        // ================= REMOVE =================

        case "REMOVE_ITEM": {

            const updatedCart =
                state.cartItems.filter(
                    (item) =>
                        item.id !== action.payload
                );

            localStorage.setItem(
                "cartItems",
                JSON.stringify(updatedCart)
            );

            return {
                ...state,
                cartItems: updatedCart,
            };
        }

        default:
            return state;
    }
};