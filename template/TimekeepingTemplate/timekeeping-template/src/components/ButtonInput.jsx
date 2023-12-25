import Button from "@mui/material/Button";

function ButtonInput({ ...props }) {
  return (
    <div>
      <Button
        variant="contained"
        onClick={props.onClick}
        sx={{
          color: props.textColor || "#FFF3E2",
          textTransform: "none",
          padding: props.padding || "12px 0px",
          fontSize: "13px",
          display: "flex",
          fontWeight: "700",
          width: props.width || "400px",
          height: "40px",
          background: props.bgColor || "#E74646",
          borderRadius: "10px",
          boxShadow: "none",
          "&:hover": {
            backgroundColor: "#E74646",
          },
        }}
      >
        {props.title}
      </Button>
    </div>
  );
}

export default ButtonInput;
