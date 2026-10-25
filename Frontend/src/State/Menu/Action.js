import { foodApi } from "../../config/api";

import {
  CREATE_MENU_ITEM_REQUEST,
  CREATE_MENU_ITEM_SUCCESS,
  CREATE_MENU_ITEM_FAILURE,

  GET_MENU_BY_RESTAURANT_ID_REQUEST,
  GET_MENU_BY_RESTAURANT_ID_SUCCESS,
  GET_MENU_BY_RESTAURANT_ID_FAILURE,

  DELETE_MENU_ITEM_REQUEST,
  DELETE_MENU_ITEM_SUCCESS,
  DELETE_MENU_ITEM_FAILURE,

  SEARCH_MENU_ITEM_REQUEST,
  SEARCH_MENU_ITEM_SUCCESS,
  SEARCH_MENU_ITEM_FAILURE,

  UPDATE_MENU_ITEM_AVAILABILITY_REQUEST,
  UPDATE_MENU_ITEM_AVAILABILITY_SUCCESS,
  UPDATE_MENU_ITEM_AVAILABILITY_FAILURE

} from "./ActionTypes";


// CREATE MENU ITEM
export const createMenuItem = ({ data, jwt }) => {
  return async (dispatch) => {

    dispatch({ type: CREATE_MENU_ITEM_REQUEST });

    try {

      const { data: response } = await api.post(
        "/api/admin/food",
        data,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      console.log("menu item created", response);

      dispatch({
        type: CREATE_MENU_ITEM_SUCCESS,
        payload: response,
      });

    } catch (error) {

      dispatch({
        type: CREATE_MENU_ITEM_FAILURE,
        payload: error,
      });

    }
  };
};



export const getMenuByRestaurantId = (reqData) => {
  return async (dispatch) => {

    dispatch({ type: GET_MENU_BY_RESTAURANT_ID_REQUEST });

    try {

      const { data } = await api.get(
        `/api/food/restaurant/${reqData.restaurantId}?vegetarain=${reqData.vegetarian}&nonveg=${reqData.nonveg}
        &seasonal=${reqData.seasonal}&food_category=${reqData.foodCategory}`,
        {
          headers: {
            Authorization: `Bearer ${reqData.jwt}`,
          },
        }
      );

      console.log("restaurant menu", data);

      dispatch({
        type: GET_MENU_BY_RESTAURANT_ID_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: GET_MENU_BY_RESTAURANT_ID_FAILURE,
        payload: error,
      });

    }
  };
};



// DELETE MENU ITEM
export const deleteMenuItem = ({ foodId, jwt }) => {
  return async (dispatch) => {

    dispatch({ type: DELETE_MENU_ITEM_REQUEST });

    try {

      await api.delete(
        `/api/admin/food/${foodId}`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      dispatch({
        type: DELETE_MENU_ITEM_SUCCESS,
        payload: foodId,
      });

    } catch (error) {

      dispatch({
        type: DELETE_MENU_ITEM_FAILURE,
        payload: error,
      });

    }
  };
};



// SEARCH MENU ITEM
export const searchMenuItem = ({ keyword, jwt }) => {
  return async (dispatch) => {

    dispatch({ type: SEARCH_MENU_ITEM_REQUEST });

    try {

      const { data } = await api.get(
        `/api/food/search?name=${keyword}`,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      console.log("search menu result", data);

      dispatch({
        type: SEARCH_MENU_ITEM_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: SEARCH_MENU_ITEM_FAILURE,
        payload: error,
      });

    }
  };
};



// UPDATE MENU ITEM AVAILABILITY
export const updateMenuItemAvailability = ({ foodId, jwt }) => {
  return async (dispatch) => {

    dispatch({ type: UPDATE_MENU_ITEM_AVAILABILITY_REQUEST });

    try {

      const { data } = await api.put(
        `/api/admin/food/${foodId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      console.log("menu availability updated", data);

      dispatch({
        type: UPDATE_MENU_ITEM_AVAILABILITY_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: UPDATE_MENU_ITEM_AVAILABILITY_FAILURE,
        payload: error,
      });

    }
  };
};