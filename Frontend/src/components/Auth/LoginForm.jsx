import { Button, TextField, Typography } from '@mui/material'
import { Field, Form, Formik } from 'formik'
import React from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { loginUser } from '../../State/Authentication/Action'

const initialValues = {
    email: "",
    password: ""
}
const LoginForm = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleSubmit = (values) => {

        console.log("LOGIN VALUES", values);

        dispatch(
            loginUser({
                userData: values,
                navigate
            })
        );
    }
    return (
        <div>
            <Typography variant='hs' className='text-center '>Login
            </Typography>

            <Formik onSubmit={handleSubmit} initialValues={initialValues}>
                {({ errors, touched }) => (

                    <Form>
                        <Field
                            as={TextField}
                            name="email"
                            label="email"
                            fullWidth
                            variant="outlined"
                            error={touched.email && Boolean(errors.email)}
                            helperText={touched.email && errors.email}
                            margin="normal"
                        />

                        <Field as={TextField} name="password" label="Password" type="password" fullWidth variant="outlined" error={touched.password && Boolean(errors.password)} helperText={touched.password && errors.password} margin="normal" />
                        <Button sx={{ mt: 2, padding: "0.5rem" }} fullWidth type='submit' variant='contained'>Login</Button>
                    </Form>
                )}
            </Formik>
            <Typography variant='body2' align='center' sx={{ mt: 3 }}>
                Don't have an account?
                <Button size='small' className='' onClick={() => navigate("/account/register")}>
                    register
                </Button>
            </Typography>
        </div>
    )
}

export default LoginForm
