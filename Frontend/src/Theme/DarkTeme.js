import { createTheme } from "@mui/material/styles";

export const darkTheme = createTheme({
  palette: {
    mode: "dark",

    primary: {
      main: "#e91e63",
    },

    secondary: {
      main: "#5A20CB",
    },

    background: {
      default: "#0D0D0D",
      paper: "#0D0D0D",
    },

    text: {
      primary: "#ffffff",
      secondary: "#aaaaaa",
    },
  },
});
