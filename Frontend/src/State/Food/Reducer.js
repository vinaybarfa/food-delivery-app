const initialState = {

    foods: [],

    loading: false,

    error: null
};

export const foodReducer =
(
    state = initialState,
    action
) => {

    switch (action.type) {

        case "SEARCH_FOOD_REQUEST":

            return {

                ...state,

                loading: true
            };

        case "SEARCH_FOOD_SUCCESS":

            return {

                ...state,

                loading: false,

                foods:
                    action.payload
            };

        case "SEARCH_FOOD_FAILURE":

            return {

                ...state,

                loading: false,

                error:
                    action.payload
            };

        default:

            return state;
    }
};