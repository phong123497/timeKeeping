

import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";

export default function CustomModal({ ...props }) {
  const handleCloseModal = () => {
    props.handleClose(true);
  };
  return (
    <Box>
      <Modal
        open={props.isOpen}
        onClose={handleCloseModal}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        sx={{
          border: "none",
        }}
      >
        <Box
          sx={{
            position: "fixed",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            bgcolor: "#fff",
            border: "1px solid #f0f0f0",
            width: props.width || 712,
            boxShadow: 24,
            p: "16px",
            display: "flex",
            flexDirection: " column",
            gap: "16px",
          }}
        >
          <Box sx={{ fontSize: "28px" }}>{props.title}</Box>
          <Box>{props.content}</Box>
          <Box sx={props.stylebuttons}>
            <Button
              sx={props.stylebutton}
              onClick={()=>handleCloseModal()}
              variant="contained"
            >
              Đóng
            </Button>
          </Box>
        </Box>
      </Modal>
    </Box>
  );
}
