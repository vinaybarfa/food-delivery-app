const initialState = {

  restaurants: [],

  loading: false,

  error: null,
};

export const adminRestaurantReducer = (

  state = initialState,

  action

) => {

  switch (action.type) {

    case "GET_ADMIN_RESTAURANTS_REQUEST":

      return {

        ...state,

        loading: true,
      };

    case "GET_ADMIN_RESTAURANTS_SUCCESS":

      return {

        ...state,

        loading: false,

        restaurants:
          Array.isArray(action.payload)

            ? action.payload

            : Array.isArray(
                action.payload?.restaurants
              )

            ? action.payload.restaurants

            : Array.isArray(
                action.payload?.data
              )

            ? action.payload.data

            : [],
      };

    case "GET_ADMIN_RESTAURANTS_FAILURE":

      return {

        ...state,

        loading: false,

        error: action.payload,
      };

    default:

      return state;
  }
};

