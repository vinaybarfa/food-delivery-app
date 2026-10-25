import { restaurantApi } from "../../config/api";

import {
    GET_INGREDIENTS,
    UPDATE_STOCK,
  CREATE_INGREDIENT_REQUEST,
  CREATE_INGREDIENT_SUCCESS,
  CREATE_INGREDIENT_FAILURE,
  GET_INGREDIENTS_REQUEST,
  GET_INGREDIENTS_SUCCESS,
  GET_INGREDIENTS_FAILURE,
  UPDATE_INGREDIENT_STOCK_REQUEST,
  UPDATE_INGREDIENT_STOCK_SUCCESS,
  UPDATE_INGREDIENT_STOCK_FAILURE,
  CREATE_INGREDIENT_CATEGORY_REQUEST,
  CREATE_INGREDIENT_CATEGORY_SUCCESS,
  CREATE_INGREDIENT_CATEGORY_FAILURE,
  GET_INGREDIENT_CATEGORY_REQUEST,
  GET_INGREDIENT_CATEGORY_SUCCESS,
  GET_INGREDIENT_CATEGORY_FAILURE,
} from "./ActionTypes";




// CREATE INGREDIENT
export const createIngredient = ({ data, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: CREATE_INGREDIENT_REQUEST });

    try {
      const { data: response } = await api.post(
        "/api/admin/ingredients",
        data,
        {
          headers: { Authorization: `Bearer ${jwt}` },
        },
      );

      dispatch({
        type: CREATE_INGREDIENT_SUCCESS,
        payload: response,
      });
    } catch (error) {
      dispatch({
        type: CREATE_INGREDIENT_FAILURE,
        payload: error,
      });
    }
  };
};

// GET INGREDIENTS
export const getIngredients = ({ restaurantId, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: GET_INGREDIENTS_REQUEST });

    try {
      const { data } = await api.get(
        `/api/admin/ingredients/restaurant/${restaurantId}`,
        {
          headers: { Authorization: `Bearer ${jwt}` },
        },
      );

      dispatch({
        type: GET_INGREDIENTS_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: GET_INGREDIENTS_FAILURE,
        payload: error,
      });
    }
  };
};

// UPDATE INGREDIENT STOCK
export const updateIngredientStock = ({ ingredientId, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: UPDATE_INGREDIENT_STOCK_REQUEST });

    try {
      const { data } = await api.put(
        `/api/admin/ingredients/${ingredientId}/stock`,
        {},
        {
          headers: { Authorization: `Bearer ${jwt}` },
        },
      );

      dispatch({
        type: UPDATE_INGREDIENT_STOCK_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: UPDATE_INGREDIENT_STOCK_FAILURE,
        payload: error,
      });
    }
  };
};

// CREATE INGREDIENT CATEGORY
export const createIngredientCategory = ({ data, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: CREATE_INGREDIENT_CATEGORY_REQUEST });

    try {
      const { data: response } = await api.post(
        "/api/admin/ingredients/category",
        data,
        {
          headers: { Authorization: `Bearer ${jwt}` },
        },
      );

      dispatch({
        type: CREATE_INGREDIENT_CATEGORY_SUCCESS,
        payload: response,
      });
    } catch (error) {
      dispatch({
        type: CREATE_INGREDIENT_CATEGORY_FAILURE,
        payload: error,
      });
    }
  };
};

// GET INGREDIENT CATEGORIES
export const getIngredientCategories = ({ restaurantId, jwt }) => {
  return async (dispatch) => {
    dispatch({ type: GET_INGREDIENT_CATEGORY_REQUEST });

    try {
      const { data } = await api.get(
        `/api/admin/ingredients/category/${restaurantId}`,
        {
          headers: { Authorization: `Bearer ${jwt}` },
        },
      );

      dispatch({
        type: GET_INGREDIENT_CATEGORY_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: GET_INGREDIENT_CATEGORY_FAILURE,
        payload: error,
      });
    }
  };
};
