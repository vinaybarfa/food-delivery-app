import React, { useEffect } from 'react'

import { ThemeProvider } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'

import { darkTheme } from './Theme/DarkTeme'

import CustomerRoute from './Routers/CustomerRoute'

import { getUser } from "./State/Authentication/Action";

import { useDispatch, useSelector } from 'react-redux'

import { loginUser } from "./State/Authentication/Action";

const App = () => {

  const dispatch = useDispatch()

  const auth = useSelector((store) => store.auth)

  useEffect(() => {

    const token =
      auth.jwt || localStorage.getItem("jwt")

    if (token && !auth.user) {
      dispatch(getUser(token))
    }

  }, [auth.jwt])

  return (

    <ThemeProvider theme={darkTheme}>

      <CssBaseline />

      <CustomerRoute />

    </ThemeProvider>
  )
}

export default App