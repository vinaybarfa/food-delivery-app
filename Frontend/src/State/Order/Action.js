import { orderApi } from "../../config/api";

export const getUserOrders =
  (userId, jwt) =>
  async (dispatch) => {

    dispatch({
      type: GET_USER_ORDERS_REQUEST,
    });

    try {

      const { data } =
        await orderApi.get(

          `/api/order/user/${userId}`,

          {
            headers: {
              Authorization:
                `Bearer ${jwt}`
            }
          }
        );

      dispatch({
        type: GET_USER_ORDERS_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: GET_USER_ORDERS_FAILURE,
        payload:
          error.response?.data?.message ||
          error.response?.data ||
          error.message ||
          "Something went wrong",
      });
    }
  };