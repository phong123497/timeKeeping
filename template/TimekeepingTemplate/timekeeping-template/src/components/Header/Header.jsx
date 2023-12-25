import { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import logo from "../../assets/logo.svg";
import account from "../../assets/account.svg";
import { Link } from "react-router-dom";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { useHeaderContext } from "../../hooks/useHeaderContext";

function Header() {
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const { isHeaderVisible } = useHeaderContext();

  useEffect(() => {
  }, [isHeaderVisible]);
   if (!isHeaderVisible) return;

  return (
    <Box
      sx={{
        width: "100%",
        height: "60px",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        borderBottom: "1px solid #F0F0F0",
        position: "sticky",
      }}
    >
      <Box display="flex" alignItems="center" justifyContent="center">
        <Box marginRight="48px">
          <img src={logo} alt="rikkeiLogo" />
        </Box>

        <Box display="flex" alignItems="center" justifyContent="center">
          <Box className="item-heading">
            <Link to="/" className="link-heading">
              <h2 className="title-heading">Tổng quan</h2>
            </Link>
          </Box>

          <Box className="item-heading">
            <Link to="/personnel" className="link-heading">
              <h2 className="title-heading">Nhân sự</h2>
            </Link>
          </Box>

          <Box className="item-heading">
            <Link to="/statistical" className="link-heading">
              <h2 className="title-heading">Thống kê</h2>
            </Link>
          </Box>
          <Box className="item-heading">
            <Link to="/calendar" className="link-heading">
              <h2 className="title-heading">Lịch Làm</h2>
            </Link>
          </Box>
          <Box className="item-heading">
            <Link to="/checkinqr" className="link-heading">
              <h2 className="title-heading">QRCode Checkin</h2>
            </Link>
          </Box>

          <Box />
        </Box>
      </Box>
      {/* icon admin */}
      <Box>
        <Button
          id="basic-button"
          aria-controls={open ? "basic-menu" : undefined}
          aria-haspopup="true"
          aria-expanded={open ? "true" : undefined}
          onClick={handleClick}
        >
          <Box display="flex" alignItems="center">
            <img src={account} alt="" />
            <Box
              className="title-heading"
              style={{ fontWeight: "500", paddingLeft: "4px" }}
            >
              admin
            </Box>
          </Box>
        </Button>
        <Menu
          id="basic-menu"
          anchorEl={anchorEl}
          open={open}
          onClose={handleClose}
          MenuListProps={{
            "aria-labelledby": "basic-button",
          }}
        >
          <MenuItem onClick={handleClose}>admin@rikkeisoft.com</MenuItem>
          <MenuItem onClick={handleClose}>Cập nhập thông tin</MenuItem>
          <MenuItem onClick={handleClose}>
            <Link to={"/login"}>Đăng xuất</Link>
          </MenuItem>
        </Menu>
      </Box>
    </Box>
  );
}
export default Header;
