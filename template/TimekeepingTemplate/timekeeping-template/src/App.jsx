import './App.css'
import { CssBaseline } from "@mui/material";
import AppRoutes from "./routes/routes";
import React from "react";
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import { useSelector } from "react-redux";

function App() {
  const loading = useSelector((state) => state.circular.loading)
  return (
    <React.Fragment>
      <Backdrop
        sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
      {/* <ThemeProvider theme={theme}> */}
      <CssBaseline />
      <AppRoutes />
      {/* </ThemeProvider> */}
    </React.Fragment>
  )
}

export default App
