import axios from "axios"

/* ================= ACTION TYPES ================= */

export const GET_ALL_RESTAURANTS_REQUEST =
  "GET_ALL_RESTAURANTS_REQUEST"

export const GET_ALL_RESTAURANTS_SUCCESS =
  "GET_ALL_RESTAURANTS_SUCCESS"

export const GET_ALL_RESTAURANTS_FAILURE =
  "GET_ALL_RESTAURANTS_FAILURE"

/* ================= API ================= */

const RESTAURANT_API =
  "http://localhost:1011"

/* ================= GET ALL RESTAURANTS ================= */

export const getAllRestaurantsAction =
  (token) => {

    return async (dispatch) => {

      dispatch({

        type:
          GET_ALL_RESTAURANTS_REQUEST,
      })

      try {

        /*
         * CUSTOMER RESTAURANT API
         */

        const response =
          await axios.get(

            `${RESTAURANT_API}/api/restaurant/restaurants`,

            {
              headers: {
                Authorization:
                  `Bearer ${token}`,
              },
            }
          )

        console.log(
          "restaurants : ",
          response.data
        )

        dispatch({

          type:
            GET_ALL_RESTAURANTS_SUCCESS,

          payload:
            response.data,
        })

      } catch (error) {

        console.log(
          "catched error ",
          error
        )

        dispatch({

          type:
            GET_ALL_RESTAURANTS_FAILURE,

          payload:
            error.response?.data ||
            error.message,
        })
      }
    }
  }

/* ================= SEARCH RESTAURANTS ================= */

export const searchRestaurants =
  ({ keyword, token }) => {

    return async (dispatch) => {

      dispatch({

        type:
          GET_ALL_RESTAURANTS_REQUEST,
      })

      try {

        const response =
          await axios.get(

            `${RESTAURANT_API}/api/restaurant/search`,

            {
              params: {
                keyword,
              },

              headers: {
                Authorization:
                  `Bearer ${token}`,
              },
            }
          )

        dispatch({

          type:
            GET_ALL_RESTAURANTS_SUCCESS,

          payload:
            response.data,
        })

      } catch (error) {

        console.log(
          "search error ",
          error
        )

        dispatch({

          type:
            GET_ALL_RESTAURANTS_FAILURE,

          payload:
            error.response?.data ||
            error.message,
        })
      }
    }
  }

/* ================= GET RESTAURANT BY ID ================= */

export const getRestaurantById =
  ({ restaurantId, token }) => {

    return async (dispatch) => {

      dispatch({

        type:
          GET_ALL_RESTAURANTS_REQUEST,
      })

      try {

        const response =
          await axios.get(

            `${RESTAURANT_API}/api/restaurant/${restaurantId}`,

            {
              headers: {
                Authorization:
                  `Bearer ${token}`,
              },
            }
          )

        dispatch({

          type:
            GET_ALL_RESTAURANTS_SUCCESS,

          payload:
            [response.data],
        })

      } catch (error) {

        console.log(
          "restaurant details error ",
          error
        )

        dispatch({

          type:
            GET_ALL_RESTAURANTS_FAILURE,

          payload:
            error.response?.data ||
            error.message,
        })
      }
    }
  }