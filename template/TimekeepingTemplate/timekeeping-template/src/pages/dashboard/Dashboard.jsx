import Box from '@mui/material/Box';
// import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from "react-redux";
import { AlertOpenModel } from "../../redux/actionCreators/alertActions";
import { 
  useSidebarSelectedMenuTitleContext
} from "../../hooks";

const Dashboard = () => {
  const { t, i18n } = useTranslation();
  const dispatch = useDispatch();
  const isAuthenticated = useSelector(
    (state) => state.alert.message
  )
  const hideModal = () => {
    console.log('hide model fired')
    dispatch(AlertOpenModel("def"))
  }

  const { menuTitle } = useSidebarSelectedMenuTitleContext();
  // const theme = useTheme();
  // sx={{[theme.breakpoints.down('sm')]: {display:'flex', flexDirection:'column', justifyContent:'center'}}}
  return (
    <Box sx={{ flexGrow: 1, p: 2 }} >
      <Grid container spacing={2} >
        <Grid item xs={10} sm={6} md={6} lg={3}>
          {t('title')} {isAuthenticated} {menuTitle}
          <Button size="sm" color="info" onClick={hideModal} style={{ fontWeight: 'bold' }}>بستن</Button>
        </Grid>

      </Grid>
    </Box>
  );
}

export default Dashboard;