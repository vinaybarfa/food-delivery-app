import React, { useEffect } from 'react'

import "./Home.css"

import MutliItemCarousel from './MutliItemCarousel'

import RestaurantCard from '../Restaurant/RestaurantCard'

import { useNavigate } from 'react-router-dom'

import { useDispatch, useSelector } from 'react-redux'

import { getAllRestaurantsAction } from '../../State/Restaurant/Action'

const categories = [
    "Pizza",
    "Burger",
    "Pasta",
    "Healthy",
    "Desserts",
    "Chinese",
    "South Indian",
]

const offers = [

    {
        title: "50% OFF",

        subtitle: "On First Order",

        image:
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
    },

    {
        title: "Free Delivery",

        subtitle: "Above ₹499",

        image:
            "https://images.unsplash.com/photo-1490645935967-10de6ba17061",
    },

    {
        title: "Healthy Veg Meals",

        subtitle: "Fresh & Organic",

        image:
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
    },
]

const Home = () => {

    const dispatch = useDispatch()

    /*
     * ACCESS TOKEN
     */
    const jwt =
        localStorage.getItem(
            "accessToken"
        )

    /*
     * CORRECT SELECTOR
     */
    const restaurant = useSelector(
        (store) => store.restaurant
    )

    const navigate = useNavigate()

    /*
     * FETCH RESTAURANTS
     */
    useEffect(() => {

        dispatch(
            getAllRestaurantsAction(jwt)
        )

    }, [dispatch, jwt])

    return (

        <div className='bg-black text-white pb-20'>

            {/* ================= HERO SECTION ================= */}

            <section className='banner relative flex flex-col justify-center items-center h-[85vh]'>

                <div className='absolute inset-0 bg-black/60 z-0'></div>

                <div className='z-10 text-center px-5 max-w-4xl'>

                    <h1 className='text-5xl lg:text-7xl font-bold leading-tight'>
                        Delicious Food Delivered
                    </h1>

                    <p className='text-gray-300 text-xl lg:text-2xl mt-6'>
                        Fresh vegetarian meals from top restaurants near you.
                    </p>

                    <button

                        onClick={() =>
                            navigate("/search")
                        }

                        className='mt-10 bg-pink-600 hover:bg-pink-700 transition-all duration-300 px-8 py-4 rounded-2xl text-lg font-semibold'
                    >
                        Explore Food
                    </button>

                </div>

            </section>

            {/* ================= CATEGORIES ================= */}

            <section className='px-5 lg:px-20 py-14'>

                <h1 className='text-3xl font-bold mb-8'>
                    Categories
                </h1>

                <div className='flex gap-5 overflow-x-auto scrollbar-hide'>

                    {categories.map((item, index) => (

                        <div

                            key={index}

                            className='min-w-[180px] bg-[#1f1f1f] hover:bg-pink-600 transition-all duration-300 cursor-pointer rounded-2xl p-6 text-center'
                        >

                            <h2 className='text-lg font-semibold'>
                                {item}
                            </h2>

                        </div>

                    ))}

                </div>

            </section>

            {/* ================= OFFERS ================= */}

            <section className='px-5 lg:px-20 pb-16'>

                <h1 className='text-3xl font-bold mb-8'>
                    Best Offers
                </h1>

                <div className='grid md:grid-cols-3 gap-8'>

                    {offers.map((offer, index) => (

                        <div

                            key={index}

                            className='relative rounded-3xl overflow-hidden h-72 group'
                        >

                            <img

                                src={offer.image}

                                alt="offer"

                                className='w-full h-full object-cover group-hover:scale-110 transition-all duration-500'
                            />

                            <div className='absolute inset-0 bg-black/50 flex flex-col justify-end p-6'>

                                <h1 className='text-4xl font-bold text-pink-500'>
                                    {offer.title}
                                </h1>

                                <p className='text-lg mt-2'>
                                    {offer.subtitle}
                                </p>

                            </div>

                        </div>

                    ))}

                </div>

            </section>

            {/* ================= TOP MEALS ================= */}

            <section className='px-5 lg:px-20 py-10'>

                <h1 className='text-3xl font-bold mb-10'>
                    Top Meals
                </h1>

                <MutliItemCarousel />

            </section>

            {/* ================= RESTAURANTS ================= */}

            <section className='px-5 lg:px-20 pt-10'>

                <div className='flex items-center justify-between mb-10'>

                    <h1 className='text-3xl font-bold'>
                        Popular Restaurants
                    </h1>

                    <button
                        className='bg-pink-600 hover:bg-pink-700 transition-all duration-300 px-5 py-2 rounded-xl'
                    >
                        View All
                    </button>

                </div>

                {/* LOADING */}

                {restaurant.loading && (

                    <div className='text-center py-20'>

                        <h1 className='text-2xl text-gray-400'>
                            Loading Restaurants...
                        </h1>

                    </div>
                )}

                {/* ERROR */}

                {restaurant.error && (

                    <div className='text-center py-20'>

                        <h1 className='text-2xl text-red-500'>
                            Failed To Load Restaurants
                        </h1>

                    </div>
                )}

                {/* RESTAURANTS */}

                <div className='flex flex-wrap items-center justify-center gap-8'>

                    {

                        restaurant?.restaurants?.length > 0 &&

                        restaurant.restaurants.map(
                            (item, index) => (

                                <RestaurantCard
                                    key={index}
                                    item={item}
                                />
                            )
                        )
                    }

                </div>

            </section>

            {/* ================= APP DOWNLOAD ================= */}

            <section className='mt-20 bg-[#111111] py-20 px-5 lg:px-20'>

                <div className='max-w-6xl mx-auto grid lg:grid-cols-2 gap-10 items-center'>

                    <div>

                        <h1 className='text-5xl font-bold leading-tight'>
                            Download Our App
                        </h1>

                        <p className='text-gray-400 mt-5 text-lg'>
                            Order food faster with our mobile app.
                        </p>

                        <div className='flex gap-4 mt-8'>

                            <button className='bg-pink-600 px-6 py-3 rounded-xl'>
                                Google Play
                            </button>

                            <button className='bg-white text-black px-6 py-3 rounded-xl'>
                                App Store
                            </button>

                        </div>

                    </div>

                    <img
                        src="https://images.unsplash.com/photo-1523475472560-d2df97ec485c"
                        alt="mobile-app"
                        className='rounded-3xl'
                    />

                </div>

            </section>

        </div>
    )
}

export default Home