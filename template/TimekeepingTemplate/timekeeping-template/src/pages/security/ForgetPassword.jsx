import { useState, useEffect } from "react";
import Box from "@mui/material/Box";
import TextInput from "../../components/TextInput.jsx";
import ButtonInput from "../../components/ButtonInput.jsx";
import { withRouter } from "../../hooks/withRouter.jsx";
import { useMediaQuery } from "@mui/material";
import { ForgetPassAction } from "../../redux/actionCreators/authActions.jsx";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import {TEXTINPUT_TYPE} from "../../static/js/constant.js"
import {validateEmail}  from "../../static/js/common.js"
import _, { flatMap } from 'lodash';

const ForgetPassword = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const maxWidth391 = useMediaQuery("(max-width:391px)");
  const [isMailSuccess,setIsMailSuccess] =useState("");
  const [isMailError,setIsMailError] =useState("");
  const [isClick, setIsClick] =useState(false)
  const [user, setUser] = useState({
    email:""
  });
  const forgetPass = useSelector((state) => state.auth.isSendPass)

  const handleSubmit = async () => {
    
    if(user.email !== "" && !validateEmail(user.email)){
        await dispatch(ForgetPassAction(user.email));
        setIsClick(!isClick)
    }
  };
;

  useEffect(()=>{
    if(forgetPass === true){
      setIsMailSuccess(true);
      customSetTimeOut(() => setIsMailSuccess(false));
    }
    if(forgetPass === false){
      
      setIsMailError(true);
        customSetTimeOut(() => setIsMailError(false));
    }
    
  },[isClick])

  const customSetTimeOut = (func) => {
    setTimeout(func, 3000);
  };
 
  
  const handleChangeEmail = (e) => {
    setUser({
      ...user,
      email: e.target.value
    });
  };
  // useEffect(() => {
  //   if (!_.isEmpty(userlogin)) {
  //     navigate('/', { replace: true });
  //   }
  // }, [userlogin]);

  return (
    <Box
      sx={{
        fontSize: maxWidth391 ? "10px" : "20px",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <Box className="flexCenter">
        <Box >
            {isMailSuccess && (
              <h3 style={{ color: "#1cbc10" }}>Mật khẩu mới đã được gửi về mail</h3>
            )}
        </Box>
        <Box>
          {isMailError && (
              <h3  style={{ color:"red" }}>Địa chỉ email không chính xác!</h3>
            )}
        </Box>

        <Box sx={{maxWidth:"400px"}}>
          <h3 className="input-text"  style={{  
            fontSize:"20px"
          }}>Quên mật khẩu</h3>
          <p className="text-blur">Nhập vào địa chỉ email đã đăng ký, mật khẩu mới sẽ được gửi về!</p>
        </Box>
        <Box marginBottom="16px">
          <h3 className="input-text">Địa chỉ email</h3>
          <TextInput
            type="email"
            placeholder="Nhập địa chỉ email"
            require={true}
            value={user.email}
            checkType={TEXTINPUT_TYPE.EMAIL}
            width={maxWidth391 ? "350px" : "400px"}
            onChange={(e) => {
              handleChangeEmail(e);
            }}
          />
        </Box>
        <ButtonInput width={maxWidth391 ? "350px" : "400px"} title="Lấy lại mật khẩu" textColor="FFF" onClick={handleSubmit} />

        
      </Box>
    </Box>
  );
};
export default withRouter(ForgetPassword);
