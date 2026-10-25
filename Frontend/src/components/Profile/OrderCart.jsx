import { Button, Card } from '@mui/material'
import React from 'react'

const OrderCart = () => {
    return (
        <Card className='flex justify-between items-center p-5'>
            <div className='flex items-center space-x-5'>
                <img className='h-16 w-16'
                    src="https://images.pexels.com/photos/1028433/pexels-photo-1028433.jpeg" alt="" />
                <div>
                    <p>Pizza</p>
                    <p>₹300</p>
                </div>
            </div>
            <div>
                <Button  className='cursor-not-allowed'>completed</Button>
            </div>
        </Card>
    )
}

export default OrderCart
