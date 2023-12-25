import { QrActionType } from "../actionTypes/qrActionType";
const initialState = {
  data: [
    {
      name: null,
      updatedDt: null
    }
  ],
  error: null
};

export const QrCodeReducer = (state = initialState, action) => {
  switch (action.type) {
    case QrActionType.QRCODE_SUCCESS:
      return {
        ...state,
        data: action.payload.body.employeeScanList,
        error: null
      };
    case QrActionType.QRCODE_FAIL:
      return {
        ...state,
        data: [{ userName: null, updatedDt: null }],
        error: action.payload
      };
    default:
      return state;
  }
};
