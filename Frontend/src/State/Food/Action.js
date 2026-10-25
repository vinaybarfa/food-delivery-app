import { foodApi } from "../../config/api";

export const searchFood =
  (keyword, jwt) =>
  async (dispatch) => {

    dispatch({
      type: "SEARCH_FOOD_REQUEST"
    });

    try {

      const { data } =
        await foodApi.get(

          `/api/food/search?keyword=${keyword}`,

          {
            headers: {
              Authorization:
                `Bearer ${jwt}`
            }
          }
        );

      dispatch({
        type: "SEARCH_FOOD_SUCCESS",
        payload: data
      });

    } catch (error) {

      dispatch({
        type: "SEARCH_FOOD_FAILURE",
        payload:
          error.response?.data ||
          error.message
      });
    }
  };