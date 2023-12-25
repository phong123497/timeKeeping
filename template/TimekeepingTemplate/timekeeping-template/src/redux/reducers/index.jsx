import { combineReducers } from 'redux';

import { AlertReducer } from './AlertReducer';
import { AuthReducer } from './authReducer';
import { CompanyReducer } from './companyReducer';
import { CircularReducer } from './circularReducer';
import { QrCodeReducer } from './qrCodeReducer';  
import { EmployessReducer } from './employeesReducer';
const rootReducer = combineReducers({
    alert: AlertReducer,
    auth: AuthReducer,
    companies: CompanyReducer,
    circular:CircularReducer,
    qrcode:QrCodeReducer,
    employees:EmployessReducer,
});

export default rootReducer;