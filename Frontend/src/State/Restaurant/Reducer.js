import {
  CREATE_RESTAURANT_REQUEST,
  CREATE_RESTAURANT_SUCCESS,
  CREATE_RESTAURANT_FAILURE,

  GET_ALL_RESTAURANTS_REQUEST,
  GET_ALL_RESTAURANTS_SUCCESS,
  GET_ALL_RESTAURANTS_FAILURE,

  DELETE_RESTAURANT_REQUEST,
  DELETE_RESTAURANT_SUCCESS,
  DELETE_RESTAURANT_FAILURE,

  UPDATE_RESTAURANT_REQUEST,
  UPDATE_RESTAURANT_SUCCESS,
  UPDATE_RESTAURANT_FAILURE,

  GET_RESTAURANT_BY_ID_REQUEST,
  GET_RESTAURANT_BY_ID_SUCCESS,
  GET_RESTAURANT_BY_ID_FAILURE,

  GET_RESTAURANT_BY_USER_ID_REQUEST,
  GET_RESTAURANT_BY_USER_ID_SUCCESS,
  GET_RESTAURANT_BY_USER_ID_FAILURE,

  UPDATE_RESTAURANT_STATUS_REQUEST,
  UPDATE_RESTAURANT_STATUS_SUCCESS,
  UPDATE_RESTAURANT_STATUS_FAILURE,

  CREATE_EVENTS_REQUEST,
  CREATE_EVENTS_SUCCESS,
  CREATE_EVENTS_FAILURE,

  GET_ALL_EVENTS_REQUEST,
  GET_ALL_EVENTS_SUCCESS,
  GET_ALL_EVENTS_FAILURE,

  DELETE_EVENTS_REQUEST,
  DELETE_EVENTS_SUCCESS,
  DELETE_EVENTS_FAILURE,

  GET_RESTAURANT_EVENTS_REQUEST,
  GET_RESTAURANT_EVENTS_SUCCESS,
  GET_RESTAURANT_EVENTS_FAILURE,

  CREATE_CATEGORY_REQUEST,
  CREATE_CATEGORY_SUCCESS,
  CREATE_CATEGORY_FAILURE,

  GET_RESTAURANT_CATEGORY_REQUEST,
  GET_RESTAURANT_CATEGORY_SUCCESS,
  GET_RESTAURANT_CATEGORY_FAILURE

} from "./ActionTypes";


const initialState = {
  restaurants: [],
  userRestaurant: null,
  events: [],
  categories: [],
  loading: false,
  restaurantEvents: [],
  error: null
};


export const restaurantReducer = (state = initialState, action) => {

  switch (action.type) {

    // CREATE RESTAURANT
    case CREATE_RESTAURANT_REQUEST:
      return { ...state, loading: true };

    case CREATE_RESTAURANT_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurants: [...state.restaurants, action.payload]
      };

    case CREATE_RESTAURANT_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // GET ALL RESTAURANTS
    case GET_ALL_RESTAURANTS_REQUEST:
      return { ...state, loading: true };

    case GET_ALL_RESTAURANTS_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurants: action.payload
      };

    case GET_ALL_RESTAURANTS_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // DELETE RESTAURANT
    case DELETE_RESTAURANT_REQUEST:
      return { ...state, loading: true };

    case DELETE_RESTAURANT_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurants: state.restaurants.filter(
          (item) => item.id !== action.payload
        )
      };

    case DELETE_RESTAURANT_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // UPDATE RESTAURANT
    case UPDATE_RESTAURANT_REQUEST:
      return { ...state, loading: true };

    case UPDATE_RESTAURANT_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurant: action.payload
      };

    case UPDATE_RESTAURANT_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // GET RESTAURANT BY ID
    case GET_RESTAURANT_BY_ID_REQUEST:
      return { ...state, loading: true };

    case GET_RESTAURANT_BY_ID_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurant: action.payload
      };

    case GET_RESTAURANT_BY_ID_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // GET RESTAURANT BY USER
    case GET_RESTAURANT_BY_USER_ID_REQUEST:
      return { ...state, loading: true };

    case GET_RESTAURANT_BY_USER_ID_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurant: action.payload
      };

    case GET_RESTAURANT_BY_USER_ID_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // UPDATE STATUS
    case UPDATE_RESTAURANT_STATUS_REQUEST:
      return { ...state, loading: true };

    case UPDATE_RESTAURANT_STATUS_SUCCESS:
      return {
        ...state,
        loading: false,
        restaurant: action.payload
      };

    case UPDATE_RESTAURANT_STATUS_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // CREATE EVENT
    case CREATE_EVENTS_REQUEST:
      return { ...state, loading: true };

    case CREATE_EVENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        events: [...state.events, action.payload]
      };

    case CREATE_EVENTS_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // GET EVENTS
    case GET_ALL_EVENTS_REQUEST:
    case GET_RESTAURANT_EVENTS_REQUEST:
      return { ...state, loading: true };

    case GET_ALL_EVENTS_SUCCESS:
    case GET_RESTAURANT_EVENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        events: action.payload
      };

    case GET_ALL_EVENTS_FAILURE:
    case GET_RESTAURANT_EVENTS_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // DELETE EVENT
    case DELETE_EVENTS_REQUEST:
      return { ...state, loading: true };

    case DELETE_EVENTS_SUCCESS:
      return {
        ...state,
        loading: false,
        events: state.events.filter(
          (event) => event.id !== action.payload
        )
      };

    case DELETE_EVENTS_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // CREATE CATEGORY
    case CREATE_CATEGORY_REQUEST:
      return { ...state, loading: true };

    case CREATE_CATEGORY_SUCCESS:
      return {
        ...state,
        loading: false,
        categories: [...state.categories, action.payload]
      };

    case CREATE_CATEGORY_FAILURE:
      return { ...state, loading: false, error: action.payload };


    // GET CATEGORY
    case GET_RESTAURANT_CATEGORY_REQUEST:
      return { ...state, loading: true };

    case GET_RESTAURANT_CATEGORY_SUCCESS:
      return {
        ...state,
        loading: false,
        categories: action.payload
      };

    case GET_RESTAURANT_CATEGORY_FAILURE:
      return { ...state, loading: false, error: action.payload };


    default:
      return state;
  }
};