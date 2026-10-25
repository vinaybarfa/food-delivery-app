import {

  applyMiddleware,

  combineReducers,

  legacy_createStore

} from "redux";

import { thunk } from "redux-thunk";

/* ================= AUTH ================= */

import {
  authReducer
} from "./Authentication/Reducer";

/* ================= RESTAURANT ================= */

import {
  restaurantReducer
} from "./Restaurant/Reducer";

/* ================= MENU ================= */

import {
  menuItemReducer
} from "./Menu/Reducer";

/* ================= ORDER ================= */

import {
  orderReducer
} from "./Order/Reducer";

/* ================= CART ================= */

import {
  cartReducer
} from "./Cart/Reducer";

/* ================= INGREDIENT ================= */

import {
  ingredientReducer
} from "./Ingredients/Reducer";

/* ================= ADMIN RESTAURANT ================= */

import {
  adminRestaurantReducer
} from "./AdminRestaurant/Reducer";

import {
  foodReducer
} from "./Food/Reducer";

/* ================= ROOT REDUCER ================= */

const rootReducer =
  combineReducers({

    auth: authReducer,

    restaurant:
      restaurantReducer,

    menu:
      menuItemReducer,

    cart:
      cartReducer,

    food: foodReducer,

    order:
      orderReducer,

    ingredients:
      ingredientReducer,

    adminRestaurant:
      adminRestaurantReducer,
  });

/* ================= STORE ================= */

export const store =
  legacy_createStore(

    rootReducer,

    applyMiddleware(thunk)
  );
