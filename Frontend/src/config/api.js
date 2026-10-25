import axios from "axios";

/* ================= SERVICE URLS ================= */

export const AUTH_URL = "http://localhost:1017";
export const RESTAURANT_URL = "http://localhost:1011";
export const FOOD_URL = "http://localhost:1012";
export const ORDER_URL = "http://localhost:1014";

/* ================= AUTH API ================= */

export const authApi = axios.create({
  baseURL: AUTH_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

/* ================= RESTAURANT API ================= */

export const restaurantApi = axios.create({
  baseURL: RESTAURANT_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

/* ================= FOOD API ================= */

export const foodApi = axios.create({
  baseURL: FOOD_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

/* ================= ORDER API ================= */

export const orderApi = axios.create({
  baseURL: ORDER_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

/* ================= COMMON INTERCEPTOR ================= */

const addInterceptor = (instance) => {
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("accessToken");

      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }

      return config;
    },
    (error) => Promise.reject(error)
  );

  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      console.error(
        "API ERROR =>",
        error.response?.data || error.message
      );

      return Promise.reject(error);
    }
  );
};

/* ================= APPLY INTERCEPTORS ================= */

addInterceptor(authApi);
addInterceptor(restaurantApi);
addInterceptor(foodApi);
addInterceptor(orderApi);