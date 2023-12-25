import Box from "@mui/material/Box";
import CustomBox from "../../../components/CustomBox";
import { Link } from "react-router-dom/dist";
import CommonModal from "../../../components/CustomModal";
import { useState, useEffect, useMemo } from "react";
import MenuItem from "@mui/material/MenuItem";
import Tooltip from "@mui/material/Tooltip";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import { useDispatch, useSelector } from "react-redux";
import { DataGrid } from "@mui/x-data-grid";
import { EmployeesAction,UpdateEmployee } from "../../../redux/actionCreators/employeesAction";
import kosin from "../../../assets/kosin.svg";
import arrowLeft from "../../../assets/arrow-left.svg";
import arrowRight from "../../../assets/arrow-right.svg";
import { GetWeekDays, GetWeekDaysShort } from "../../../static/js/Weekdays";

function Overview() {
  const [isModalOpenOnTime, setIsModalOpenOnTime] = useState(false);
  const [isModalOpenLate, setIsModalOpenLate] = useState(false);
  const [isModalOpenNoAttend, setIsModalOpenNoAttend] = useState(false);
  const [isModalOpenEdit, setIsModalOpenEdit] = useState(false);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [currentTime, setCurrentTime] = useState(new Date());
  const [borderIndex, setBorderIndex] = useState(null);
  const [referByDate, setReferByDate] = useState("");
  const [selectedDate, setSelectedDate] = useState(getFormattedDate());
  const [requestDay, setRequestDay] = useState("");
  const [employeeEdit, setEmployeeEdit]= useState(
    {
      employeesId:"",
      startTime:"",
      endTime:"",
      status:"",
      note:"",
      date:""
    }
  )
  const dispatch = useDispatch();
  //get data from redux
  const getListEmployee = useSelector((state) => state.employees.listEmployees);
  const getOntime = useSelector((state) => state.employees.onTime);
  const getLate = useSelector((state) => state.employees.late);
  const getNotAttend = useSelector((state) => state.employees.noAttend);
  const employeeStatusEdit =[
    {
      value:1,
      label:"Đúng giờ"
    },
    {
      value :2,
      label:"Đi muộn"
    },
    {
      value :3,
      label:"Nghỉ làm"
    }
  ]
  const initEmployeesColumn = [
    {
      field: "rowNo",
      headerName: "STT",
      width: 40,
      align: "center",
      headerAlign: "left"
    },
    {
      field: "name",
      headerName: "Họ và tên",
      align: "center",
      headerAlign: "center",
      flex: 4,
      renderCell:(params)=>{
        const statusValue = params.row.status;
        const nameEmployee= params.value;
        return (
          <span style={{ color: statusValue !== 'Bình thường' ?  '#E74C3C' : '#303E65'  }}>
            {nameEmployee}
          </span>
        );
      }
    },
    {
      field: "employeeId",
      headerName: "Mã nhân viên",
      align: "center",
      headerAlign: "center",
      flex: 2
    },
    {
      field: "startTime",
      headerName: "Giờ vào làm",
      align: "center",
      headerAlign: "center",
      flex: 3
    },
    {
      field: "endTime",
      headerName: "Giờ tan làm",
      align: "center",
      headerAlign: "center",
      flex: 2
    },
    {
      field: "sumTime",
      headerName: "Thực làm",
      align: "center",
      headerAlign: "center",
      flex: 2
    },
    {
      field: "status",
      headerName: "Trạng thái",
      align: "center",
      headerAlign: "center",
      renderCell: (params) => {
        const statusValue = params.value;
        switch (statusValue) {
          case "Bình thường":
            return <span style={{ color: "#6BCB77", fontWeight:"700" }}>{statusValue}</span>;
          case "Đi muộn":
            return <span style={{ color: "#FFD93D", fontWeight:"700" }}>{statusValue}</span>;
          case "Nghỉ phép":
            return <span style={{ color: "#BB9981", fontWeight:"700" }}>{statusValue}</span>;
          case "Lỗi":
            return <span style={{ color: "#E74C3C", fontWeight:"700" }}>{statusValue}</span>;
          default :
          return <span style={{ color: "#303E65", fontWeight:"700" }}>{statusValue}</span>;
        }
      },
      flex: 3
    },
    {
      field: "note",
      headerName: "Ghi chú",
      align: "center",
      headerAlign: "center",
      flex: 3
    },
    {
      field: "action",
      headerName: "Thao tác",
      align: "center",
      headerAlign: "center",
      flex: 2,
      renderCell: (params) => (
        <Box
          sx={{
            "& .MuiTooltip-tooltip": {
              background: "#fff !important",
              boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.5)"
            }
          }}
        >
          <Tooltip
            arrow
            placement="left"
            PopperProps={{
              disablePortal: true
            }}
            onClose={closeModalEditEmployee}
            open={
              params.row.employeeId !== null
                ? params.row.employeeId === selectedRowId
                : false
            }
            disableFocusListener
            disableHoverListener
            disableTouchListener
            title={
              <Box
                sx={{
                  background: "#fff",
                  width: "200px",
                  height: "260px",
                  p: "8px"
                }}
              >
                <Box sx={{ color: "black", mb: "10px" }}>
                  <Box>
                    <Box>{params.row.name} - {params.row.employeeId}</Box>
                    <Box
                      sx={{ m: "10px 0" }}
                      component="form"
                      noValidate
                      autoComplete="off"
                      display={"flex"}
                    >
                      <TextField
                        id="start-time"
                        placeholder="08:00"
                        type=""
                        size="small"
                        onChange={(e)=>{
                          setEmployeeEdit({
                            ...employeeEdit,
                            date:referByDate!="" ? selectedDate : referByDate ,
                            employeeId:params.row.employeeId,
                            startTime:e.target.value
                          })
                        }}
                      />
                      <Box
                        sx={{
                          m: "0 10px",
                          alignItems: "center",
                          display: "flex"
                        }}
                      >
                        -
                      </Box>
                      <TextField
                        id="end-time"
                        placeholder="18:00"
                        size="small"
                        onChange={(e)=>{
                          setEmployeeEdit({
                            ...employeeEdit,
                            endTime:e.target.value
                          })
                        }}
                      />
                    </Box>
                    <Box sx={{ minWidth: 120 }}>
                    <TextField
                      id="employee-update"
                      select
                      defaultValue='Đúng giờ' 
                      size="small"
                      sx={{ width: "184px" }}
                      onChange={(e)=>{
                        setEmployeeEdit({
                          ...employeeEdit,
                          status:e.target.value
                        })
                      }}
                    
                    >
                      {employeeStatusEdit.map((option) => (
                        <MenuItem key={option.value} value={option.label}>
                          {option.label}
                        </MenuItem>
                      ))}
                    </TextField>
                    </Box>
                    <Box sx={{ m: "15px 0" }}>
                      <TextField
                        multiline
                        rows={1.5}
                        defaultValue=""
                        onChange={(e)=>{
                         setEmployeeEdit({
                          ...employeeEdit,
                          note:e.target.value
                         })
                        }}
                      />
                    </Box>
                    <Box>
                      <Button
                        sx={{ width: "100%" }}
                        size="small"
                        variant="contained"
                        onClick={handleSaveModalEditEmployee}
                      >
                        Lưu
                      </Button>
                    </Box>
                  </Box>
                </Box>
              </Box>
            }
          >
            <Link
              onClick={() =>
                handleToggelEditModalEmployee(params.row.employeeId)
              }
              className="edit-button"
            >
              {getListEmployee !== null && getListEmployee.length >= 1
                ? "chỉnh sửa"
                : ""}
            </Link>
          </Tooltip>
        </Box>
      )
    }
  ];
  const initColumnsModalDetailStatus = [
    { field: "rowNo", headerName: "STT", width: 50 },
    {
      field: "name",
      headerName: "Họ và tên",
      align: "center",
      headerAlign: "center",
      width: 220
    },
    {
      field: "employeeId",
      headerName: "Mã nhân viên",
      align: "center",
      headerAlign: "center",
      type: "email",
      width: 150
    },
    {
      field: "startTime",
      headerName: "Giờ chấm công",
      align: "center",
      headerAlign: "center",
      type: "number",
      width: 120
    },
    {
      field: "note",
      headerName: "Ghi chú",
      align: "center",
      headerAlign: "center",
      width: 150
    }
  ];

  const rowsModalDetailOntime = [];
  const rowsModalDetailLate = [];
  const rowsModalDetailNotAttend = [];
  const getrowsModalDetailStatus = () => {
    if (getListEmployee !== null && getListEmployee.length!=0) {
      for (let i = 0; i < getListEmployee.length; i++) {
        const EmployeesByStatus = getListEmployee[i];
        if (EmployeesByStatus.status == "Bình thường") {
          rowsModalDetailOntime.push({
            name: EmployeesByStatus.name,
            employeeId: EmployeesByStatus.employeeId,
            startTime: EmployeesByStatus.startTime,
            note: EmployeesByStatus.note
          });
        } else if (EmployeesByStatus.status == "Đi muộn") {
          rowsModalDetailLate.push({
            name: EmployeesByStatus.name,
            employeeId: EmployeesByStatus.employeeId,
            startTime: EmployeesByStatus.startTime,
            note: EmployeesByStatus.note
          });
        } else {
          rowsModalDetailNotAttend.push({
            name: EmployeesByStatus.name,
            employeeId: EmployeesByStatus.employeeId,
            startTime: EmployeesByStatus.startTime,
            note: EmployeesByStatus.note
          });
        }
      }
    }
  };
  getrowsModalDetailStatus();
  // page refer 
  const handleReferPage=()=>{
    handleShowDetailDay(referByDate)
  }
  //set format Date
  function getFormattedDate() {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, "0");
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const year = today.getFullYear();
    return `${year}-${month}-${day}`;
  }
  // set date change when day select
  const handleDateChange = (event) => {
    const newDate = event.target.value;
    setSelectedDate(newDate);
    handleShowDetailDay(newDate);
  };
  // next Week
  const handleNextWeek = () => {
    const currentDate = new Date(selectedDate);
    currentDate.setDate(currentDate.getDate() + 7);
    updateSelectedDate(currentDate);
    // setBorderIndex("1px solid #303E65")
  };
  // back week
  const handleBackWeek = () => {
    const currentDate = new Date(selectedDate);
    currentDate.setDate(currentDate.getDate() - 7);
    updateSelectedDate(currentDate);
  };
  // update date and get data from server
  const updateSelectedDate = (date) => {
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    setSelectedDate(`${year}-${month}-${day}`);
    setReferByDate(`${year}-${month}-${day}`)
    handleShowDetailDay(`${year}-${month}-${day}`);
  };
  // show day in week function
  const renderDayInWeek = () => {
    // console.log("abc");
    const days = [];
    const today = new Date();
    for (let i = 0; i < 7; i++) {
      const nextDate = new Date(selectedDate);
      nextDate.setDate(nextDate.getDate() + i);
      const year = nextDate.getFullYear();
      const month = String(nextDate.getMonth() + 1).padStart(2, "0");
      const day = String(nextDate.getDate()).padStart(2, "0");
      const weekday = nextDate.getDay();
      const formattedDate = `${GetWeekDaysShort(weekday)} ${day} Th.${month}`;
      const formatDate = `${year}-${month}-${day}`;

      const isFutureDate = today < nextDate;
      days.push({
        format: formatDate,
        date: formattedDate,
        isFutureDate: isFutureDate
      });
    }
    return days.map((day, index) => (
      <CustomBox
        key={index}
        title={day.date}
        detail={day.isFutureDate ? "Không có dữ liệu" : "Chi tiết"}
        border={
          (index === borderIndex ? "1px solid #303E65" : "1px solid #F0F0F0") 

        }
        width="13.7%"
        fontTitle="12px"
        fontDetail="12px"
        colorTitle={day.isFutureDate ? "#F0F0F0" : "#303E65"}
        onClick={() => {
          setReferByDate(day.format)
          setBorderIndex(index);
          handleShowDetailDay(day.format);
        }}
      />
    ));
  };

  const handleShowDetailDay = (date) => {
    setRequestDay(date);
    dispatch(EmployeesAction(date));
  };
  useEffect(() => {
    renderDayInWeek();
    
  }, [requestDay]);
  // set real time
  // useEffect(() => {
  //   const interval = setInterval(() => {
  //     setCurrentTime(new Date());
  //   }, 1000);

  //   return () => {
  //     clearInterval(interval);
  //   };
  // }, []);

  const handleToggelEditModalEmployee = (rowId) => {
    setSelectedRowId(rowId);
    if (isModalOpenEdit && selectedRowId === rowId) {
      closeModalEditEmployee(rowId);
    } else {
      setIsModalOpenEdit(true);
    }
  };

  const closeModalEditEmployee = (rowId) => {
    setIsModalOpenEdit(false);
    setSelectedRowId(rowId);
  };
  const handleSaveModalEditEmployee = () => {
    closeModalEditEmployee();
    dispatch(UpdateEmployee(employeeEdit))
  };

  const EmployeeByStatus = {
    ONTIME: "ONTIME",
    LATE: "LATE",
    NOATTEND: "NOATTEND"
  };
  const handleOpenModal = (status) => {
    switch (status) {
      case EmployeeByStatus.ONTIME:
        setIsModalOpenOnTime(true);
        break;
      case EmployeeByStatus.LATE:
        setIsModalOpenLate(true);
        break;

      case EmployeeByStatus.NOATTEND:
        setIsModalOpenNoAttend(true);
        break;
      default:
        break;
    }
  };

  const handleCloseModal = () => {
    setIsModalOpenOnTime(false);
    setIsModalOpenLate(false);
    setIsModalOpenNoAttend(false);
  };

  const [age, setAge] = useState("");

  const handleChange = (event) => {
    setAge(event.target.value);
  };

  // const renderByChangDate = useMemo(()=>{
  //   renderDayInWeek()
  // },[referByDate,selectedDate]);
  // funtion show modal list employee detail
  const modalDetailEmployeesStatus = (status, rowModalByStatus) => {
    let openModal;
    let titleStatus;
    const date = new Date(requestDay);
    const today = date.getDay();

    let month = requestDay.split("-")[1] ? requestDay.split("-")[1] : "";
    let day = requestDay.split("-")[2] ? requestDay.split("-")[2] : "";
    switch (status) {
      case EmployeeByStatus.ONTIME:
        openModal = isModalOpenOnTime;
        titleStatus = "Vào làm";
        break;
      case EmployeeByStatus.LATE:
        openModal = isModalOpenLate;
        titleStatus = "Đi Muộn";
        break;

      case EmployeeByStatus.NOATTEND:
        openModal = isModalOpenNoAttend;
        titleStatus = "Nghỉ phép";
        break;
      default:
        break;
    }
    let title = "Danh sách nhân viên ".concat(titleStatus," thứ ",GetWeekDays(today)," ngày ",day," tháng ", month);
    return (
      <CommonModal
        stylebuttons={{
          width: "679px",
          display: "flex",
          justifyContent: "flex-end"
        }}
        width="712"
        title={title}
        isOpen={openModal}
        handleClose={handleCloseModal}
        content={
          <Box sx={{ height: 322, width: "100%" }}>
            <DataGrid
              sx={{
                "& .MuiDataGrid-cell": {
                  border: "1px solid #F0F0F0",
                  borderRadius: "2px",
                  margin: "0 8px",
                  maxHeight: "28px !important",
                  minHeight: "28px !important"
                },

                "& .MuiDataGrid-columnHeader": {
                  background: " #F0F0F0",
                  margin: "6px 8px"
                },
                "& .MuiDataGrid-columnHeaders": {
                  background: " #F0F0F0"
                },
                "& .MuiDataGrid-row": {
                  margin: "6px",
                  maxHeight: "28px !important",
                  minHeight: "28px !important"
                },
                "& .MuiDataGrid-row:hover": {
                  background: "rgba(0, 0, 0, 0.0) !important"
                },
                "& .MuiDataGrid-row.Mui-selected": {
                  background: "rgba(0, 0, 0, 0.0) !important"
                }
              }}
              getRowId={(row) => row.rowNo}
              rows={
                rowModalByStatus
                  ? rowModalByStatus.map((row) => ({
                      ...row,
                      rowNo: rowModalByStatus.indexOf(row) + 1
                    }))
                  : []
              }
              columns={initColumnsModalDetailStatus}
              initialState={{
                pagination: {
                  paginationModel: {
                    pageSize: 5
                  }
                }
              }}
              pageSizeOptions={[5]}
            />
          </Box>
        }
      />
    );
  };
 
  return (
    <Box
      sx={{
        height: "100vh",
        display: "flex",
        alignItems: "center",
        flexDirection: "column"
      }}
    >
      {/* titile */}
      <Box
        sx={{
          width: "100%",
          minHeight: "4.5rem",
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between"
        }}
      >
        <Box>
          <h1
            className="title-heading"
            style={{ fontSize: "32px", paddingLeft: "0" }}
          >
            Tổng quan
          </h1>
        </Box>

        <Box onClick={()=>handleReferPage()}>
          <img src={kosin} alt="" />
        </Box>
      </Box>
      {/* status */}
      <Box className="display-flexbody">
        <Box display="flex" justifyContent="space-between" marginBottom="16px">
          <h4
            className="title-text"
            style={{ fontSize: "14px", fontWeight: "400" }}
          >
            Chấm công hôm nay
          </h4>
          <h4 className="title-text" style={{ fontSize: "14px" }}>
            <span>{currentTime.toLocaleTimeString()}</span>
            <span> </span>
            <span>{currentTime.toLocaleDateString()}</span>
          </h4>
        </Box>

        <Box display="flex" justifyContent="space-between">
          <Box sx={{ width: "24%" }}>
            <CustomBox
              onClick={() => {
                handleOpenModal(EmployeeByStatus.ONTIME);
              }}
              background="rgba(107, 203, 119, 0.20)"
              colorTitle="#6BCB77"
              title={getOntime ? getOntime : ""}
              detail="Vào làm"
              height="70px"
              width="100%"
              borderRadius="10px"
              marginBottom="8px"
            />
            {modalDetailEmployeesStatus(
              EmployeeByStatus.ONTIME,
              rowsModalDetailOntime
            )}
          </Box>
          <Box sx={{ width: "24%" }}>
            <CustomBox
              onClick={() => {
                handleOpenModal(EmployeeByStatus.LATE);
              }}
              background="rgba(255, 217, 61, 0.20)"
              title={getLate ? getLate : ""}
              detail="Đi muộn"
              height="70px"
              width="100%"
              borderRadius="10px"
              marginBottom="8px"
              colorTitle="#FFD93D"
            />
            {modalDetailEmployeesStatus(
              EmployeeByStatus.LATE,
              rowsModalDetailLate
            )}
          </Box>

          <Box sx={{ width: "24%" }}>
            <CustomBox
              // onClick={handleOpenModal}
              background="rgba(231, 76, 60, 0.20)"
              colorTitle="#E74C3C"
              title=""
              detail="Chưa vào"
              height="70px"
              width="100%"
              borderRadius="10px"
              marginBottom="8px"
            />
          </Box>

          <Box sx={{ width: "24%" }}>
            <CustomBox
              onClick={() => handleOpenModal(EmployeeByStatus.NOATTEND)}
              background="rgba(187, 153, 129, 0.20)"
              colorTitle="#BB9981"
              title={getNotAttend ? getNotAttend : ""}
              detail="Nghỉ phép"
              height="70px"
              width="100%"
              borderRadius="10px"
              marginBottom="8px"
            />
            {modalDetailEmployeesStatus(
              EmployeeByStatus.NOATTEND,
              rowsModalDetailNotAttend
            )}
          </Box>
        </Box>
      </Box>

      {/* detail */}
      <Box className="display-flexbody" marginTop="16px">
        <Box
          display="flex"
          justifyContent="space-between"
          alignItems="center"
          marginBottom="16px"
        >
          <h4
            className="title-text"
            style={{ fontSize: "17px", fontWeight: "500" }}
          >
            Lịch sử chấm công
          </h4>
          <Box sx={{ display: "flex", justifyItems: "center" }}>
            <Box onClick={handleBackWeek}>
              <img src={arrowLeft} alt="" />
            </Box>
            <Box sx={{ height: "24px", margin: "0 8px", padding: "0 8px" }}>
              <TextField
                type="date"
                value={selectedDate}
                onChange={handleDateChange}
                sx={{
                  "& .MuiInputBase-input": {
                    padding: 0
                  },
                  "& .MuiOutlinedInput-root": {
                    fontSize: "13px",
                    height: "24px",
                    border: " 1px solid #303E65",
                    "& > fieldset": {
                      top: "0px",
                      padding: "0",
                      border: "none"
                    }
                  }
                }}
              />
            </Box>
            <Box onClick={handleNextWeek}>
              <img src={arrowRight} alt="" />
            </Box>
          </Box>
        </Box>

        <Box
          display="flex"
          justifyContent="space-between"
          sx={{ width: "100%" }}
        >
          {renderDayInWeek()}
        </Box>
        <Box
          sx={{
            minHeight: "40vh",
            width: "100%",
            marginTop: "14px"
          }}
        >
          <DataGrid
            sx={{
              "& .MuiDataGrid-virtualScroller css-qvtrhg-MuiDataGrid-virtualScroller":{
                overflowX:"hidden !important"
              },
              "& .MuiDataGrid-columnHeader": {
                color: "#303E65 ",
                fontWeight: 700,
                fontSize: 12,
                background: " #F0F0F0",
                minHeight: "50px !important",
                maxHeight: "50px !important"
              },
              "& .MuiDataGrid-row": {
                color: "#303E65 ",
                fontWeight: 400,
                fontSize: 12,
                minHeight: "40px !important",
                maxHeight: "40px !important"
              },
              "& .MuiDataGrid-row:nth-child(odd)": {
                bgcolor: "rgba(240, 240, 240, 0.30) !important"
              },
              "& .MuiDataGrid-cell": {
                minHeight: "40px !important",
                maxHeight: "40px !important"
              },
              "& .MuiDataGrid-columnHeaders": {
                background: " #F0F0F0"
              },
              "& .MuiDataGrid-row:hover": {
                background: "rgba(0, 0, 0, 0.0) !important"
              },
              "& .MuiDataGrid-row.Mui-selected": {
                background: "rgba(0, 0, 0, 0.0) !important"
              }
            }}
            getRowId={(row) => row.rowNo}
            rows={
              getListEmployee
                ? getListEmployee.map((row) => ({
                    ...row,
                    rowNo: getListEmployee.indexOf(row) + 1
                  }))
                : []
            }
            columns={initEmployeesColumn}
            pageSizeOptions={[5, 10, 15]}
            // initialState={{
            //   pagination: {
            //     paginationModel: {
            //       pageSize: 2,
            //     },
            //   },
            // }
            // GridSlotsComponentsProps={{
            //   pagination: {
            //     labelRowsPerPage: t('key.rowPerPageTranslation')
            //   }
            // }}
          />
        </Box>
      </Box>
    </Box>
  );
}

export default Overview;
