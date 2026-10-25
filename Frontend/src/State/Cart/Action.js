import { api } from "../../config/api";

import {
  FIND_CART_REQUEST,
  FIND_CART_SUCCESS,
  FIND_CART_FAILURE,
  GET_ALL_CART_ITEMS_REQUEST,
  GET_ALL_CART_ITEMS_SUCCESS,
  GET_ALL_CART_ITEMS_FAILURE,
  ADD_ITEM_TO_CART_REQUEST,
  ADD_ITEM_TO_CART_SUCCESS,
  ADD_ITEM_TO_CART_FAILURE,
  UPDATE_CART_ITEM_REQUEST,
  UPDATE_CART_ITEM_SUCCESS,
  UPDATE_CART_ITEM_FAILURE,
  REMOVE_CART_ITEM_REQUEST,
  REMOVE_CART_ITEM_SUCCESS,
  REMOVE_CART_ITEM_FAILURE,
  CLEAR_CART_REQUEST,
  CLEAR_CART_SUCCESS,
  CLEAR_CART_FAILURE,
} from "./ActionTypes";

// FIND CART
export const findCart = (jwt) => {
  return async (dispatch) => {
    dispatch({ type: FIND_CART_REQUEST });

    try {
      const { data } = await api.get("/api/cart", {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      dispatch({
        type: FIND_CART_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: FIND_CART_FAILURE,
        payload: error,
      });
    }
  };
};

// GET CART ITEMS
export const getAllCartItems = (jwt) => {
  return async (dispatch) => {
    dispatch({ type: GET_ALL_CART_ITEMS_REQUEST });

    try {
      const { data } = await api.get(`/api/carts/${jwt.data}/items`, {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      dispatch({
        type: GET_ALL_CART_ITEMS_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: GET_ALL_CART_ITEMS_FAILURE,
        payload: error,
      });
    }
  };
};

// ADD ITEM TO CART
export const addItemToCart = ({ data, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: ADD_ITEM_TO_CART_REQUEST });

    try {
      const res = await api.post("/api/cart/add", data, {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      dispatch({
        type: ADD_ITEM_TO_CART_SUCCESS,
        payload: res.data,
      });
    } catch (error) {
      dispatch({
        type: ADD_ITEM_TO_CART_FAILURE,
        payload: error,
      });
    }
  };
};

// UPDATE CART ITEM
export const updateCartItem = ({reqData}) => {
  return async (dispatch) => {
    dispatch({ type: UPDATE_CART_ITEM_REQUEST });

    try {
      const res = await api.put(`/api/cart-item/update`, reqData.data, {
        headers: { Authorization: `Bearer ${reqData.jwt}` },
      });

      dispatch({
        type: UPDATE_CART_ITEM_SUCCESS,
        payload: res.data,
      });
    } catch (error) {
      dispatch({
        type: UPDATE_CART_ITEM_FAILURE,
        payload: error,
      });
    }
  };
};

// REMOVE CART ITEM
export const removeCartItem = ({ cartItemId, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: REMOVE_CART_ITEM_REQUEST });

    try {
      await api.delete(`/api/cart/item/${cartItemId}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      dispatch({
        type: REMOVE_CART_ITEM_SUCCESS,
        payload: cartItemId,
      });
    } catch (error) {
      dispatch({
        type: REMOVE_CART_ITEM_FAILURE,
        payload: error,
      });
    }
  };
};

// CLEAR CART
export const clearCart = (jwt) => {
  return async (dispatch) => {
    dispatch({ type: CLEAR_CART_REQUEST });

    try {
      await api.delete("/api/cart/clear", {
        headers: { Authorization: `Bearer ${jwt}` },
      });

      dispatch({
        type: CLEAR_CART_SUCCESS,
      });
    } catch (error) {
      dispatch({
        type: CLEAR_CART_FAILURE,
        payload: error,
      });
    }
  };
};
