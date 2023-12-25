

import Box from "@mui/material/Box";
import { Link } from "react-router-dom/dist";
import Typography from "@mui/material/Typography";
function CustomBox({ ...props }) {
  return (
    <Box
      onClick={props.onClick}
      sx={{
        width: props.width || "166px",
        height: props.height || "52px",
        background: props.background,
        border: props.border || "none",
        padding: props.padding || "16px",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItem: "center",
        gap: "8px",
        fontStyle: "normal",
        borderRadius: props.borderRadius || "5px",
      }}
    >
      <Link className="link-heading" to={props.Link}>
        <Typography
          sx={{
            fontSize: props.fontTitle || "24px",
            color: props.colorTitle || "#303E65",
            fontWeight: props.fontWeight || "700",
            lineHeight: props.lineHeight || "20px",
            textAlign: "center",
            marginBottom: props.marginBottom || "4px",
          }}
        >
          {props.title}
        </Typography>

        <Typography
          sx={{
            fontSize: props.fontDetail || "14px",
            color: "#303E65",
            fontWeight: props.fontWeight || "400",
            lineHeight: props.lineHeight || "20px",
            textAlign: "center",
          }}
        >
          {props.detail}
        </Typography>
      </Link>
    </Box>
  );
}

export default CustomBox;
