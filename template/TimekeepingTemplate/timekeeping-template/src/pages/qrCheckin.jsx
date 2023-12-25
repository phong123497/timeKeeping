import { useEffect } from "react";
import { useState } from "react";
import moment from "moment";
import Box from "@mui/material/Box";
import Group from "../assets/group.svg";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useHeaderContext } from "../hooks/useHeaderContext";
import Qrcode from "./dashboard/Qrcode";
import { useDispatch, useSelector } from "react-redux";
import { QrCodeAction } from "../redux/actionCreators/qrCodeAction";
import { GetWeekDays } from "../components/Weekdays";
function CheckinQr() {
  const dispatch = useDispatch();
  const getData = useSelector((state) => state.qrcode.data);
  const getError = useSelector((state) => state.qrcode.error);
  const [data, setData]= useState([])
  const matches = useMediaQuery("(max-width:391px)");
  const { isHeaderVisible, setIsHeaderVisible } = useHeaderContext();
  const [decodedResults, setDecodedResults] = useState(null);
  const [isCheckinSuccess, setIsCheckinSuccess] = useState(false);
  const [isCheckinError, setIsCheckinError] = useState(false);
  const [currentTime, setCurrentTime] = useState(new Date());
  const date = new Date();
  const today = date.getDay();
  const month = date.getMonth() + 1;
  const year = date.getFullYear();
  const onNewScanResult = (decodedText) => {
    setDecodedResults(decodedText);
  };
  
  useEffect(() => {
    if (decodedResults) {
      dispatch(QrCodeAction(decodedResults));
    }
  }, [decodedResults]);
  useEffect(() => {
    const customSetTimeOut = (func) => {
      setTimeout(() => {
        func();
      }, 3000);
    };
    if (getError == 400 || getError == 500) {
      setIsCheckinError(true);
      customSetTimeOut(() => setIsCheckinError(false));
    }
    if (getData.length > 1) {
      setIsCheckinSuccess(true);
      customSetTimeOut(() => setIsCheckinSuccess(false));
    }
  }, [getError, getData]);
  useEffect(() => {
    setIsHeaderVisible(false);
    if (getData.length > 1) {
      setData(getData);  
    }
    const interval = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);
    return () => {
      clearInterval(interval);
    };
  }, [getData]);

  const ResultData = ({ listUser }) => {
    return (
      <>
        {getData.length ? (
          <table style={{ width: 600 }}>
            <tbody>
              {listUser.map((user, i) => {
                return (
                  <tr key={i} style={{ fontSize: "14px", fontWeight: "400" }}>
                    <td style={{ width: 300 }}>{user.name}</td>
                    <td style={{ width: 300, textAlign: "right" }}>
                      {user && user.updatedDt
                        ? moment(user.updatedDt).format("HH:mm:ss")
                        : ""}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        ) : (
          <p>Không có dữ liệu !!!</p>
        )}
      </>
    );
  };

  return (
    <Box
      sx={{
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
        alignItems: "center"
      }}
    >
      <Box>
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            width: "100%"
          }}
        >
          <img src={Group} />
          <Box
            sx={{
              color: "#E74C3C",
              fontSize: "20px",
              fontWeight: "700",
              fontFamily: "Be Vietnam Pro",
              lineHeight: "20px",
              fontStyle: "italic"
            }}
          >
            <Box>Rikkei</Box>
            <Box>Fukuoka</Box>
          </Box>
        </Box>
      </Box>
      <Box>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            gap: "2rem",
            padding: "2rem 0"
          }}
        >
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              gap: "2rem"
            }}
          >
            <Box
              sx={{
                padding: "0 20px",
                justifyContent: "center",
                alignItems: "center",
                color: "#303E65",
                fontSize: matches ? "0.7rem" : "1rem",
                fontWeight: 400
              }}
            >
              <p>
                {GetWeekDays(today)}, ngày {today} tháng {month} năm {year} -- giờ
                hiện tại :{currentTime.toLocaleTimeString()}
              </p>
            </Box>
            <Box sx={{ widht: "18rem" }}>
              <Box id="reader">
                <Qrcode
                  fps={10}
                  qrbox={300}
                  disableFlip={false}
                  qrCodeSuccessCallback={onNewScanResult}
                />
              </Box>
            </Box>
          </Box>
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              padding: "2rem",
              color: "#303E65",
              fontSize: matches ? "0.7rem" : "1rem",
              fontWeight: 700,
              flexDirection: "column"
            }}
          >
            {isCheckinSuccess ? (
              <h2 style={{ display: "block", color: "#1cbc10" }}>
                check in thành công
              </h2>
            ) : (
              <h2></h2>
            )}
            {isCheckinError ? (
              <h2 style={{ display: "block", color: "red" }}>check in lỗi</h2>
            ) : (
              <h2></h2>
            )}
            <h3 style={{ fontSize: "16px", fontWeight: "700" }}>
              ĐƯA MÃ QR TRƯỚC CAMERA ĐỂ CHẤM CÔNG
            </h3>
          </Box>
        </Box>
      </Box>
      <Box>
        <Box
          sx={{
            color: "#303E65",
            fontSize: matches ? "0.7rem" : "1rem",
            fontWeight: 700
          }}
        >
          <p
            style={{
              textAlign: "center",
              fontWeight: 700,
              marginBottom: "16px"
            }}
          >
            Lịch sử chấm công hôm nay:
          </p>
        </Box>
        <Box>
          <Box>
            <Box>
              <ResultData listUser={getData} />
            </Box>
          </Box>
        </Box>
      </Box>
    </Box>
  );
}

export default CheckinQr;
