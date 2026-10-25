import {

  CREATE_ORDER_REQUEST,
  CREATE_ORDER_SUCCESS,
  CREATE_ORDER_FAILURE,

  GET_USER_ORDERS_REQUEST,
  GET_USER_ORDERS_SUCCESS,
  GET_USER_ORDERS_FAILURE

} from "./ActionType";

const initialState = {

  orders: [],

  loading: false,

  error: null,

  success: false,

  currentOrder: null
};

export const orderReducer = (

  state = initialState,

  action

) => {

  switch (action.type) {

    case CREATE_ORDER_REQUEST:

    case GET_USER_ORDERS_REQUEST:

      return {

        ...state,

        loading: true,

        error: null
      };

    case CREATE_ORDER_SUCCESS:

      return {

        ...state,

        loading: false,

        success: true,

        currentOrder: action.payload,

        orders: [

          action.payload,

          ...state.orders
        ]
      };

    case GET_USER_ORDERS_SUCCESS:

      return {

        ...state,

        loading: false,

        orders: Array.isArray(
          action.payload
        )
          ? action.payload
          : []
      };

    case CREATE_ORDER_FAILURE:

    case GET_USER_ORDERS_FAILURE:

      return {

        ...state,

        loading: false,

        success: false,

        error: action.payload
      };

    default:

      return state;
  }
};
