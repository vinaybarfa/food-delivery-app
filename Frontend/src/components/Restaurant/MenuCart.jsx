import React, { useState } from 'react'

import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Button,
    Checkbox,
    FormControlLabel,
    FormGroup,
    Rating,
} from '@mui/material'

import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart'

import { useDispatch } from 'react-redux'

const MenuCart = ({ item }) => {

    const dispatch = useDispatch()

    const [selectedIngredients, setSelectedIngredients] =
        useState([])

    // ================= INGREDIENTS =================

    const ingredientsData = [

        {
            category: "Extra Toppings",

            ingredients: [
                "Cheese",
                "Olives",
                "Paneer",
            ],
        },

        {
            category: "Sauces",

            ingredients: [
                "Mayo",
                "Spicy Sauce",
                "Mint Sauce",
            ],
        },

    ]

    // ================= CHECKBOX =================

    const handleCheckBoxChange = (ingredient) => {

        setSelectedIngredients((prev) =>

            prev.includes(ingredient)

                ? prev.filter((i) => i !== ingredient)

                : [...prev, ingredient]
        )
    }

    // ================= ADD TO CART =================

    const handleAddToCart = () => {

        dispatch({

            type: "ADD_TO_CART",

            payload: {

                ...item,

                selectedIngredients,

            },
        })
    }

    return (

        <Accordion
            sx={{
                background: "#1f1f1f",
                color: "white",
                borderRadius: "20px !important",
                overflow: "hidden",
                mb: 3,

                "&:before": {
                    display: "none",
                },
            }}
        >

            {/* ================= HEADER ================= */}

            <AccordionSummary
                expandIcon={
                    <ExpandMoreIcon
                        sx={{ color: "white" }}
                    />
                }
            >

                <div className='flex flex-col lg:flex-row gap-5 w-full'>

                    {/* IMAGE */}

                    <div className='relative'>

                        <img
                            className='w-full lg:w-40 h-40 object-cover rounded-2xl'
                            src={item.image}
                            alt=""
                        />

                        {/* VEG BADGE */}

                        <div className='absolute top-3 left-3 bg-green-600 px-3 py-1 rounded-full text-xs font-semibold'>
                            Veg
                        </div>

                    </div>

                    {/* CONTENT */}

                    <div className='flex-1 flex flex-col justify-between'>

                        <div>

                            <div className='flex justify-between items-start'>

                                <div>

                                    <h1 className='text-2xl font-bold'>
                                        {item.name}
                                    </h1>

                                    <p className='text-gray-400 mt-2'>
                                        {item.description}
                                    </p>

                                </div>

                                <h1 className='text-pink-500 text-2xl font-bold'>
                                    ₹{item.price}
                                </h1>

                            </div>

                            {/* RATING */}

                            <div className='flex items-center gap-3 mt-4'>

                                <Rating
                                    value={item.rating}
                                    precision={0.5}
                                    readOnly
                                    size='small'
                                />

                                <span className='text-gray-400'>
                                    {item.rating}
                                </span>

                            </div>

                        </div>

                    </div>

                </div>

            </AccordionSummary>

            {/* ================= DETAILS ================= */}

            <AccordionDetails>

                <div className='space-y-8'>

                    {/* INGREDIENT GROUPS */}

                    <div className='grid md:grid-cols-2 gap-8'>

                        {ingredientsData.map((group, index) => (

                            <div key={index}>

                                <h1 className='font-semibold text-lg mb-4'>
                                    {group.category}
                                </h1>

                                <FormGroup>

                                    {group.ingredients.map(
                                        (ingredient, i) => (

                                            <FormControlLabel
                                                key={i}

                                                control={
                                                    <Checkbox

                                                        checked={
                                                            selectedIngredients.includes(
                                                                ingredient
                                                            )
                                                        }

                                                        onChange={() =>
                                                            handleCheckBoxChange(
                                                                ingredient
                                                            )
                                                        }

                                                        sx={{
                                                            color: "#ec4899",

                                                            "&.Mui-checked": {
                                                                color: "#ec4899",
                                                            },
                                                        }}
                                                    />
                                                }

                                                label={ingredient}
                                            />
                                        )
                                    )}

                                </FormGroup>

                            </div>
                        ))}

                    </div>

                    {/* ================= BUTTON ================= */}

                    <Button
                        fullWidth
                        variant='contained'
                        startIcon={
                            <AddShoppingCartIcon />
                        }

                        onClick={handleAddToCart}

                        sx={{
                            background: "#ec4899",
                            py: 1.5,
                            borderRadius: "14px",
                            fontWeight: "bold",
                            fontSize: "1rem",

                            "&:hover": {
                                background: "#db2777",
                            },
                        }}
                    >
                        Add To Cart
                    </Button>

                </div>

            </AccordionDetails>

        </Accordion>
    )
}

export default MenuCart