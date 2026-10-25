// src/utils/cartCalculations.js

export const calculateCartTotal = (cartItems) => {

  const subtotal =
    cartItems.reduce(
      (sum, item) =>
        sum + item.totalPrice,
      0
    );

  const gst =
    Math.round(subtotal * 0.05);

  const deliveryFee =
    subtotal > 1000 ? 0 : 49;

  return {
    subtotal,
    gst,
    deliveryFee,
    total:
      subtotal +
      gst +
      deliveryFee
  };
};