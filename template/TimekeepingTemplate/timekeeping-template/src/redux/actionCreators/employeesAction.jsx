import { API } from "../../services/apiConstant";
import apiRequest from "../../services/axiosRequest";
import { ENV } from "../../services/envApiConstant";
import { EmployeeActionType } from "../actionTypes/employeeActionType";

export const EmployeesAction = (workDay) => async (dispatch) => {
  const link = API.USERS.LIST_EMPLOYEE + workDay;
  dispatch({
    type: EmployeeActionType.FETCH_EMPLOYEES
  });

  const response = await apiRequest(
    ENV.BASE_URL_MANAGEMENT,
    link,
    API.METHOD.POST
  );
  if (response.status == API.STATUS.SUCCESS) {
    dispatch({
      type: EmployeeActionType.FETCH_EMPLOYEES_SUCCESS,
      payload: response.data
    });
  } else {
    dispatch({
      type: EmployeeActionType.FETCH_EMPLOYEES_FAIL,
      payload: response.status
    });
  }
};

export const UpdateEmployee = (employeeEdit) => async (dispatch) => {
  
  dispatch({
    type: EmployeeActionType.UPDATE_EMPLOYEE
  });

  const response = await apiRequest(
    ENV.BASE_URL_MANAGEMENT,
    API.USERS.UPDATE_EMPLOYEE,
    API.METHOD.POST,
    employeeEdit
  );
  if (response.status == API.STATUS.SUCCESS) {
    dispatch({
      type: EmployeeActionType.UPDATE_EMPLOYEE_SUCCESS,
      payload: response.data
    });
  } else {
    dispatch({
      type: EmployeeActionType.UPDATE_EMPLOYEE_FAIL,
      payload: response.payload
    });
  }
};
