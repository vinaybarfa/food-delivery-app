import { orderApi } from "../../config/api";

import {

  GET_RESTAURANT_ORDERS_REQUEST,
  GET_RESTAURANT_ORDERS_SUCCESS,
  GET_RESTAURANT_ORDERS_FAILURE,

  UPDATE_ORDER_STATUS_REQUEST,
  UPDATE_ORDER_STATUS_SUCCESS,
  UPDATE_ORDER_STATUS_FAILURE

} from "./ActionType";

/* ================= GET RESTAURANT ORDERS ================= */

export const fetchRestaurantOrders =
({
  restaurantId,
  orderStatus
}) =>
async (dispatch) => {

  dispatch({
    type:
      GET_RESTAURANT_ORDERS_REQUEST
  });

  try {

    const { data } =
      await orderApi.get(

        `/api/admin/order/restaurant/${restaurantId}`,

        {
          params: {
            orderStatus
          }
        }
      );

    dispatch({

      type:
        GET_RESTAURANT_ORDERS_SUCCESS,

      payload:
        data
    });

  } catch (error) {

    dispatch({

      type:
        GET_RESTAURANT_ORDERS_FAILURE,

      payload:

        error.response?.data ||

        error.message
    });
  }
};

/* ================= UPDATE ORDER STATUS ================= */

export const updateOrderStatus =
({
  orderId,
  status
}) =>
async (dispatch) => {

  dispatch({

    type:
      UPDATE_ORDER_STATUS_REQUEST
  });

  try {

    const { data } =
      await orderApi.put(

        `/api/admin/order/status/${orderId}/${status}`
      );

    dispatch({

      type:
        UPDATE_ORDER_STATUS_SUCCESS,

      payload:
        data
    });

  } catch (error) {

    dispatch({

      type:
        UPDATE_ORDER_STATUS_FAILURE,

      payload:

        error.response?.data ||

        error.message
    });
  }
};