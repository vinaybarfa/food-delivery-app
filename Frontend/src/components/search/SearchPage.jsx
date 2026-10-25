import React, {
  useEffect
} from "react";

import {
  useLocation,
  useNavigate
} from "react-router-dom";

import {
  useDispatch,
  useSelector
} from "react-redux";

import SearchIcon from "@mui/icons-material/Search";

import {
  Box,
  Card,
  CardContent,
  Typography,
  Chip,
  CircularProgress,
} from "@mui/material";

import {
  searchFood
} from "../../State/Food/Action";

const SearchPage = () => {

  const dispatch =
    useDispatch();

  const location =
    useLocation();

  const navigate =
    useNavigate();

const jwt =
  localStorage.getItem("accessToken");

  const query =
    new URLSearchParams(
      location.search
    ).get("q") || "";

  const {
    foods,
    loading,
    error
  } = useSelector(
    (store) => store.food
  );

  useEffect(() => {

    console.log("QUERY =", query);
    console.log("JWT =", jwt);

    if (
      query.trim() &&
      jwt
    ) {

      console.log("Calling searchFood API...");

      dispatch(
        searchFood(
          query,
          jwt
        )
      );
    }

  }, [
    dispatch,
    query,
    jwt
  ]);

  const addToCart =
    (food) => {

      dispatch({

        type:
          "ADD_TO_CART",

        payload: {

          id:
            food.id,

          food: {

            name:
              food.name,

            price:
              food.price,

            description:
              food.description,

            images:
              food.images || []
          },

          ingredients:
            food.ingredients || []
        }
      });

      alert(
        `${food.name} added to cart`
      );
    };


const demoFoods = [
  {
    id: 1,
    name: "Paneer Butter Masala",
    price: 249,
    description: "Creamy paneer curry with rich tomato gravy",
    images: [
      "https://images.unsplash.com/photo-1546833999-b9f581a1996d"
    ]
  },
  {
    id: 2,
    name: "Veg Loaded Pizza",
    price: 299,
    description: "Cheesy pizza topped with fresh vegetables",
    images: [
      "https://images.unsplash.com/photo-1513104890138-7c749659a591"
    ]
  },
  {
    id: 3,
    name: "White Sauce Pasta",
    price: 199,
    description: "Creamy Italian pasta with herbs",
    images: [
      "https://images.unsplash.com/photo-1555949258-eb67b1ef0ceb"
    ]
  },
  {
    id: 4,
    name: "Veg Burger",
    price: 149,
    description: "Loaded burger with crispy patty",
    images: [
      "https://images.unsplash.com/photo-1568901346375-23c9450c58cd"
    ]
  },
  {
    id: 5,
    name: "Masala Dosa",
    price: 129,
    description: "South Indian crispy dosa",
    images: [
      "https://images.unsplash.com/photo-1668236543090-82eba5ee5976"
    ]
  },
  {
    id: 6,
    name: "Chole Bhature",
    price: 189,
    description: "Punjabi special spicy chole with fluffy bhature",
    images: [
      "https://images.unsplash.com/photo-1626132647523-66c1a7f4a4c1"
    ]
  },
  {
    id: 7,
    name: "Chicken Biryani",
    price: 349,
    description: "Aromatic rice cooked with spicy chicken",
    images: [
      "https://images.unsplash.com/photo-1631515243349-e0cb75fb8d3a"
    ]
  },
  {
    id: 8,
    name: "Hakka Noodles",
    price: 179,
    description: "Chinese-style noodles tossed with vegetables",
    images: [
      "https://images.unsplash.com/photo-1612929633738-8fe44f7ec841"
    ]
  },
  {
    id: 9,
    name: "Paneer Tikka",
    price: 279,
    description: "Grilled paneer cubes with Indian spices",
    images: [
      "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8"
    ]
  },
  {
    id: 10,
    name: "Chocolate Brownie",
    price: 149,
    description: "Rich chocolate brownie served warm",
    images: [
      "https://images.unsplash.com/photo-1606313564200-e75d5e30476c"
    ]
  },
  {
    id: 11,
    name: "Margherita Pizza",
    price: 249,
    description: "Classic pizza with mozzarella cheese",
    images: [
      "https://images.unsplash.com/photo-1604382355076-af4b0eb60143"
    ]
  },
  {
    id: 12,
    name: "French Fries",
    price: 99,
    description: "Crispy golden potato fries",
    images: [
      "https://images.unsplash.com/photo-1576107232684-1279f390859f"
    ]
  },
  {
    id: 13,
    name: "Momos",
    price: 149,
    description: "Steamed dumplings served with spicy chutney",
    images: [
      "https://images.unsplash.com/photo-1626700051175-6818013e1d4f"
    ]
  },
  {
    id: 14,
    name: "Dal Makhani",
    price: 219,
    description: "Creamy black lentils cooked overnight",
    images: [
      "https://images.unsplash.com/photo-1547592180-85f173990554"
    ]
  },
  {
    id: 15,
    name: "Cold Coffee",
    price: 119,
    description: "Refreshing chilled coffee with ice cream",
    images: [
      "https://images.unsplash.com/photo-1517701604599-bb29b565090c"
    ]
  }
];


  return (

    <Box
      className="
        min-h-screen
        bg-black
        text-white
        px-4
        md:px-10
        py-10
      "
    >

      {/* HEADER */}

      <div
        className="
          flex
          items-center
          gap-3
          mb-8
        "
      >

        <SearchIcon
          sx={{
            fontSize: "2rem",
            color: "#ec4899",
          }}
        />

        <div>

          <Typography
            variant="h4"
            fontWeight="bold"
          >
            Search Results
          </Typography>

          <Typography
            sx={{
              color: "#9ca3af",
              mt: 1,
            }}
          >
            Showing results for:

            <span
              className="
                text-pink-500
                ml-2
                font-semibold
              "
            >
              "{query}"
            </span>

          </Typography>

        </div>

      </div>

      {/* LOADING */}

      {loading && (

        <div
          className="
            flex
            justify-center
            mt-20
          "
        >

          <CircularProgress
            color="secondary"
          />

        </div>
      )}

      {/* EMPTY */}

      {!loading &&
        foods?.length === 0 &&
        demoFoods.length === 0 && (

          <div
            className="
        flex
        flex-col
        items-center
        justify-center
        mt-20
      "
          >

            <Typography
              variant="h5"
              sx={{
                color: "#9ca3af",
                mb: 2,
              }}
            >
              No food found
            </Typography>

            <button
              onClick={() =>
                navigate("/")
              }
              className="
          bg-pink-600
          hover:bg-pink-700
          px-5
          py-3
          rounded-xl
          transition-all
          duration-300
        "
            >
              Back To Home
            </button>

          </div>

        )}

      {/* FOOD GRID */}

      <div
        className="
          grid
          grid-cols-1
          sm:grid-cols-2
          lg:grid-cols-4
          gap-6
        "
      >

        {(foods?.length > 0 ? foods : demoFoods)?.map(
          (food) => (

            <Card

              key={food.id}

              sx={{
                background:
                  "#111827",

                borderRadius:
                  "18px",

                overflow:
                  "hidden",

                cursor:
                  "pointer",

                transition:
                  "0.3s",

                "&:hover": {
                  transform:
                    "translateY(-6px)",
                },
              }}
            >

              <img

                src={
                  food.images?.[0] ||

                  "https://via.placeholder.com/300"
                }

                alt={
                  food.name
                }

                className="
                  w-full
                  h-52
                  object-cover
                "
              />

              <CardContent>

                <div
                  className="
                    flex
                    items-center
                    justify-between
                    mb-3
                  "
                >

                  <Typography
                    variant="h6"
                    sx={{
                      color:
                        "white",

                      fontWeight:
                        "bold",
                    }}
                  >
                    {food.name}
                  </Typography>

                  <Chip

                    label={
                      food.foodCategory
                        ?.name ||

                      "Food"
                    }

                    sx={{
                      background:
                        "#ec4899",

                      color:
                        "white",
                    }}
                  />

                </div>

                <Typography
                  sx={{
                    color:
                      "#9ca3af",

                    mb: 3,
                  }}
                >
                  {food.description}
                </Typography>

                <div
                  className="
                    flex
                    items-center
                    justify-between
                  "
                >

                  <Typography
                    sx={{
                      color:
                        "#ec4899",

                      fontWeight:
                        "bold",

                      fontSize:
                        "1.1rem",
                    }}
                  >
                    ₹{food.price}
                  </Typography>

                  <button

                    onClick={() =>
                      addToCart(
                        food
                      )
                    }

                    className="
                      bg-pink-600
                      hover:bg-pink-700
                      px-4
                      py-2
                      rounded-lg
                      transition-all
                      duration-300
                    "
                  >
                    Add
                  </button>

                </div>

              </CardContent>

            </Card>
          )
        )}

      </div>

    </Box>
  );
};

export default SearchPage;