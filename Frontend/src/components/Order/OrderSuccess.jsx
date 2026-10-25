import React from "react";

import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import DeliveryDiningIcon from "@mui/icons-material/DeliveryDining";
import ReceiptLongIcon from "@mui/icons-material/ReceiptLong";
import LocationOnIcon from "@mui/icons-material/LocationOn";

import {
  Button,
} from "@mui/material";

import {
  useNavigate,
} from "react-router-dom";

const OrderSuccess = () => {

  const navigate = useNavigate();

  const currentOrder =
    JSON.parse(
      localStorage.getItem(
        "currentOrder"
      )
    );

  if (!currentOrder) {

    return (

      <div className="bg-black min-h-screen flex items-center justify-center text-white">

        <div className="text-center">

          <h1 className="text-3xl font-bold">

            No Order Found

          </h1>

          <Button
            variant="contained"
            sx={{
              mt: 4,
              background: "#ec4899",
            }}
            onClick={() =>
              navigate("/")
            }
          >
            Go Home
          </Button>

        </div>

      </div>
    );
  }

  const totalItems =
    currentOrder.items?.reduce(
      (total, item) =>
        total + item.quantity,
      0
    ) || 0;

  return (

    <div className="bg-black min-h-screen flex items-center justify-center px-4 py-6">

      <div className="w-full max-w-5xl bg-[#1f1f1f] rounded-3xl p-6 shadow-2xl">

        {/* HEADER */}

        <div className="text-center">

          <CheckCircleIcon
            sx={{
              fontSize: "5rem",
              color: "#22c55e",
            }}
          />

          <h1 className="text-4xl font-bold mt-3">

            Order Confirmed

          </h1>

          <p className="text-gray-400 mt-2">

            Your order has been placed successfully

          </p>

        </div>

        {/* TOP CARDS */}

        <div className="grid md:grid-cols-3 gap-4 mt-8">

          <div className="bg-[#111111] rounded-2xl p-5">

            <div className="flex items-center gap-2 mb-2">

              <ReceiptLongIcon
                sx={{
                  color: "#ec4899",
                }}
              />

              <h2 className="font-semibold">

                Order ID

              </h2>

            </div>

            <p className="text-gray-400">

              #{currentOrder.id}

            </p>

          </div>

          <div className="bg-[#111111] rounded-2xl p-5">

            <div className="flex items-center gap-2 mb-2">

              <DeliveryDiningIcon
                sx={{
                  color: "#ec4899",
                }}
              />

              <h2 className="font-semibold">

                Delivery Time

              </h2>

            </div>

            <p className="text-gray-400">

              25 - 35 Minutes

            </p>

          </div>

          <div className="bg-[#111111] rounded-2xl p-5">

            <h2 className="font-semibold mb-2">

              Status

            </h2>

            <span className="bg-yellow-500 text-black px-3 py-1 rounded-full text-sm font-bold">

              {currentOrder.orderStatus}

            </span>

          </div>

        </div>

        {/* DETAILS */}

        <div className="grid md:grid-cols-2 gap-5 mt-6">

          {/* ORDER SUMMARY */}

          <div className="bg-[#111111] rounded-2xl p-5">

            <h2 className="text-xl font-bold mb-4">

              Order Summary

            </h2>

            <div className="space-y-3">

              {currentOrder.items?.map(
                (item) => (

                  <div
                    key={item.id}
                    className="flex justify-between"
                  >

                    <span>

                      {item.food.name}
                      {" "}
                      ×
                      {" "}
                      {item.quantity}

                    </span>

                    <span>

                      ₹
                      {item.food.price *
                        item.quantity}

                    </span>

                  </div>
                )
              )}

              <hr className="border-gray-700" />

              <div className="flex justify-between font-bold text-lg">

                <span>

                  Total Items

                </span>

                <span>

                  {totalItems}

                </span>

              </div>

              <div className="flex justify-between font-bold text-pink-500 text-xl">

                <span>

                  Total Amount

                </span>

                <span>

                  ₹
                  {currentOrder.totalAmount}

                </span>

              </div>

            </div>

          </div>

          {/* DELIVERY ADDRESS */}

          <div className="bg-[#111111] rounded-2xl p-5">

            <div className="flex items-center gap-2 mb-4">

              <LocationOnIcon
                sx={{
                  color: "#ec4899",
                }}
              />

              <h2 className="text-xl font-bold">

                Delivery Address

              </h2>

            </div>

            <div className="space-y-2 text-gray-300">

              <p>

                <strong>Name:</strong>
                {" "}
                {currentOrder.address?.fullName}

              </p>

              <p>

                <strong>Phone:</strong>
                {" "}
                {currentOrder.address?.phone}

              </p>

              <p>

                <strong>City:</strong>
                {" "}
                {currentOrder.address?.city}

              </p>

              <p>

                <strong>State:</strong>
                {" "}
                {currentOrder.address?.state}

              </p>

              <p>

                <strong>Pincode:</strong>
                {" "}
                {currentOrder.address?.pincode}

              </p>

              <p>

                <strong>Address:</strong>
                {" "}
                {currentOrder.address?.address}

              </p>

            </div>

          </div>

        </div>

        {/* ORDER TRACKER */}

        <div className="bg-[#111111] rounded-2xl p-5 mt-6">

          <h2 className="text-xl font-bold mb-4">

            Order Progress

          </h2>

          <div className="flex flex-wrap gap-4">

            <div className="text-green-500 font-bold">
              ✓ Order Placed
            </div>

            <div className="text-yellow-500 font-bold">
              ✓ Confirmed
            </div>

            <div className="text-gray-500">
              Preparing
            </div>

            <div className="text-gray-500">
              Out For Delivery
            </div>

            <div className="text-gray-500">
              Delivered
            </div>

          </div>

        </div>

        {/* BUTTONS */}

        <div className="grid md:grid-cols-3 gap-4 mt-6">

          <Button
            variant="contained"
            onClick={() =>
              navigate("/")
            }
            sx={{
              background: "#ec4899",
              py: 1.5,
              borderRadius: "12px",
            }}
          >
            Continue Shopping
          </Button>

          <Button
            variant="outlined"
            onClick={() =>
              navigate("/my-orders")
            }
            sx={{
              borderColor: "#ec4899",
              color: "#ec4899",
              py: 1.5,
              borderRadius: "12px",
            }}
          >
            Track Order
          </Button>

          <Button
            variant="outlined"
            onClick={() =>
              navigate("/my-profile")
            }
            sx={{
              borderColor: "#22c55e",
              color: "#22c55e",
              py: 1.5,
              borderRadius: "12px",
            }}
          >
            My Account
          </Button>

        </div>

      </div>

    </div>
  );
};

export default OrderSuccess;