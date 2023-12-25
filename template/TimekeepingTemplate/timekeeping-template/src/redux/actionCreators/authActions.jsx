import { API } from '../../services/apiConstant';
import apiRequest from '../../services/axiosRequest';
import { ENV } from '../../services/envApiConstant';
import {
    ActionType
} from '../actionTypes/authActionTypes';
import { CircularActionType } from '../actionTypes/circularActionTypes';

export const AuthStart = (username, password) =>
    async (dispatch) => {
        dispatch({ type: CircularActionType.OPEN_CIRCULAR });
        // Request Body
        const body = JSON.stringify({
            username,
            password
        });
        const response = await apiRequest(ENV.BASE_URL_AUTH, API.USERS.AUTHORIZATION, API.METHOD.POST, body)
        if (response.status == API.STATUS.SUCCESS) {
            dispatch({
                type: ActionType.AUTH_SUCCESS,
                payload: response.data
            });
        } else {
            dispatch({
                type: ActionType.AUTH_FAIL,
                payload: response.data.error
            });
        }
        dispatch({ type: CircularActionType.CLOSE_CIRCULAR });
    };

export const ForgetPassAction = (email) =>
    async (dispatch) => {
    const link =  API.USERS.FORGETPASS +email
    // Request Body
    const body = JSON.stringify({
        email,
    });
    const response = await apiRequest(ENV.BASE_URL_MANAGEMENT,link, API.METHOD.POST)
    if (response.status == API.STATUS.SUCCESS) {
        dispatch({
            type: ActionType.PASSWORD_SUCCESS,
            payload: response.data
        });
    } else {
        dispatch({
            type: ActionType.PASSWORD_FAIL,
            payload: response.data.error
        });
    }
};





