import { Box, CssBaseline } from "@mui/material";
import Header from "../components/Header/Header";
import Grid from "@mui/material/Grid";

const Layout = ({ children }) => {
  return (
    <Box>
      <CssBaseline />
      <Grid container>
        <Grid item xs></Grid>
        <Grid item xs={8} sx={{ padding: "0 20px" }}>
          <Header />
          {children}
        </Grid>
        <Grid item xs></Grid>
      </Grid>
    </Box>
  );
};

export default Layout;
