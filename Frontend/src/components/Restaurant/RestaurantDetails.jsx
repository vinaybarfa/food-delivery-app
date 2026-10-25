import React, { useState } from 'react'

import {
  Avatar,
  Divider,
  Rating,
} from '@mui/material'

import AddIcon from '@mui/icons-material/Add'

const categories = [
  "Recommended",
  "Pizza",
  "Pasta",
  "Healthy",
  "Desserts",
]

const foods = [
  {
    id: 1,
    name: "Paneer Pizza",
    description:
      "Cheesy loaded pizza with paneer toppings",
    price: 299,
    image:
      "https://images.unsplash.com/photo-1513104890138-7c749659a591",
    rating: 4.5,
  },

  {
    id: 2,
    name: "White Sauce Pasta",
    description:
      "Italian creamy pasta with herbs",
    price: 199,
    image:
      "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9",
    rating: 4.4,
  },

  {
    id: 3,
    name: "Veg Burger",
    description:
      "Crispy burger with fresh vegetables",
    price: 189,
    image:
      "https://images.unsplash.com/photo-1520072959219-c595dc870360",
    rating: 4.2,
  },
]

const RestaurantDetails = () => {

  const [selectedCategory, setSelectedCategory] =
    useState("Recommended")

  return (

    <div className='bg-black text-white min-h-screen'>

      {/* ================= BANNER ================= */}

      <section className='relative h-[420px]'>

        <img
          src="https://images.unsplash.com/photo-1504674900247-0877df9cc836"
          alt=""
          className='w-full h-full object-cover'
        />

        <div className='absolute inset-0 bg-black/60'></div>

        <div className='absolute bottom-10 left-10'>

          <h1 className='text-5xl font-bold'>
            Green Leaf Restaurant
          </h1>

          <div className='flex items-center gap-5 mt-5'>

            <div className='bg-green-600 px-4 py-2 rounded-full font-semibold'>
              Pure Veg
            </div>

            <div className='flex items-center gap-2'>

              <Rating
                value={4.5}
                precision={0.5}
                readOnly
              />

              <span className='text-lg'>
                4.5
              </span>

            </div>

          </div>

          <p className='text-gray-300 mt-5 max-w-2xl'>
            Healthy vegetarian meals prepared
            with fresh ingredients and authentic taste.
          </p>

        </div>

      </section>

      {/* ================= CATEGORY TABS ================= */}

      <section className='sticky top-20 z-40 bg-black border-b border-gray-800'>

        <div className='flex gap-5 overflow-x-auto px-5 lg:px-10 py-5'>

          {categories.map((category) => (

            <button
              key={category}
              onClick={() =>
                setSelectedCategory(category)
              }
              className={`

                px-6 py-3 rounded-xl transition-all duration-300 whitespace-nowrap

                ${
                  selectedCategory === category
                    ? 'bg-pink-600 text-white'
                    : 'bg-[#1f1f1f] hover:bg-[#2a2a2a]'
                }

              `}
            >
              {category}
            </button>

          ))}

        </div>

      </section>

      {/* ================= CONTENT ================= */}

      <section className='grid lg:grid-cols-3 gap-10 px-5 lg:px-10 py-10'>

        {/* ================= LEFT ================= */}

        <div className='lg:col-span-2 space-y-8'>

          {foods.map((food) => (

            <div
              key={food.id}
              className='bg-[#1f1f1f] rounded-3xl overflow-hidden p-5 flex gap-5 hover:shadow-2xl transition-all duration-300'
            >

              {/* IMAGE */}

              <img
                src={food.image}
                alt=""
                className='w-36 h-36 object-cover rounded-2xl'
              />

              {/* CONTENT */}

              <div className='flex-1 flex flex-col justify-between'>

                <div>

                  <div className='flex justify-between items-start'>

                    <div>

                      <h1 className='text-2xl font-bold'>
                        {food.name}
                      </h1>

                      <p className='text-gray-400 mt-2'>
                        {food.description}
                      </p>

                    </div>

                    <div className='text-pink-500 text-2xl font-bold'>
                      ₹{food.price}
                    </div>

                  </div>

                  <div className='flex items-center gap-3 mt-5'>

                    <Rating
                      value={food.rating}
                      precision={0.5}
                      readOnly
                      size='small'
                    />

                    <span className='text-gray-400'>
                      {food.rating}
                    </span>

                  </div>

                </div>

                {/* BUTTON */}

                <div className='mt-5'>

                  <button
                    className='bg-pink-600 hover:bg-pink-700 transition-all duration-300 px-6 py-3 rounded-xl flex items-center gap-2'
                  >

                    <AddIcon />

                    Add To Cart

                  </button>

                </div>

              </div>

            </div>

          ))}

        </div>

        {/* ================= RIGHT ================= */}

        <div>

          <div className='bg-[#1f1f1f] p-6 rounded-3xl sticky top-36'>

            <h1 className='text-3xl font-bold mb-8'>
              Restaurant Info
            </h1>

            <div className='space-y-5'>

              <div>

                <p className='text-gray-400'>
                  Delivery Time
                </p>

                <h2 className='text-xl font-semibold mt-1'>
                  25 - 35 min
                </h2>

              </div>

              <Divider sx={{ bgcolor: "#333" }} />

              <div>

                <p className='text-gray-400'>
                  Address
                </p>

                <h2 className='text-lg mt-1'>
                  Vijay Nagar, Indore
                </h2>

              </div>

              <Divider sx={{ bgcolor: "#333" }} />

              <div>

                <p className='text-gray-400'>
                  Opening Hours
                </p>

                <h2 className='text-lg mt-1'>
                  10:00 AM - 11:00 PM
                </h2>

              </div>

              <Divider sx={{ bgcolor: "#333" }} />

              <div>

                <p className='text-gray-400'>
                  Customer Reviews
                </p>

                <div className='space-y-5 mt-5'>

                  {[1, 2].map((item) => (

                    <div
                      key={item}
                      className='flex gap-4'
                    >

                      <Avatar />

                      <div>

                        <h1 className='font-semibold'>
                          Customer
                        </h1>

                        <p className='text-gray-400 text-sm mt-1'>
                          Amazing vegetarian food and fast delivery.
                        </p>

                      </div>

                    </div>

                  ))}

                </div>

              </div>

            </div>

          </div>

        </div>

      </section>

    </div>
  )
}

export default RestaurantDetails