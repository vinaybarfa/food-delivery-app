import React from 'react'
import Navbar from '../components/Navbar/Navbar'
import { Route, Routes } from 'react-router-dom'

import SearchPage from '../components/Search/SearchPage'

import Home from '../components/Home/Home'
import RestaurantDetails from '../components/Restaurant/RestaurantDetails'
import Cart from '../components/Cart/Cart'
import Profile from '../components/Profile/Profile'
import Auth from '../components/Auth/Auth'
import Checkout from '../components/Checkout/Checkout'
import OrderSuccess from '../components/Order/OrderSuccess'
import AdminDashboard from '../components/Profile/AdminDashboard'

import MyOrders
    from "../components/Order/MyOrders";

const CustomerRoute = () => {
    return (
        <div>

            <Navbar />

            <Routes>

                {/* Home */}
                <Route
                    path="/"
                    element={<Home />}
                />

                {/* Login / Register */}
                <Route
                    path="/account/:type"
                    element={<Auth />}
                />

                {/* Restaurant Details */}
                <Route
                    path="/restaurant/:city/:title/:id"
                    element={<RestaurantDetails />}
                />

                {/* Cart */}
                <Route
                    path="/cart"
                    element={<Cart />}
                />

                {/* Profile */}
                <Route
                    path="/my-profile/*"
                    element={<Profile />}
                />

                <Route
                    path="/search"
                    element={<SearchPage />}
                />

                <Route
                    path='/checkout'
                    element={<Checkout />}
                />

                <Route
                    path='/order-success'
                    element={<OrderSuccess />}
                />
                <Route
                    path="/admin/restaurant"
                    element={<AdminDashboard />}
                />

                <Route
                    path="/my-orders"
                    element={<MyOrders />}
                />
            </Routes>

        </div>
    )
}

export default CustomerRoute