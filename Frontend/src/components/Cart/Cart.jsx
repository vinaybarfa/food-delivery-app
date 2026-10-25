import React, { useMemo } from "react";

import { useSelector, useDispatch } from "react-redux";

import {
  Box,
  Divider,
  Typography,
  Button,
  IconButton,
  Chip,
} from "@mui/material";

import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import ShoppingCartCheckoutIcon from "@mui/icons-material/ShoppingCartCheckout";

import { useNavigate } from "react-router-dom";

const Cart = () => {

  const navigate = useNavigate();

  const dispatch = useDispatch();

  const { cartItems } = useSelector(
    (store) => store.cart
  );

  // ================= INCREASE =================

  const increaseQuantity = (id) => {

    dispatch({

      type: "INCREASE_QUANTITY",

      payload: id,
    });
  };

  // ================= DECREASE =================

  const decreaseQuantity = (id) => {

    dispatch({

      type: "DECREASE_QUANTITY",

      payload: id,
    });
  };

  // ================= REMOVE =================

  const removeItem = (id) => {

    dispatch({

      type: "REMOVE_ITEM",

      payload: id,
    });
  };

  // ================= TOTALS =================

  const totals = useMemo(() => {

    const subtotal = cartItems.reduce(

      (acc, item) =>

        acc +
        item.food.price *
        item.quantity,

      0
    );

    const deliveryFee =
      subtotal > 1000 ? 0 : 49;

    const gst =
      Math.round(
        subtotal * 0.05
      );

    const total =
      subtotal +
      deliveryFee +
      gst;

    return {

      subtotal,

      deliveryFee,

      gst,

      total,
    };

  }, [cartItems]);

  return (

    <Box className="min-h-screen bg-black text-white p-5 lg:p-10">

      <div className="grid lg:grid-cols-3 gap-10">

        {/* LEFT */}

        <div className="lg:col-span-2">

          <Typography
            variant="h4"
            fontWeight="bold"
            mb={5}
          >
            Your Cart
          </Typography>

          {cartItems.length === 0 && (

            <div className="bg-[#1f1f1f] rounded-3xl p-20 text-center">

              <h1 className="text-4xl font-bold">
                Cart Is Empty
              </h1>

              <p className="text-gray-400 mt-5 text-lg">
                Add delicious food items
                to continue ordering.
              </p>

              <Button
                variant="contained"
                sx={{
                  mt: 5,
                  background: "#ec4899",
                  borderRadius: "14px",
                }}
                onClick={() =>
                  navigate("/search")
                }
              >
                Explore Food
              </Button>

            </div>
          )}

          <div className="space-y-6">

            {cartItems.map((item) => {

              const itemTotal =
                item.food.price *
                item.quantity;

              return (

                <div
                  key={item.id}
                  className="
                    bg-[#1f1f1f]
                    rounded-3xl
                    p-5
                    shadow-xl
                  "
                >

                  <div className="flex flex-col lg:flex-row gap-5">

                    <img
                      src={
                        item.food.images?.[0]
                      }
                      alt={
                        item.food.name
                      }
                      className="
                        w-full
                        lg:w-40
                        h-40
                        rounded-2xl
                        object-cover
                      "
                    />

                    <div className="flex-1">

                      <div className="flex justify-between">

                        <div>

                          <h1 className="text-2xl font-bold">

                            {item.food.name}

                          </h1>

                          <p className="text-gray-400 mt-2">

                            {
                              item.food.description
                            }

                          </p>

                        </div>

                        <IconButton
                          onClick={() =>
                            removeItem(
                              item.id
                            )
                          }
                        >

                          <DeleteOutlineIcon
                            sx={{
                              color:
                                "#ef4444",
                            }}
                          />

                        </IconButton>

                      </div>

                      <div className="flex flex-wrap gap-2 mt-5">

                        {item.ingredients?.map(

                          (
                            ingredient,
                            index
                          ) => (

                            <Chip
                              key={index}
                              label={
                                ingredient.name
                              }
                              sx={{
                                background:
                                  "#ec4899",
                                color:
                                  "white",
                              }}
                            />
                          )
                        )}

                      </div>

                      <div className="flex justify-between items-center mt-6">

                        <div className="flex items-center gap-3">

                          <IconButton
                            onClick={() =>
                              decreaseQuantity(
                                item.id
                              )
                            }
                          >

                            <RemoveCircleOutlineIcon
                              sx={{
                                color:
                                  "white",
                              }}
                            />

                          </IconButton>

                          <div className="text-xl font-bold">

                            {
                              item.quantity
                            }

                          </div>

                          <IconButton
                            onClick={() =>
                              increaseQuantity(
                                item.id
                              )
                            }
                          >

                            <AddCircleOutlineIcon
                              sx={{
                                color:
                                  "white",
                              }}
                            />

                          </IconButton>

                        </div>

                        <h1 className="text-2xl font-bold text-pink-500">

                          ₹{itemTotal}

                        </h1>

                      </div>

                    </div>

                  </div>

                </div>
              );
            })}

          </div>

        </div>

        {/* RIGHT */}

        <div>

          <div className="bg-[#1f1f1f] p-8 rounded-3xl sticky top-24 shadow-xl">

            <Typography
              variant="h5"
              fontWeight="bold"
              mb={5}
            >
              Bill Details
            </Typography>

            <div className="space-y-5">

              <div className="flex justify-between text-gray-400">

                <p>Subtotal</p>

                <p>
                  ₹{totals.subtotal}
                </p>

              </div>

              <div className="flex justify-between text-gray-400">

                <p>Delivery Fee</p>

                <p>

                  {totals.deliveryFee === 0
                    ? "FREE"
                    : `₹${totals.deliveryFee}`}

                </p>

              </div>

              <div className="flex justify-between text-gray-400">

                <p>GST</p>

                <p>
                  ₹{totals.gst}
                </p>

              </div>

              <Divider
                sx={{
                  bgcolor: "#333",
                }}
              />

              <div className="flex justify-between text-3xl font-bold">

                <p>Total</p>

                <p className="text-pink-500">

                  ₹{totals.total}

                </p>

              </div>

              <Button
                fullWidth
                variant="contained"
                startIcon={
                  <ShoppingCartCheckoutIcon />
                }
                onClick={() =>
                  navigate("/checkout")
                }
                sx={{
                  mt: 5,
                  background: "#ec4899",
                  py: 1.7,
                  borderRadius: "14px",
                  fontSize: "1rem",
                  fontWeight: "bold",

                  "&:hover": {
                    background:
                      "#db2777",
                  },
                }}
              >
                Proceed To Checkout
              </Button>

            </div>

          </div>

        </div>

      </div>

    </Box>
  );
};

export default Cart;