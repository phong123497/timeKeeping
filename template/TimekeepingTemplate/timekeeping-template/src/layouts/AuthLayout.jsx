
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import Group from "../assets/group.svg";
import Login from "../assets/login.svg";
import useMediaQuery from "@mui/material/useMediaQuery";

const AuthLayout = ({ children }) => {
  const matches = useMediaQuery("(max-width:1100px)");
  const maxWidth391 = useMediaQuery("(max-width:391px)");
  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container>
        <Grid
          item
          xs={12}
          sm={6}
          sx={{ display: matches ? "none" : "block" }}
        >
          <Box>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                alignItems: "center",
                width: "50vw",
                height: "100vh",
                flexShrink: 0,
                backgroundImage:
                  "linear-gradient(181deg, rgba(231, 76, 60, 0.69) 0.53%, #E74646 99.57%)",
                borderBottomRightRadius: "80px",

              }}
            >
              <Box></Box>
              <Box width="480px" height="392px">
                <Box>
                  <img src={Login} alt="login"
                    style={{ width: "400px", height: "280px", marginBottom: "32px" }}
                  />
                </Box>
                <Typography
                  sx={{
                    color: "#FFF3E2",
                    fontWeight: "700",
                    width: "100%",
                    fontSize: "32px",
                    lineHeight: "normal"
                  }}
                >
                  Giải pháp quản lý chấm công
                  miễn phí & tốt nhất hiện nay.
                </Typography>
              </Box>
              <Box>
                <Typography
                  sx={{
                    color: "#FFF3E2",
                    fontWeight: "400",
                    fontSize: "13px",
                    lineHeight: "32px",
                    marginBottom: "16px",
                  }}
                >
                  Một sản phẩm của <strong>Rikkei Fukuoka</strong>
                </Typography>
              </Box>
            </Box>
          </Box>
        </Grid>
        <Grid item xs={12} sm={matches ? 12 : 6}>
          <Box
            sx={{
              height: "100vh",
              background: "#fff",
              border: "1px solid #BDC3C7",
              borderLeft: "none",
              display: "flex",
              alignItems: "center",
              justifyContent: matches ? "space-between" : "center",
              flexDirection: "column",
              padding: "32px 0",
            }}
          >
            <Box sx={{ display: matches ? "block" : "none" }}>
              <Box sx={{ display: "flex", alignItems: "center", gap: "5px" }}>
                <img src={Group} />
                <Box
                  sx={{
                    color: "#E74C3C",
                    fontSize: "20px",
                    fontWeight: "700",
                    fontFamily: "Be Vietnam Pro",
                    lineHeight: "20px",
                    fontStyle: "italic",
                  }}
                >
                  <Box>Rikkei</Box>
                  <Box>Fukuoka</Box>
                </Box>
              </Box>
            </Box>
            {children}
            <Box sx={{ display: matches ? "block" : "none" }}>
              <Box
                sx={{
                  color: "#303E65",
                  fontSize:maxWidth391? "10px" :"14px" ,
                  fontWeight: "400",
                  fontFamily: "Be Vietnam Pro",
                  lineHeight: "20px",
                  fontStyle: "normal",
                  textAlign: "center",
                }}
              >
                Một sản phẩm của
              </Box>
              <Box
                sx={{
                  color: "#303E65",
                  fontSize:maxWidth391? "10px" :"14px" ,
                  fontWeight: "700",
                  fontFamily: "Be Vietnam Pro",
                  lineHeight: "20px",
                  fontStyle: "normal",
                  textAlign: "center",
                }}
              >
                Rikkei Fukuoka
              </Box>
            </Box>
          </Box>
        </Grid>
      </Grid>
    </Box>
  )
};

export default AuthLayout;
