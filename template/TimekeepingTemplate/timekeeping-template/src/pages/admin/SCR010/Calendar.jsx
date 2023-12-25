
import Box from "@mui/material/Box";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import Vector from "../../../assets/vector.svg";

function Calendar() {
  return (
    <Box
      sx={{
        width: "100%",
        display: "flex",
        alignItems: "center",
        flexDirection: "column",
      }}
    >
      <Box
        className="wapper"
        sx={{
          height: "84px",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
        }}
      >
        <Box>
          <h1
            className="title-heading"
            style={{ fontSize: "32px", paddingLeft: "0" }}
          >
            Lịch Làm Việc
          </h1>
        </Box>
      </Box>
      <Box className="wapper" sx={{ display: "flex" }}>
        <Box sx={{ width: "50%" }}>
          <FullCalendar initialView="dayGridMonth" plugins={[dayGridPlugin]} />
        </Box>
        <Box
          sx={{
            width: "50%",
            display: "flex",
            flexDirection: "column",
            gap: "16px",
            padding: "16px",
          }}
        >
          <Box>sự kiện trong tháng 10:</Box>
          <Box
            sx={{
              display: "flex",
              width: "100%",
              height: "50px",
              padding: "8px",
              justifyContent: "space-between",
              alignItems: "center",
              borderRadius: "4px",
              background: "#303E65",
            }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                gap: "4px",
                color: "#FFF",
              }}
            >
              <Box
                sx={{
                  fontSize: "13px",
                  fontWeight: 700,
                }}
              >
                (Ngày Nghỉ) Nghỉ lể Ngày của Biến
              </Box>
              <Box
                sx={{
                  fontSize: "13px",
                  fontWeight: 400,
                }}
              >
                10/10/2023
              </Box>
            </Box>
            <Box>
              <img src={Vector} alt="" />
            </Box>
          </Box>
          <Box
            sx={{
              display: "flex",
              width: "100%",
              height: "50px",
              padding: "8px",
              justifyContent: "space-between",
              alignItems: "center",
              borderRadius: "4px",
              background: "#303E65",
            }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                gap: "4px",
                color: "#FFF",
              }}
            >
              <Box
                sx={{
                  fontSize: "13px",
                  fontWeight: 700,
                }}
              >
                (Ngày Nghỉ) Nghỉ lể Ngày của Biến
              </Box>
              <Box
                sx={{
                  fontSize: "13px",
                  fontWeight: 400,
                }}
              >
                10/10/2023
              </Box>
            </Box>
            <Box>
              <img src={Vector} alt="" />
            </Box>
          </Box>
          <Box
            sx={{
              display: "flex",
              width: "100%",
              height: "50px",
              padding: "8px",
              justifyContent: "space-between",
              alignItems: "center",
              borderRadius: "4px",
              background: "#303E65",
            }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                gap: "4px",
                color: "#FFF",
              }}
            >
              <Box
                sx={{
                  fontSize: "13px",
                  fontWeight: 700,
                }}
              >
                (Ngày Nghỉ) Nghỉ lể Ngày của Biến
              </Box>
              <Box
                sx={{
                  fontSize: "13px",
                  fontWeight: 400,
                }}
              >
                10/10/2023
              </Box>
            </Box>
            <Box>
              <img
                src={Vector}
                alt=""
              />
            </Box>
          </Box>
        </Box>
      </Box>
    </Box>
  );
}

export default Calendar;
