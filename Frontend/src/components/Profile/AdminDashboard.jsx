import React, { useEffect } from "react";

import {
    Box,
    Typography,
    Card,
    CardContent,
    Chip,
    CircularProgress,
} from "@mui/material";

import {
    useDispatch,
    useSelector,
} from "react-redux";

import {
    getAdminRestaurants,
} from "../../State/AdminRestaurant/Action";

const AdminDashboard = () => {

    const dispatch = useDispatch();

    /*
     * GET ADMIN RESTAURANT STATE
     */
    const {
        restaurants,
        loading,
        error,
    } = useSelector(
        (store) => store.adminRestaurant
    );

    /*
     * DEBUG LOG
     */

    console.log("RESTAURANTS => ", restaurants);
    console.log("LOADING => ", loading);
    console.log("ERROR => ", error);


    /*
     * FETCH RESTAURANTS
     */
    useEffect(() => {

        console.log(
            "Admin Dashboard Loaded"
        );

        dispatch(
            getAdminRestaurants()
        );

    }, [dispatch]);

    /*
     * LOADING
     */
    if (loading) {

        return (

            <div className="flex justify-center items-center h-screen bg-black">

                <CircularProgress />

            </div>
        );
    }

    /*
     * ERROR
     */
    if (error) {

        return (

            <div className="flex justify-center items-center h-screen bg-black text-white">

                <Typography variant="h6">

                    {typeof error === "string"
                        ? error
                        : "Something went wrong"}

                </Typography>

            </div>
        );
    }

    return (

        <Box className="min-h-screen bg-black text-white p-5 lg:p-10">

            {/* ================= TITLE ================= */}

            <Typography
                variant="h4"
                fontWeight="bold"
                mb={5}
            >
                Restaurant Dashboard
            </Typography>

            {/* ================= EMPTY ================= */}

            {
                restaurants?.length === 0 && (

                    <Typography variant="h6">

                        No Restaurants Found

                    </Typography>
                )
            }

            {/* ================= RESTAURANTS ================= */}

            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">

                {
                    Array.isArray(restaurants) && restaurants.map(
                        (restaurant) => (

                            <Card
                                key={restaurant.id}
                                sx={{
                                    background: "#1f1f1f",
                                    color: "white",
                                    borderRadius: "20px",
                                    overflow: "hidden",
                                }}
                            >

                                {/* ================= IMAGE ================= */}

                                <img
                                    src={
                                        restaurant.images?.[0]
                                        || "https://via.placeholder.com/400"
                                    }
                                    alt={restaurant.name}
                                    className="w-full h-52 object-cover"
                                />

                                <CardContent>

                                    {/* ================= HEADER ================= */}

                                    <div className="flex items-center justify-between">

                                        <Typography
                                            variant="h6"
                                            fontWeight="bold"
                                        >
                                            {restaurant.name}
                                        </Typography>

                                        <Chip
                                            label={
                                                restaurant.isOpen
                                                    ? "Open"
                                                    : "Closed"
                                            }
                                            color={
                                                restaurant.isOpen
                                                    ? "success"
                                                    : "error"
                                            }
                                        />

                                    </div>

                                    {/* ================= DESCRIPTION ================= */}

                                    <Typography
                                        sx={{
                                            color: "#9ca3af",
                                            mt: 1,
                                        }}
                                    >
                                        {restaurant.description}
                                    </Typography>

                                    {/* ================= CUISINE ================= */}

                                    <Typography
                                        sx={{
                                            mt: 2,
                                            color: "#ec4899",
                                            fontWeight: "bold",
                                        }}
                                    >
                                        {restaurant.cuisineType}
                                    </Typography>

                                    {/* ================= RATING ================= */}

                                    <Typography
                                        sx={{
                                            mt: 1,
                                            color: "#facc15",
                                        }}
                                    >
                                        ⭐ {restaurant.rating}
                                    </Typography>

                                    {/* ================= TIMING ================= */}

                                    <Typography
                                        sx={{
                                            mt: 1,
                                            color: "#d1d5db",
                                        }}
                                    >
                                        {restaurant.openingHours}
                                    </Typography>

                                </CardContent>

                            </Card>
                        )
                    )
                }

            </div>

        </Box>
    );
};

export default AdminDashboard;
