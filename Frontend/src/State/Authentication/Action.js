import { authApi } from "../../config/api";

import {
  ADD_TO_FAVORITE_FAILURE,
  ADD_TO_FAVORITE_REQUEST,
  ADD_TO_FAVORITE_SUCCESS,
  GET_USER_FAILURE,
  GET_USER_REQUEST,
  GET_USER_SUCCESS,
  LOGIN_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  LOGOUT,
  REGISTER_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
} from "./ActionType";

/* ================= REGISTER ================= */

export const registerUser =
  (reqData) => async (dispatch) => {

    dispatch({
      type: REGISTER_REQUEST,
    });

    try {

      const { data } =
        await authApi.post(
          "/auth/signup",
          reqData.userData
        );

      console.log(
        "register success",
        data
      );

      reqData.navigate(
        "/account/login"
      );

      dispatch({
        type: REGISTER_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: REGISTER_FAILURE,
        payload:
          error.response?.data ||
          error.message,
      });

      console.log(
        "register error",
        error
      );
    }
  };

/* ================= LOGIN ================= */

export const loginUser =
  (reqData) => async (dispatch) => {

    dispatch({
      type: LOGIN_REQUEST,
    });

    try {

      const { data } =
        await authApi.post(
          "/auth/login",
          reqData.userData
        );

      console.log(
        "login success",
        data
      );

      localStorage.setItem(
        "jwt",
        data.accessToken
      );

      localStorage.setItem(
        "accessToken",
        data.accessToken
      );

      localStorage.setItem(
        "refreshToken",
        data.refreshToken
      );

      localStorage.setItem(
        "user",
        JSON.stringify(data)
      );

      dispatch({
        type: LOGIN_SUCCESS,
        payload: data,
      });

      reqData.navigate("/");

    } catch (error) {

      dispatch({
        type: LOGIN_FAILURE,
        payload:
          error.response?.data ||
          error.message,
      });

      console.log(
        "login error",
        error
      );
    }
  };

/* ================= FAVORITES ================= */

export const addToFavorite =
  ({ restaurantId }) =>
  async (dispatch) => {

    dispatch({
      type: ADD_TO_FAVORITE_REQUEST,
    });

    try {

      const { data } =
        await restaurantApi.put(
          `/api/restaurant/${restaurantId}/add-favorites`,
          {}
        );

      dispatch({
        type: ADD_TO_FAVORITE_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: ADD_TO_FAVORITE_FAILURE,
        payload:
          error.response?.data ||
          error.message,
      });

      console.log(
        "favorite error",
        error
      );
    }
  };

/* ================= LOGOUT ================= */

export const logout =
  () => async (dispatch) => {

    localStorage.clear();

    dispatch({
      type: LOGOUT,
    });
  };

/* ================= GET USER ================= */

export const getUser =
  () => async (dispatch) => {

    dispatch({
      type: GET_USER_REQUEST,
    });

    try {

      const { data } =
        await authApi.get(
          "/api/user/profile"
        );

      dispatch({
        type: GET_USER_SUCCESS,
        payload: data,
      });

    } catch (error) {

      dispatch({
        type: GET_USER_FAILURE,
        payload:
          error.response?.data ||
          error.message,
      });

      console.log(
        "get user error",
        error
      );
    }
  };