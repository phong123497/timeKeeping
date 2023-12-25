
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import upload from "../../../assets/upload.svg";
import { Link } from "react-router-dom";
import CommonModal from "../../../components/CustomModal";
import TextField from "@mui/material/TextField";
import Tooltip from "@mui/material/Tooltip";
import { useState } from "react";
import Dowloadd from "../../../assets/download.svg";
import { DataGrid } from "@mui/x-data-grid";


const rowss = [
  { id: 1, lastName: null, email: null, stt: 35 },
  { id: 2, lastName: null, email: null, stt: 42 },
  { id: 3, lastName: null, email: null, stt: 45 },
  { id: 4, lastName: null, email: null, stt: 16 },
  { id: 5, lastName: null, email: null, stt: null },
  { id: 6, lastName: null, email: null, stt: 150 },
  { id: 7, lastName: null, email: null, stt: 44 },
  { id: 8, lastName: null, email: null, stt: 36 },
  { id: 9, lastName: null, email: null, stt: 65 },
];



function Personnel() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModalEdit, setIsModalEdit] = useState(false);
  const [isModalDelete, setIsModalDelete] = useState(false);
  const [selectedRowId, setSelectedRowId] = useState(null);

  const handEdit = () => {
    setIsModalEdit(true);
  };
  const heandEditClose = () => {
    setIsModalEdit(false);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleDelete = (rowId) => {
    setIsModalDelete(true);
    setSelectedRowId(rowId);
  };

  const closehandleDelete = (rowId) => {
    setIsModalDelete(false);
    setSelectedRowId(rowId);
  };

  const handleOpenModalTest = () => {
    setIsModalOpen(true);
  };

  const handledelete = () => {
    alert("Đã Xóa");
    // handleTooltipClose(rowId);
  };
  const column = [
    { field: "id", headerName: "STT", width: 40 },
    {
      field: "hvt",
      headerName: "Họ và tên",
      width: 220,
      renderCell: () => <Link to={"/detail"}>Phan Minh Phong</Link>,
    },
    {
      field: "mnv",
      headerName: "Mã nhân viên",
      type: "number",
      width: 150,
    },
    {
      field: "email",
      headerName: "Địa chỉ email",
      width: 250,
    },
    {
      field: "mqr",
      headerName: "Mã QR",
      width: 150,
    },
    {
      field: "gch",
      headerName: "Ghi chú",
      width: 323,
    },
    {
      field: "tht",
      headerName: "Thao tác",
      width: 100,
      renderCell: (params) => (
        <Box sx={{ display: "flex", justifyContent: "space-between", gap: 1 }}>
          <Box>
            <Link style={{ textDecoration: "none" }} onClick={handEdit}>
              Sửa
            </Link>
          </Box>
          <Box
            sx={{
              "& .MuiTooltip-tooltip": {
                background: "#fff !important",
                boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.5)",
              },
            }}
          >
            <Tooltip
              arrow
              placement="left"
              PopperProps={{
                disablePortal: true,
              }}
              onClose={closehandleDelete}
              open={params.row.id === selectedRowId}
              disableFocusListener
              disableHoverListener
              disableTouchListener
              title={
                <Box sx={{ color: "black", padding: "8px" }}>
                  <Box>
                    <Typography
                      sx={{
                        color: "#303E65",
                        fontSize: "12px",
                        fontWeight: "400",
                      }}
                    >
                      Bạn có chắc chắn muốn xóa nhân
                    </Typography>
                    <Typography>:Phan Minh Phong không?</Typography>
                  </Box>
                  <Box
                    sx={{
                      marginTop: "10px",
                      display: "flex",
                      justifyContent: "space-around",
                    }}
                  >
                    <Button
                      size="small"
                      variant="outlined"
                      onClick={closehandleDelete}
                    >
                      Hủy
                    </Button>
                    <Button
                      size="small"
                      color="error"
                      variant="outlined"
                      onClick={handledelete}
                    >
                      Xóa
                    </Button>
                  </Box>
                </Box>
              }
            >
              <Link
                style={{ textDecoration: "none", color: "red" }}
                onClick={() => handleDelete(params.row.id)}
              >
                Xóa
              </Link>
            </Tooltip>
          </Box>
        </Box>
      ),
    },
  ];
  const rows = [
    {
      id: 1,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
    {
      id: 2,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
    {
      id: 3,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
    {
      id: 4,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
    {
      id: 5,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
    {
      id: 6,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
    {
      id: 7,
      mnv: "2344656",
      email: 35,
      mqr: "QTDVB",
      gch: "Đi muộn",
    },
  ];
  const columns = [
    { field: "id", headerName: "STT", width: 40 },
    {
      field: "hvt",
      headerName: "Họ và tên",
      width: 220,
      editable: true,
    },
    {
      field: "email",
      headerName: "Địa chỉ email",
      width: 250,
      editable: true,
    },
    {
      field: "sdt",
      headerName: "Số điện thoại",
      width: 150,
      editable: true,
    },
  ];

  return (
    <Box
      sx={{
        width: "100%",
        height: "100vh",
        display: "flex",
        alignItems: "center",
        // justifyContent: "center",
        flexDirection: "column",
      }}
    >
      <CommonModal
        stylebuttons={{
          display: "flex",
          justifyContent: "flex-end",
        }}
        width="450px"
        title="Phan Minh Phong"
        content={
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-around",
              }}
            >
              <Box>ID/Mã nhân viên</Box>
              <Box>Tên nhân viên*</Box>
              <Box>Email*</Box>
              <Box>QR-Code*</Box>
            </Box>
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                gap: "10px",
              }}
            >
              <Box>
                <TextField
                  id="outlined-size-small"
                  defaultValue="PhongPM"
                  size="small"
                />
              </Box>
              <Box>
                <TextField
                  id="outlined-size-small"
                  defaultValue="Phan Minh Phong"
                  size="small"
                />
              </Box>
              <Box>
                <TextField
                  id="outlined-size-small"
                  defaultValue="PhongThuy@LOve.com"
                  size="small"
                />
              </Box>
              <Box>
                <TextField
                  id="outlined-size-small"
                  defaultValue="zasfffg"
                  size="small"
                />
              </Box>
            </Box>
          </Box>
        }
        isOpen={isModalEdit}
        handleClose={heandEditClose}
      />

      <Box
        sx={{
          width: "100%",
          minHeight: "4.5rem",
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
            Quản Lý Nhân Sự
          </h1>
        </Box>

        <Box>
          <Button
            sx={{
              right: "10px",
              background: "#6BCB77",
              "&:hover": {
                background: "#4CAF50",
              },
            }}
            variant="contained"
          >
            <img src={upload} alt="" />
            Nhập file Excel
          </Button>
          <Button
            onClick={() => handleOpenModalTest()}
            sx={{
              background: "#6BCB77",
              "&:hover": {
                background: "#4CAF50",
              },
            }}
            variant="contained"
            color="success"
          >
            <Box>+</Box>
            Thêm
          </Button>
          <CommonModal
            width={692}
            title="Thêm nhân viên"
            handleClose={handleCloseModal}
            isOpen={isModalOpen}
            content={
              <Box sx={{ height: "55vh", width: "100%" }}>
                <DataGrid
                  sx={{
                    "& .MuiDataGrid-cell": {
                      border: "1px solid #F0F0F0",
                      borderRadius: "2px",
                      margin: "0 8px",
                      maxHeight: "28px !important",
                      minHeight: "28px !important",
                    },

                    "& .MuiDataGrid-columnHeader": {
                      padding: "0 ",

                      background: " #F0F0F0",
                      margin: "6px 8px",
                    },
                    "& .MuiDataGrid-columnHeaders": {
                      background: " #F0F0F0",
                    },
                    "& .MuiDataGrid-row": {
                      margin: "6px",
                      maxHeight: "28px !important",
                      minHeight: "28px !important",
                    },
                    "& .MuiDataGrid-row:hover": {
                      background: "rgba(0, 0, 0, 0.0) !important",
                    },
                    "& .MuiDataGrid-row.Mui-selected": {
                      background: "rgba(0, 0, 0, 0.0) !important",
                    },
                  }}
                  rows={rowss}
                  columns={columns}
                  initialState={{
                    pagination: {
                      paginationModel: {
                        pageSize: 10,
                      },
                    },
                  }}
                />
              </Box>
            }
          />
        </Box>
      </Box>
      <Box className="display-flexbody">
        <Box
          sx={{
            display: "flex",
            justifyContent: "flex-end",
            mb: "15px",
            alignItems: "center",
          }}
        >
          <img src={Dowloadd} alt="" />
          <Link>Xuất File Excel</Link>
        </Box>
        <Box sx={{ height: "100%", width: "100%", marginTop: "14px" }}>
          <DataGrid
            sx={{
              "& .MuiDataGrid-columnHeader": {
                color: "#303E65 ",
                fontWeight: 700,
                fontSize: 12,
                background: " #F0F0F0",
                minHeight: "40px !important",
                maxHeight: "50px !important",
              },
              "& .MuiDataGrid-row": {
                color: "#303E65 ",
                fontWeight: 400,
                fontSize: 12,
                minHeight: "40px !important",
                maxHeight: "40px !important",
              },
              "& .MuiDataGrid-row:nth-child(odd)": {
                bgcolor: "rgba(240, 240, 240, 0.30) !important",
              },
              "& .MuiDataGrid-cell": {
                minHeight: "40px !important",
                maxHeight: "40px !important",
              },
              "& .MuiDataGrid-columnHeaders": {
                background: " #F0F0F0",
              },
              "& .MuiDataGrid-row:hover": {
                background: "rgba(0, 0, 0, 0.0) !important",
              },
              "& .MuiDataGrid-row.Mui-selected": {
                background: "rgba(0, 0, 0, 0.0) !important",
              },
            }}
            rows={rows}
            columns={column}
          />
        </Box>
      </Box>
    </Box>
  );
}

export default Personnel;
