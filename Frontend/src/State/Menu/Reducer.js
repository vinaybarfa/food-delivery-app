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
  UPDATE_MENU_ITEM_AVAILABILITY_FAILURE,
} from "./ActionType";

const initialState = {
  menuItems: [],
  searchResults: [],
  loading: false,
  error: null,
  message: null,
};

export const menuItemReducer = (state = initialState, action) => {
  switch (action.type) {
    // ALL REQUESTS
    case CREATE_MENU_ITEM_REQUEST:
    case GET_MENU_BY_RESTAURANT_ID_REQUEST:
    case DELETE_MENU_ITEM_REQUEST:
    case SEARCH_MENU_ITEM_REQUEST:
    case UPDATE_MENU_ITEM_AVAILABILITY_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };

    // CREATE MENU ITEM
    case CREATE_MENU_ITEM_SUCCESS:
      return {
        ...state,
        loading: false,
        menuItems: [...state.menuItems, action.payload],
        message: "Menu item created successfully",
      };

    case CREATE_MENU_ITEM_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    // GET MENU BY RESTAURANT
    case GET_MENU_BY_RESTAURANT_ID_SUCCESS:
      return {
        ...state,
        loading: false,
        menuItems: action.payload,
      };

    case GET_MENU_BY_RESTAURANT_ID_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    // DELETE MENU ITEM
    case DELETE_MENU_ITEM_SUCCESS:
      return {
        ...state,
        loading: false,
        menuItems: state.menuItems.filter((item) => item.id !== action.payload),
        message: "Menu item deleted successfully",
      };

    case DELETE_MENU_ITEM_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    // SEARCH MENU ITEM
    case SEARCH_MENU_ITEM_SUCCESS:
      return {
        ...state,
        loading: false,
        searchResults: action.payload,
      };

    case SEARCH_MENU_ITEM_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    // UPDATE MENU ITEM AVAILABILITY
    case UPDATE_MENU_ITEM_AVAILABILITY_SUCCESS:
      return {
        ...state,
        loading: false,
        menuItems: state.menuItems.map((item) =>
          item.id === action.payload.id ? action.payload : item,
        ),
        message: "Menu availability updated",
      };

    case UPDATE_MENU_ITEM_AVAILABILITY_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    default:
      return state;
  }
};
