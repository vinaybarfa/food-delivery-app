import { restaurantApi } from "../../config/api";
/* ================= GET ALL RESTAURANTS ================= */

export const getAdminRestaurants = () => async (dispatch) => {
  dispatch({
    type: "GET_ADMIN_RESTAURANTS_REQUEST",
  });

  try {
    const { data } = await restaurantApi.get(
      "/api/admin/restaurant/restaurants",
    );

    dispatch({
      type: "GET_ADMIN_RESTAURANTS_SUCCESS",

      payload: data,
    });

    console.log("ADMIN API RESPONSE => ", JSON.stringify(data));
  } catch (error) {
    dispatch({
      type: "GET_ADMIN_RESTAURANTS_FAILURE",

      payload: error.response?.data || error.message,
    });

    console.log("admin restaurant error", error);
  }
};
