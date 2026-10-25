import React from 'react'

import Card from '@mui/material/Card'

import Chip from '@mui/material/Chip'

import IconButton from '@mui/material/IconButton'

import FavoriteIcon from '@mui/icons-material/Favorite'

import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder'

import StarIcon from '@mui/icons-material/Star'

import AccessTimeFilledIcon from '@mui/icons-material/AccessTimeFilled'

import LocationOnIcon from '@mui/icons-material/LocationOn'

import { useNavigate } from 'react-router-dom'

import { useDispatch, useSelector } from 'react-redux'

import { addToFavorite } from '../../State/Authentication/Action'

import { isPresentInFavorites } from '../../config/logic'

const RestaurantCard = ({ item }) => {

    const dispatch = useDispatch()

    const navigate = useNavigate()

    const auth = useSelector(
        (state) => state.auth
    )

    if (!item) return null

    // ================= FAVORITE =================

    const handleAddToFavorite = (e) => {

        e.stopPropagation()

        const jwt = auth?.jwt

        dispatch(
            addToFavorite({
                restaurantId: item.id,
                jwt,
            })
        )
    }

    // ================= NAVIGATION =================

    const handleNavigateToRestaurant = () => {

        if (item.open) {

            navigate(
                `/restaurant/${item.address?.city}/${item.name}/${item.id}`
            )
        }
    }

    return (

        <Card
            onClick={handleNavigateToRestaurant}
            className={`

                w-[340px]
                bg-[#1f1f1f]
                rounded-3xl
                overflow-hidden
                transition-all
                duration-300
                hover:scale-[1.03]
                hover:shadow-2xl

                ${item.open
                    ? 'cursor-pointer'
                    : 'cursor-not-allowed opacity-80'
                }

            `}
        >

            {/* ================= IMAGE ================= */}

            <div className='relative'>

                <img
                    className='w-full h-[240px] object-cover'
                    src={
                        item.images?.[0] ||
                        "https://images.unsplash.com/photo-1504674900247-0877df9cc836"
                    }
                    alt={item.name}
                />

                {/* OVERLAY */}

                <div className='absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent'></div>

                {/* OPEN/CLOSED */}

                <Chip
                    size='small'
                    label={item.open ? "Open" : "Closed"}
                    color={item.open ? "success" : "error"}
                    className='absolute top-4 left-4'
                />

                {/* FAVORITE */}

                <div className='absolute top-4 right-4 bg-black/40 backdrop-blur-md rounded-full'>

                    <IconButton onClick={handleAddToFavorite}>

                        {isPresentInFavorites(
                            auth?.favorites || [],
                            item
                        ) ? (

                            <FavoriteIcon
                                sx={{
                                    color: "#ec4899",
                                }}
                            />

                        ) : (

                            <FavoriteBorderIcon
                                sx={{
                                    color: "white",
                                }}
                            />

                        )}

                    </IconButton>

                </div>

                {/* BOTTOM TEXT */}

                <div className='absolute bottom-5 left-5 right-5'>

                    <h1 className='text-2xl font-bold text-white'>
                        {item.name}
                    </h1>

                    <p className='text-gray-300 mt-1 line-clamp-2'>
                        {item.description ||
                            "Fresh vegetarian meals and healthy food options"}
                    </p>

                </div>

            </div>

            {/* ================= CONTENT ================= */}

            <div className='p-5 space-y-5'>

                {/* CUISINES */}

                <div className='flex flex-wrap gap-2'>

                    <div className='bg-pink-600/20 text-pink-500 px-3 py-1 rounded-full text-sm'>
                        Veg
                    </div>

                    <div className='bg-pink-600/20 text-pink-500 px-3 py-1 rounded-full text-sm'>
                        Healthy
                    </div>

                    <div className='bg-pink-600/20 text-pink-500 px-3 py-1 rounded-full text-sm'>
                        Fast Delivery
                    </div>

                </div>

                {/* INFO */}

                <div className='flex items-center justify-between'>

                    {/* RATING */}

                    <div className='flex items-center gap-2 text-yellow-400'>

                        <StarIcon
                            sx={{
                                fontSize: "1.2rem",
                            }}
                        />

                        <span className='font-semibold'>
                            {item.rating || "4.5"}
                        </span>

                    </div>

                    {/* DELIVERY */}

                    <div className='flex items-center gap-2 text-gray-400'>

                        <AccessTimeFilledIcon
                            sx={{
                                fontSize: "1rem",
                            }}
                        />

                        <span>
                            25-35 min
                        </span>

                    </div>

                </div>

                {/* LOCATION */}

                <div className='flex items-center gap-2 text-gray-500 text-sm'>

                    <LocationOnIcon
                        sx={{
                            fontSize: "1rem",
                        }}
                    />

                    <span>
                        {item.address?.city || "Indore"}
                    </span>

                </div>

            </div>

        </Card>
    )
}

export default RestaurantCard