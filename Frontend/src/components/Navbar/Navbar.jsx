import "./Navbar.css";

import React, { useState } from "react";

import {
  Avatar,
  Badge,
  Box,
  IconButton,
  InputBase,
} from "@mui/material";

import SearchIcon from "@mui/icons-material/Search";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import PersonIcon from "@mui/icons-material/Person";

import { pink } from "@mui/material/colors";

import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

const Navbar = () => {

  const navigate = useNavigate();

  const auth = useSelector(
    (store) => store.auth
  );

  const { cartItems } = useSelector(
    (store) => store.cart
  );

  const [searchQuery, setSearchQuery] =
    useState("");

  // ================= CART COUNT =================

  const cartCount = cartItems.reduce(
    (total, item) =>
      total + item.quantity,
    0
  );

  // ================= SEARCH =================

  const handleSearch = () => {

    const query =
      searchQuery.trim();

    if (!query) return;

    navigate(
      `/search?q=${query}`
    );
  };

  // ================= PROFILE =================

  const handleProfileNavigation =
    () => {

      if (!auth.user) {

        navigate(
          "/account/login"
        );

        return;
      }

      if (
        auth.user.role ===
        "CUSTOMER"
      ) {

        navigate(
          "/my-profile"
        );

      } else {

        navigate(
          "/admin/restaurant"
        );
      }
    };

  // ================= USER LETTER =================

  const avatarLetter =
    auth?.user?.fullName
      ?.charAt(0)
      ?.toUpperCase() || "U";

  return (

    <Box
      className="sticky top-0 z-50"
      sx={{
        backdropFilter:
          "blur(10px)",

        background:
          "rgba(233,30,99,0.95)",

        borderBottom:
          "1px solid rgba(255,255,255,0.1)",
      }}
    >

      <div className="max-w-7xl mx-auto px-4 lg:px-10 py-3 flex items-center justify-between gap-4">

        {/* ================= LOGO ================= */}

        <div
          onClick={() =>
            navigate("/")
          }
          className="cursor-pointer select-none"
        >

          <h1 className="text-2xl md:text-3xl font-bold text-white tracking-wide">

            Zosh Food

          </h1>

        </div>

        {/* ================= SEARCH BAR ================= */}

        <div className="hidden md:flex flex-1 justify-center px-6">

          <div
            className="w-full max-w-2xl flex items-center"
            style={{
              background:
                "rgba(255,255,255,0.16)",

              border:
                "1px solid rgba(255,255,255,0.18)",

              borderRadius:
                "14px",

              padding:
                "6px 12px",
            }}
          >

            <SearchIcon
              sx={{
                color:
                  "rgba(255,255,255,0.8)",

                fontSize:
                  "1.4rem",
              }}
            />

            <InputBase
              fullWidth
              placeholder="Search restaurants, food, dishes..."
              value={
                searchQuery
              }
              onChange={(e) =>
                setSearchQuery(
                  e.target.value
                )
              }
              onKeyDown={(e) => {

                if (
                  e.key ===
                  "Enter"
                ) {

                  handleSearch();
                }
              }}
              sx={{
                ml: 1.5,
                color: "white",
                fontSize:
                  "0.95rem",
              }}
            />

            <button
              onClick={
                handleSearch
              }
              className="bg-white text-pink-600 px-4 py-2 rounded-lg text-sm font-semibold hover:scale-105 transition-all duration-200"
            >

              Search

            </button>

          </div>

        </div>

        {/* ================= RIGHT ================= */}

        <div className="flex items-center gap-3 md:gap-5">

          {/* Mobile Search */}

          <IconButton
            className="md:hidden"
            onClick={
              handleSearch
            }
          >

            <SearchIcon
              sx={{
                color:
                  "white",
              }}
            />

          </IconButton>

          {/* Cart */}

          <IconButton
            onClick={() =>
              navigate(
                "/cart"
              )
            }
          >

            <Badge
              badgeContent={
                cartCount
              }
              color="warning"
            >

              <ShoppingCartIcon
                sx={{
                  color:
                    "white",

                  fontSize:
                    "1.8rem",
                }}
              />

            </Badge>

          </IconButton>

          {/* User */}

          {auth.user ? (

            <Avatar
              onClick={
                handleProfileNavigation
              }
              sx={{
                bgcolor:
                  "white",

                color:
                  pink.A400,

                width: 40,
                height: 40,

                cursor:
                  "pointer",

                fontWeight:
                  "bold",

                transition:
                  "0.3s",

                "&:hover": {
                  transform:
                    "scale(1.08)",
                },
              }}
            >

              {
                avatarLetter
              }

            </Avatar>

          ) : (

            <IconButton
              onClick={() =>
                navigate(
                  "/account/login"
                )
              }
            >

              <PersonIcon
                sx={{
                  color:
                    "white",

                  fontSize:
                    "1.8rem",
                }}
              />

            </IconButton>

          )}

        </div>

      </div>

    </Box>
  );
};

export default Navbar;