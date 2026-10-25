import React from 'react'

import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline'
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline'

import {
  Chip,
  IconButton,
} from '@mui/material'

import { useDispatch, useSelector } from 'react-redux'

import {
  removeCartItem,
  updateCartItem,
} from '../State/Cart/Action'

const CartItem = ({ item }) => {

  const dispatch = useDispatch()

  const jwt = localStorage.getItem("jwt")

  // ================= INCREASE =================

  const handleIncrease = () => {

    dispatch(updateCartItem({
      reqData: {
        data: {
          cartItemId: item.id,
          quantity: item.quantity + 1,
        },
        jwt,
      }
    }))
  }

  // ================= DECREASE =================

  const handleDecrease = () => {

    if (item.quantity <= 1) {

      dispatch(removeCartItem({
        cartItemId: item.id,
        jwt,
      }))

      return
    }

    dispatch(updateCartItem({
      reqData: {
        data: {
          cartItemId: item.id,
          quantity: item.quantity - 1,
        },
        jwt,
      }
    }))
  }

  return (

    <div className='bg-[#1f1f1f] rounded-2xl p-4 shadow-lg'>

      <div className='flex items-center gap-5'>

        {/* IMAGE */}

        <img
          className='w-24 h-24 object-cover rounded-xl'
          src={item.food?.images[0]}
          alt={item.food?.name}
        />

        {/* CONTENT */}

        <div className='flex-1'>

          <div className='flex justify-between'>

            <div>

              <h1 className='text-lg font-semibold text-white'>
                {item.food?.name}
              </h1>

              <p className='text-gray-400 text-sm mt-1'>
                {item.food?.description}
              </p>

            </div>

            <h1 className='text-pink-500 font-bold text-lg'>
              ₹{item.totalPrice}
            </h1>

          </div>

          {/* QUANTITY */}

          <div className='flex items-center justify-between mt-4'>

            <div className='flex items-center gap-2'>

              <IconButton onClick={handleDecrease}>
                <RemoveCircleOutlineIcon sx={{ color: "white" }} />
              </IconButton>

              <div className='text-white font-semibold'>
                {item.quantity}
              </div>

              <IconButton onClick={handleIncrease}>
                <AddCircleOutlineIcon sx={{ color: "white" }} />
              </IconButton>

            </div>

          </div>

          {/* INGREDIENTS */}

          <div className='flex gap-2 flex-wrap mt-4'>

            {item.ingredients?.map((ingredient, index) => (

              <Chip
                key={index}
                label={ingredient.name}
                sx={{
                  background: "#ec4899",
                  color: "white",
                }}
              />

            ))}

          </div>

        </div>

      </div>

    </div>
  )
}

export default CartItem