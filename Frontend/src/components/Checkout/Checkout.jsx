
import React, { useMemo, useState } from "react";

import { useNavigate } from "react-router-dom";


import Select from "react-select";
import { State, City } from "country-state-city";

import {
  Box,
  Button,
  Divider,
  TextField,
} from "@mui/material";

import { useSelector } from "react-redux";

const Checkout = () => {

  const navigate = useNavigate();

  const { cartItems } = useSelector(
    (store) => store.cart
  );

  const [address, setAddress] = useState({

    fullName: "",
    phone: "",
    city: "",
    state: "",
    pincode: "",
    address: "",
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {

    setAddress({

      ...address,

      [e.target.name]:
        e.target.value,
    });
  };


  const [selectedState, setSelectedState] =
    useState("");

  const states =
    State.getStatesOfCountry("IN")
      .map((state) => ({
        value: state.isoCode,
        label: state.name,
      }));

  const cities =
    selectedState
      ? City.getCitiesOfState(
        "IN",
        selectedState
      ).map((city) => ({
        value: city.name,
        label: city.name,
      }))
      : [];

  const validateForm = () => {

    const newErrors = {};

    if (!address.fullName.trim()) {

      newErrors.fullName =
        "Full Name is required";
    }

    if (!address.phone.trim()) {

      newErrors.phone =
        "Phone Number is required";
    } else if (
      !/^[6-9]\d{9}$/.test(
        address.phone
      )
    ) {

      newErrors.phone =
        "Enter valid 10 digit mobile number";
    }

    if (!address.state.trim()) {

      newErrors.state =
        "State is required";
    }

    if (!address.city.trim()) {

      newErrors.city =
        "City is required";
    }

    if (
      !/^\d{6}$/.test(
        address.pincode
      )
    ) {

      newErrors.pincode =
        "Enter valid 6 digit pincode";
    }

    if (
      address.address.trim().length < 10
    ) {

      newErrors.address =
        "Address should be at least 10 characters";
    }

    setErrors(newErrors);

    return (
      Object.keys(
        newErrors
      ).length === 0
    );
  };

  const totals = useMemo(() => {

    const subtotal =
      cartItems.reduce(

        (acc, item) =>

          acc +
          item.food.price *
          item.quantity,

        0
      );

    const gst =
      Math.round(
        subtotal * 0.05
      );

    const deliveryFee =
      subtotal > 1000
        ? 0
        : 49;

    const total =
      subtotal +
      gst +
      deliveryFee;

    return {

      subtotal,

      gst,

      deliveryFee,

      total,
    };

  }, [cartItems]);

  const handlePlaceOrder = () => {

    if (cartItems.length === 0) {

      alert("Your cart is empty");

      return;
    }

    if (!validateForm()) {

      return;
    }

    const order = {

      id: Date.now(),

      orderStatus: "PENDING",

      totalAmount: totals.total,

      items: cartItems,

      address,
    };

    localStorage.setItem(
      "currentOrder",
      JSON.stringify(order)
    );

    localStorage.setItem(
      "myOrders",
      JSON.stringify([
        order,
        ...(JSON.parse(
          localStorage.getItem(
            "myOrders"
          )
        ) || [])
      ])
    );

    navigate("/order-success");
  };

  return (

    <Box className="bg-black text-white min-h-screen p-5 lg:p-10">

      <div className="grid lg:grid-cols-3 gap-10">

        {/* LEFT */}

        <div className="lg:col-span-2">

          <h1 className="text-4xl font-bold mb-10">
            Checkout
          </h1>

          <div className="bg-[#1f1f1f] rounded-3xl p-8">

            <h1 className="text-2xl font-bold mb-8">
              Delivery Address
            </h1>

            <div className="grid md:grid-cols-2 gap-5">

              <TextField
                fullWidth
                label="Full Name"
                name="fullName"
                value={address.fullName}
                onChange={handleChange}
                error={!!errors.fullName}
                helperText={errors.fullName}
              />

              <TextField
                fullWidth
                label="Phone Number"
                name="phone"
                value={address.phone}
                onChange={handleChange}
                error={!!errors.phone}
                helperText={errors.phone}
              />

              <div>
                <label className="block mb-2 text-white font-medium">
                  Delivery State
                </label>

                <Select
                  options={states}
                  placeholder="Select Delivery State"
                  styles={{
                    control: (base) => ({
                      ...base,
                      backgroundColor: "#111827",
                      borderColor: "#ec4899",
                      minHeight: "56px",
                    }),

                    menu: (base) => ({
                      ...base,
                      backgroundColor: "#111827",
                      color: "white",
                      zIndex: 9999,
                    }),

                    option: (base, state) => ({
                      ...base,
                      backgroundColor: state.isFocused
                        ? "#ec4899"
                        : "#111827",

                      color: "white",

                      cursor: "pointer",
                    }),

                    singleValue: (base) => ({
                      ...base,
                      color: "white",
                    }),

                    input: (base) => ({
                      ...base,
                      color: "white",
                    }),

                    placeholder: (base) => ({
                      ...base,
                      color: "#9ca3af",
                    }),
                  }}
                  onChange={(selected) => {

                    setSelectedState(
                      selected.value
                    );

                    setAddress({
                      ...address,
                      state: selected.label,
                      city: "",
                    });
                  }}
                />

                {errors.state && (
                  <p className="text-red-500 text-sm mt-1">
                    {errors.state}
                  </p>
                )}
              </div>

              <div>
                <label className="block mb-2 text-white font-medium">
                  Delivery City
                </label>

                <Select
                  options={cities}
                  placeholder="Select Delivery City"
                  isDisabled={!selectedState}
                  styles={{
                    control: (base) => ({
                      ...base,
                      backgroundColor: "#111827",
                      borderColor: "#ec4899",
                      minHeight: "56px",
                    }),

                    menu: (base) => ({
                      ...base,
                      backgroundColor: "#111827",
                      color: "white",
                      zIndex: 9999,
                    }),

                    option: (base, state) => ({
                      ...base,
                      backgroundColor: state.isFocused
                        ? "#ec4899"
                        : "#111827",

                      color: "white",

                      cursor: "pointer",
                    }),

                    singleValue: (base) => ({
                      ...base,
                      color: "white",
                    }),

                    input: (base) => ({
                      ...base,
                      color: "white",
                    }),

                    placeholder: (base) => ({
                      ...base,
                      color: "#9ca3af",
                    }),
                  }}
                  onChange={(selected) => {

                    setAddress({
                      ...address,
                      city: selected.label,
                    });
                  }}
                />

                {errors.city && (
                  <p className="text-red-500 text-sm mt-1">
                    {errors.city}
                  </p>
                )}
              </div>

              <TextField
                fullWidth
                label="Pincode"
                name="pincode"
                value={address.pincode}
                onChange={handleChange}
                error={!!errors.pincode}
                helperText={errors.pincode}
              />

              <div className="md:col-span-2">

                <TextField
                  fullWidth
                  label="Full Address"
                  name="address"
                  multiline
                  rows={4}
                  value={address.address}
                  onChange={handleChange}
                  error={!!errors.address}
                  helperText={errors.address}
                />

              </div>

            </div>

          </div>

        </div>

        {/* RIGHT */}

        <div>

          <div className="bg-[#1f1f1f] rounded-3xl p-8 sticky top-28">

            <h1 className="text-3xl font-bold mb-8">
              Order Summary
            </h1>

            <div className="space-y-5">

              {cartItems.map(
                (item) => (

                  <div
                    key={item.id}
                    className="flex justify-between"
                  >

                    <div>

                      <p>
                        {item.food.name}
                      </p>

                      <p className="text-gray-400 text-sm">
                        Qty:
                        {" "}
                        {item.quantity}
                      </p>

                    </div>

                    <p>
                      ₹
                      {item.food.price *
                        item.quantity}
                    </p>

                  </div>
                )
              )}

              <Divider
                sx={{
                  bgcolor: "#333",
                }}
              />

              <div className="flex justify-between text-gray-400">

                <p>Subtotal</p>

                <p>
                  ₹
                  {totals.subtotal}
                </p>

              </div>

              <div className="flex justify-between text-gray-400">

                <p>GST</p>

                <p>
                  ₹
                  {totals.gst}
                </p>

              </div>

              <div className="flex justify-between text-gray-400">

                <p>Delivery Fee</p>

                <p>

                  {
                    totals.deliveryFee === 0
                      ? "FREE"
                      : `₹${totals.deliveryFee}`
                  }

                </p>

              </div>

              <Divider
                sx={{
                  bgcolor: "#333",
                }}
              />

              <div className="flex justify-between text-2xl font-bold">

                <p>Total</p>

                <p className="text-pink-500">
                  ₹
                  {totals.total}
                </p>

              </div>

              <Button
                fullWidth
                variant="contained"
                onClick={handlePlaceOrder}
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
                Place Order
              </Button>

            </div>

          </div>

        </div>

      </div>

    </Box>
  );
};

export default Checkout;
