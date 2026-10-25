
import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography
} from "@mui/material";

import { Field, Form, Formik } from "formik";

import React from "react";

import { useDispatch } from "react-redux";

import { useNavigate } from "react-router-dom";

import { registerUser } from "../../State/Authentication/Action";

const initialValues = {
  fullName: "",
  email: "",
  password: "",
  role: "ROLE_CUSTOMER"
};

const RegisterForm = () => {

  const navigate = useNavigate();

  const dispatch = useDispatch();

  const handleSubmit = (values) => {

    console.log("form value : ", values);

    dispatch(
      registerUser({
        userData: values,
        navigate
      })
    );
  };

  return (

    <div>

      <Typography
        variant="h5"
        className="text-center"
      >
        Register
      </Typography>

      <Formik
        onSubmit={handleSubmit}
        initialValues={initialValues}
      >

        {({ errors, touched }) => (

          <Form>

            <Field
              as={TextField}
              name="fullName"
              label="Full Name"
              fullWidth
              variant="outlined"
              margin="normal"
              error={
                touched.fullName &&
                Boolean(errors.fullName)
              }
              helperText={
                touched.fullName &&
                errors.fullName
              }
            />

            <Field
              as={TextField}
              name="email"
              label="Email"
              type="email"
              fullWidth
              variant="outlined"
              margin="normal"
              error={
                touched.email &&
                Boolean(errors.email)
              }
              helperText={
                touched.email &&
                errors.email
              }
            />

            <Field
              as={TextField}
              name="password"
              label="Password"
              type="password"
              fullWidth
              variant="outlined"
              margin="normal"
              error={
                touched.password &&
                Boolean(errors.password)
              }
              helperText={
                touched.password &&
                errors.password
              }
            />

            <FormControl
              fullWidth
              margin="normal"
            >

              <InputLabel id="role-select-label">
                Role
              </InputLabel>

              <Field
                as={Select}
                name="role"
                label="Role"
                labelId="role-select-label"
              >

                <MenuItem value="ROLE_CUSTOMER">
                  Customer
                </MenuItem>

                <MenuItem value="ROLE_RESTAURANT_OWNER">
                  Restaurant Owner
                </MenuItem>

              </Field>

            </FormControl>

            <Button
              sx={{
                mt: 2,
                padding: "0.7rem"
              }}
              fullWidth
              type="submit"
              variant="contained"
            >
              Register
            </Button>

          </Form>
        )}

      </Formik>

      <Typography
        variant="body2"
        align="center"
        sx={{ mt: 3 }}
      >

        Already have an account?

        <Button
          size="small"
          onClick={() =>
            navigate("/account/login")
          }
        >
          Login
        </Button>

      </Typography>

    </div>
  );
};

export default RegisterForm;
