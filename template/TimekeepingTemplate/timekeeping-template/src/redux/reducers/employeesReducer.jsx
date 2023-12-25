import { EmployeeActionType } from "../actionTypes/employeeActionType";

const initState = {
  listEmployees: [],
  onTime: null,
  late: null,
  noAttend: null,
  error: null,
  loading: false,
  isUpdate:false
};
export const EmployessReducer = (state = initState, action) => {
  switch (action.type) {
    case EmployeeActionType.FETCH_EMPLOYEES_SUCCESS:
      return {
        ...state,
        listEmployees:action.payload.body.listEntity,
        onTime: action.payload.body.onTime,
        late: action.payload.body.late,
        noAttend: action.payload.body.noAttend,
        error: null,
        loading: false
      };
    case EmployeeActionType.FETCH_EMPLOYEES_FAIL:
      return {
        ...state,
        listEmployees: [],
        onTime: null,
        late: null,
        noAttend: null,
        error: action.payload.error
      };
    case EmployeeActionType.UPDATE_EMPLOYEE_SUCCESS:
      return{
        ...state,
        isUpdate:action.payload.body,
        loading:false,
        error:null,
      }
    case EmployeeActionType.UPDATE_EMPLOYEE_FAIL:
      return{ 
        ...state,
        isUpdate:action.payload,
        error:action.payload
      }
    default:
      return state;
  }
};
