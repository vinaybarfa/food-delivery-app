import { Card, CardActions, CardContent, CardMedia, IconButton, Typography } from '@mui/material'
import DeleteIcon from '@mui/icons-material/Delete';
import React from 'react'

const EventCard = () => {
    return (
        <div>
            <Card sx={{ width: 345 }}>
                <CardMedia sx={{ height: 345 }} image='https://images.pexels.com/photos/12318206/pexels-photo-12318206.jpeg' />
                <CardContent>
                    <Typography variant='h5 '>
                        Tasty Town
                    </Typography>
                    <Typography variant='body2'>
                        Buy more, save more
                    </Typography>
                    <div className='py-2 space-y-2'>
                        <p>{"Indore"}</p>
                        <p className="text-sm text-blue-500">March 14, 2026 12:00 AM</p>
                        <p className="text-sm text-red-500"> March 15, 2026 12:00 AM</p>
                    </div>
                </CardContent>
                {false && <CardActions>
                    <IconButton>
                        <DeleteIcon/>
                    </IconButton>
                </CardActions> }
            </Card>
        </div>
    )
}

export default EventCard
