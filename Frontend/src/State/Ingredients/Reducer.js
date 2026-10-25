import {
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
  GET_INGREDIENT_CATEGORY_FAILURE
} from "./ActionType";

const initialState = {
  ingredients: [],
  categories: [],
  loading: false,
  update: null,
  error: null
};

export const ingredientReducer = (state = initialState, action) => {
  switch (action.type) {
    case CREATE_INGREDIENT_REQUEST:
    case GET_INGREDIENTS_REQUEST:
    case UPDATE_INGREDIENT_STOCK_REQUEST:
    case CREATE_INGREDIENT_CATEGORY_REQUEST:
    case GET_INGREDIENT_CATEGORY_REQUEST:
      return {
        ...state,
        loading: true,
      };

    case CREATE_INGREDIENT_SUCCESS:
      return {
        ...state,
        loading: false,
        ingredients: [...state.ingredients, action.payload],
      };

    case GET_INGREDIENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        ingredients: action.payload,
      };

    case UPDATE_INGREDIENT_STOCK_SUCCESS:
      return {
        ...state,
        loading: false,
        ingredients: state.ingredients.map((item) =>
          item.id === action.payload.id ? action.payload : item,            // this will replace with existing ingredient
        ),
      };

    case CREATE_INGREDIENT_CATEGORY_SUCCESS:
      return {
        ...state,
        loading: false,
        categories: [...state.categories, action.payload],
      };

    case GET_INGREDIENT_CATEGORY_SUCCESS:
      return {
        ...state,
        loading: false,
        categories: action.payload,
      };

    case CREATE_INGREDIENT_FAILURE:
    case GET_INGREDIENTS_FAILURE:
    case UPDATE_INGREDIENT_STOCK_FAILURE:
    case CREATE_INGREDIENT_CATEGORY_FAILURE:
    case GET_INGREDIENT_CATEGORY_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };

    default:
      return state;
  }
};
