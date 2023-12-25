import { API } from "../../services/apiConstant";
import apiRequest from "../../services/axiosRequest";
import { ENV } from "../../services/envApiConstant";
import { QrActionType } from "../actionTypes/qrActionType";
export const QrCodeAction = (qrcode) => async (dispatch) => {
  const body = JSON.stringify({
    qrcode
  });
  const response = await apiRequest(
    ENV.BASE_URL_MANAGEMENT,
    API.USERS.QRSCAN,
    API.METHOD.POST,
    body
  );
  if (response.status == API.STATUS.SUCCESS) {
    dispatch({
      type: QrActionType.QRCODE_SUCCESS,
      payload: response.data 
    });
  } else {
    dispatch({
      type: QrActionType.QRCODE_FAIL,
      payload: response.status
    });
  }
};
