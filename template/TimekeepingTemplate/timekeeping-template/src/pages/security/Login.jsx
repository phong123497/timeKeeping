import { useState, useEffect } from "react";
import Box from "@mui/material/Box";
import TextInput from "../../components/TextInput";
import ButtonInput from "../../components/ButtonInput";
import { Link } from "react-router-dom";
import { withRouter } from "../../hooks/withRouter.jsx";
import { useMediaQuery } from "@mui/material";
import { AuthStart } from "../../redux/actionCreators/authActions.jsx";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import _ from 'lodash';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const maxWidth391 = useMediaQuery("(max-width:391px)");
  const [user, setUser] = useState({
    userName: "",
    passWord: ""
  });
  const userlogin = useSelector((state) => state.auth.user)

  const handleSubmit = async () => {
    await dispatch(AuthStart(user.userName, user.passWord));
  };
  const handleChangeUserName = (e) => {
    setUser({
      ...user,
      userName: e.target.value
    });
  };
  const handleChangePass = (e) => {
    setUser({
      ...user,
      passWord: e.target.value
    });
  };

  useEffect(() => {
    if (!_.isEmpty(userlogin)) {
      navigate('/', { replace: true });
    }
  }, [userlogin]);

  return (
    <Box
      sx={{
        fontSize: maxWidth391 ? "10px" : "20px",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <Box sx={{ mb: maxWidth391 ? "10px" : "32px" }}>
        <h2 className="input-text" style={{ fontSize: "20px" }}>
          Đăng nhập
        </h2>
      </Box>
      <Box className="flexCenter">
        <Box>
          <h3 className="input-text">Tên tài khoản</h3>
          <TextInput
            type="text"
            placeholder="Nhập tên tài khoản"
            value={user.userName}
            require={true}
            checkType={9}
            width={maxWidth391 ? "350px" : "400px"}
            onChange={(e) => {
              handleChangeUserName(e);
            }}
          />
        </Box>
        <Box marginBottom="16px">
          <h3 className="input-text">Mật khẩu</h3>
          <TextInput
            type="password"
            placeholder="Nhập mật khẩu"
            require={true}
            value={user.passWord}
            checkType={8}
            width={maxWidth391 ? "350px" : "400px"}
            onChange={(e) => {
              handleChangePass(e);
            }}
          />
        </Box>
        <ButtonInput width={maxWidth391 ? "350px" : "400px"} title="Đăng nhập" textColor="FFF" onClick={handleSubmit} />

        <Box sx={{ display: "flex", alignItems: "end" }}>
          <Link
            className="input-text"
            style={{ color: "#E74646", fontWeight: "400" }} S
            to="/forget-pass"
          >
            Quên mật khẩu?
          </Link>
        </Box>
      </Box>
    </Box>
  );
};
export default withRouter(Login);
